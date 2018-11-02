package com.ly.ems.common.utils.file;

import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.ReflectUtil;

import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil implements Serializable {

    private static final long serialVersionUID = -6051701806551481211L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public final static String KEY_SHEET_SIZE = "sheetSize";
    public final static int SHEET_SIZE = 10000;
    public final static int ROW_SIZE = 2000; // 缓存行数


    /**
     * 1、jxls 导入时从Excel中读取数据
     *
     * @param excelStream
     * @param xmlStream
     * @param beans
     * @return
     */
    public static boolean getDataFromExcelFile(InputStream excelStream, InputStream xmlStream, Map beans, Map<Class, Converter> converterClassMap) {
        XLSReader mainReader = null;
        try {
            mainReader = ReaderBuilder.buildFromXML(xmlStream);

            // 添加外面指定添加的类型转换器
            if (converterClassMap != null && converterClassMap.size() != 0) {
                for ( Map.Entry<Class, Converter> entry : converterClassMap.entrySet()) {
                    mainReader.getConvertUtilsBeanProvider().getConvertUtilsBean().register(entry.getValue(), entry.getKey());
                }
            }

            XLSReadStatus readStatus = mainReader.read(excelStream, beans);
            return readStatus.isStatusOK();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EMSRuntimeException("从Excel读取数据出错");
        }
    }

    /**
     * 2、jxls 根据模板is 和数据，生成excel workbook
     *
     * @param is         模板
     * @param beanParams 数据
     * @return
     */
    public static Workbook generateWorkbook(InputStream is, Map beanParams) {
        // 1. 生成workbook
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook;
        try {
            long s = System.currentTimeMillis();
            workbook = transformer.transformXLS(is, beanParams);
            System.out.println("生成Excel成功，耗时：" + (System.currentTimeMillis() - s));
        } catch (Exception e) {
            LOGGER.error("生成Excel出错", e);
            throw new EMSRuntimeException("生成Excel出错");
        }
        return workbook;
    }

    /**
     * 3、根据模板和导出描述类生成excel
     *
     * @param templateWorkbook 模板workbook
     * @param exportClass      导出描述类
     * @param resultList       结果集
     * @param param            额外数据
     * @return
     */
    public static Workbook generateBigWorkbook(XSSFWorkbook templateWorkbook, Class exportClass, List resultList, Map<String, String> param) {
        try {
            // 数据源数量
            int listSize = 0;
            if (resultList != null && !resultList.isEmpty()) {
                listSize = resultList.size();
            }
            // 工作簿页数
            Integer sheetSize = StringUtils.isEmpty(param.get(KEY_SHEET_SIZE)) ? SHEET_SIZE : Integer.parseInt(param.get(KEY_SHEET_SIZE));
            Integer sheetNo = Double.valueOf(Math.ceil(1.0 * listSize / sheetSize)).intValue();

            // 需要导出的字段
            List<Field> fields = ReflectUtil.getFieldByAnnotation(ExcelAttribute.class, exportClass);


            ExcelAttribute exportAttr = (ExcelAttribute) exportClass.getAnnotation(ExcelAttribute.class);
            Integer contentRow = exportAttr.contentRow();

            // 产生工作薄对象
            Workbook workbook = new SXSSFWorkbook(templateWorkbook, ROW_SIZE);

            // 输出每个工作簿的内容、并输出标题
            for (int i = 0; i < sheetNo; i++) {
                // 1. 创建工作簿
//                Sheet sheet = workbook.createSheet();
//                workbook.setSheetName(i, "Sheet" + (i + 1));
                // 使用导出模板中的sheet
                Sheet sheet = workbook.getSheetAt(i);

                // 2. 输出固定单元格数据
                fillFixedData(sheet, fields, param);

                // 3. 输出列表
                int startNo = i * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                createRecords(sheet, fields, startNo, endNo, contentRow, resultList);
            }
            return workbook;
        } catch (Exception e) {
            LOGGER.error("生成excel失败", e);
            throw new EMSRuntimeException("生成excel失败");
        }
    }

    /**
     * util：输出固定单元格数据
     *
     * @param sheet  工作簿
     * @param fields 导出定义类的字段
     * @return maxRow 标题所占用的行数
     */
    private static void fillFixedData(Sheet sheet, List<Field> fields, Map<String, String> param) {

        for (Field field : fields) {
            // 获取注解信息
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
            if (attr.isColumn()) {
                continue;
            }

            String region = attr.region();
            if (!StringUtils.isEmpty(region)) {
                // 获取单元格位置
                String[] regions = region.split(",");
                Integer regionRow = Integer.parseInt(regions[0]);
                Integer regionCol = getExcelColNum(regions[1]);

                // 获取指定的单元格
                Row row = sheet.getRow(regionRow);
                if (row == null) {
                    row = sheet.createRow(regionRow);
                }
                Cell cell = row.getCell(regionCol);
                if (cell == null) {
                    cell = row.createCell(regionCol);
                }

                // 设置列中写入内容为String类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                // 写入内容，可以是类中定义的content，也可以是key
                String paramKey = attr.key();
                String content = attr.content();
                if (param != null
                        && !StringUtils.isEmpty(paramKey)
                        && !StringUtils.isEmpty(param.get(paramKey))) {
                    content = param.get(paramKey);
                }
                cell.setCellValue(content);
            } else {
                LOGGER.warn(String.format("%s字段为固定单元格但没有指定位置", field.getName()));
            }
        }
    }

    /**
     * 4、poi 根据导出定义类和数据，生成大workbook
     *
     * @param clazz      导出定义类
     * @param resultList 数据
     * @return
     */
    public static <T> Workbook generateBigWorkbook(Class<?> clazz, List<T> resultList, Map<String, String> externalParam) {
        try {
            // 数据源数量
            int listSize = 0;
            if (resultList != null && !resultList.isEmpty()) {
                listSize = resultList.size();
            }
            // 工作簿页数
            Integer sheetSize = SHEET_SIZE;
            double sheetNo = Math.ceil(listSize / sheetSize);

            // 需要导出的字段
            List<Field> fields = ReflectUtil.getFieldByAnnotation(ExcelAttribute.class, clazz);

            // 产生工作薄对象
            Workbook workbook = new SXSSFWorkbook(ROW_SIZE);

            // 输出每个工作簿的内容、并输出标题
            for (int i = 0; i <= sheetNo; i++) {
                // 1. 创建工作簿
                Sheet sheet = workbook.createSheet();
                workbook.setSheetName(i, "Sheet" + (i + 1));


                // 2. 创建标题行
                int titleRow = createTitles(sheet, fields, externalParam);

                // 3. 创建内容列
                int startNo = i * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                createRecords(sheet, fields, startNo, endNo, titleRow, resultList);
            }
            return workbook;
        } catch (Exception e) {
            LOGGER.error("生成excel失败", e);
            throw new EMSRuntimeException("生成excel失败");
        }
    }

    /**
     * util：将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param colName
     */
    private static int getExcelColNum(String colName) {
        colName = colName.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int colNum = -1;
        char[] cs = colName.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            colNum += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return colNum;
    }

    /**
     * util：创建标题行（支持创建复杂表头）
     *
     * @param sheet  工作簿
     * @param fields 导出定义类的字段
     * @return maxRow 标题所占用的行数
     */
    private static int createTitles(Sheet sheet, List<Field> fields, Map<String, String> externalParam) {
        // 创建纯标题字段最大行数
        int maxRow = findMaxRowByFields(fields);
        if (maxRow != -1) {
            // 至少为0
            for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
                sheet.createRow(rowNum);
            }
        }
        maxRow++;
        // 数据标题字段开始的行数
        Row titleRow = sheet.createRow(maxRow);

        // 生成标题
        for (Field field : fields) {
            // 获取注解信息
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
//            boolean isTitle = attr.isColumn();
//            if (isTitle) {
            //
            String titleRegion = attr.titleRegion();

            // 配置复杂表头
            if (!StringUtils.isEmpty(titleRegion)) {
                String[] titleRegions = titleRegion.split(",");

                Integer startRow = Integer.parseInt(titleRegions[0]);
                Integer endRow = Integer.parseInt(titleRegions[1]);
                Integer startCol = getExcelColNum(titleRegions[2]);
                Integer endCol = getExcelColNum(titleRegions[3]);

                // 再创建对应的单元格
                Row row = sheet.getRow(startRow);
                Cell cell = row.createCell(startCol);

                // 设置列中写入内容为String类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                // 写入内容，可以是类中定义的content，也可以是paramKey
                String paramKey = attr.key();
                String content = attr.content();
                if (!StringUtils.isEmpty(paramKey) && externalParam != null) {
                    content = externalParam.get(paramKey);
                }
                cell.setCellValue(content);
//                sheet.addValidationData();

                // 先合并单元格
                sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));

                // 设置样式
                for (int colNum = startCol; colNum <= endCol; colNum++) {
                    CellStyle cellStyle = sheet.getColumnStyle(colNum);
                    // 居中
                    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


                    sheet.setDefaultColumnStyle(colNum, cellStyle);
                }

                // 设置宽度
                sheet.setColumnWidth(startCol, (int) ((attr.content().getBytes().length <= 4 ? 6 : attr.content().getBytes().length) * 1.5 * 256));
            }
        }
        return maxRow;
    }


    /**
     * 根据纯标题字段返回标题所占的最大行(0\1\2)
     *
     * @param fields
     * @return -1代表没有找到纯标题字段
     */
    private static int findMaxRowByFields(List<Field> fields) {
        int maxRow = -1;
        for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
            // 获取每列标题field
            Field field = fields.get(cellNum);
            // 获取注解信息
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
            boolean isTitle = attr.isColumn();
            if (isTitle) {
                // 纯标题列
                String titleRegion = attr.titleRegion();

                if (!StringUtils.isEmpty(titleRegion)) {
                    String[] titleRegions = titleRegion.split(",");
                    Integer endRow = Integer.parseInt(titleRegions[1]);
                    if (endRow > maxRow) {
                        maxRow = endRow;
                    }
                }
            }
        }
        return maxRow;
    }

    /**
     * util：根据结果集、输出指定行数的内容
     *
     * @param sheet
     * @param fields
     * @param startNo
     * @param endNo
     * @param resultList
     * @throws Exception
     */
    private static <T extends Object> void createRecords(Sheet sheet, List<Field> fields, int startNo, int endNo, int contentRow, List<T> resultList) throws Exception {
        for (int rowNo = startNo; rowNo < endNo; rowNo++) {

            Row row = sheet.createRow(contentRow + rowNo - startNo);
            // 得到导出对象.
            Object vo = resultList.get(rowNo);
            for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
                // 获得field
                Field field = fields.get(cellNum);
                // 设置实体类私有属性可访问
                field.setAccessible(true);

                ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);

                if (!attr.isColumn()) {
                    continue;
                }

                // 根据指定的顺序获得列号
                int col = cellNum;
                if (StringUtils.isNotBlank(attr.column())) {
                    col = getExcelColNum(attr.column());
                }

                // 创建列
                Cell cell = row.createCell(col);

                // 这里的field只是导出定义类的field，如果导出定义类跟结果集类不一样，这里的field是不能直接获取数据的
                // 所以要用导出定义类的fieldName拿到结果集的field再获取数据
                Field voField = ReflectUtil.getFieldByClass(field.getName(), vo.getClass());
                voField.setAccessible(true);
                Object value = voField.get(vo);

                // 如果数据存在就填入，不存在填入空格
                Class<?> classType = voField.getType();
                String valueString = "";
                if (value != null) {
                    if (classType.isAssignableFrom(Date.class)) {
                        valueString = DateFormatUtils.format((Date) value, DateUtil.YYYY_MM_DD);
                    } else if (classType.isAssignableFrom(BaseKeyValueEnum.class)) {
                        valueString = ((BaseKeyValueEnum) value).getValue();
                    } else if (classType.isAssignableFrom(BaseCodeValueEnum.class)) {
                        valueString = ((BaseCodeValueEnum) value).getValue();
                    } else {
                        valueString = String.valueOf(value);
                    }
                }
                cell.setCellValue(valueString);

            }
        }
    }

}