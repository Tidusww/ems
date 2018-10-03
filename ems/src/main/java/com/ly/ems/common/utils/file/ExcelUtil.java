package com.ly.ems.common.utils.file;

import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.ReflectUtil;
import com.ly.ems.core.exception.EMSBusinessException;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil implements Serializable {

    private static final long serialVersionUID = -6051701806551481211L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public final static int sheetSize = 10000;
    public final static int diskSize = 100000;

    /**
     * 1、根据模板is 和数据，生成excel workbook
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
            throw new EMSBusinessException("生成Excel出错");
        }
        return workbook;
    }


    /**
     * 2、导入时从Excel中读取数据
     *
     * @param excelStream
     * @param xmlStream
     * @param beans
     * @return
     */
    public static boolean getDataFromExcelFile(InputStream excelStream, InputStream xmlStream, Map beans) {
        XLSReader mainReader = null;
        try {
            mainReader = ReaderBuilder.buildFromXML(xmlStream);
            XLSReadStatus readStatus = mainReader.read(excelStream, beans);
            return readStatus.isStatusOK();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EMSBusinessException("从Excel读取数据出错");
        }
    }


    /**
     * 3、根据类和数据，生成workbook
     *
     * @param clazz      类
     * @param resultList 数据
     * @return
     */
    public static Workbook getWorkbook(Class<?> clazz, List<?> resultList) {
        try {
            // 数据源数量
            int listSize = 0;
            if (resultList != null && !resultList.isEmpty()) {
                listSize = resultList.size();
            }
            // 工作簿页数
            double sheetNo = Math.ceil(listSize / sheetSize);

            // 需要导出的字段
            List<Field> fields = ReflectUtil.getFieldByAnnotation(ExcelAttribute.class, clazz);

            // 产生工作薄对象
            Workbook workbook = new SXSSFWorkbook(diskSize);

            // 输出每个工作簿的内容、并输出标题
            for (int i = 0; i <= sheetNo; i++) {
                // 创建工作簿
                Sheet sheet = workbook.createSheet();
                workbook.setSheetName(i, "Sheet" + (i + 1));

                // 创建标题行
                createTitles(sheet, fields);

                // 创建内容列
                int startNo = i * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                createRecords(sheet, fields, startNo, endNo, resultList);
            }
            return workbook;
        } catch (Exception e) {
            LOGGER.warn("Excel writeExcelFromList Exception" + e);
            return null;
        }
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
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
     * 创建标题行
     * @param fields
     * @param sheet
     */
    private static void createTitles(Sheet sheet, List<Field> fields) {
        Row row = sheet.createRow(0);
        for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
            // 获取每列标题field
            Field field = fields.get(cellNum);

            // 获取注解信息
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);

            // 根据指定的顺序获得列号
            int col = cellNum;
            if (StringUtils.isNotBlank(attr.column())) {
                col = getExcelColNum(attr.column());
            }

            // 创建列
            Cell cell = row.createCell(col);
            // 设置列中写入内容为String类型
            cell.setCellType(Cell.CELL_TYPE_STRING);
            // 写入列名
            cell.setCellValue(attr.name());
            // 设置宽度
            sheet.setColumnWidth(col, (int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 1.5 * 256));
        }
    }

    /**
     * 根据结果集、输出指定行数的内容
     * @param sheet
     * @param fields
     * @param startNo
     * @param endNo
     * @param resultList
     * @throws Exception
     */
    private static void createRecords(Sheet sheet, List<Field> fields, int startNo, int endNo, List resultList) throws Exception{
        for (int rowNo = startNo; rowNo < endNo; rowNo++) {
            Row row = sheet.createRow(rowNo + 1 - startNo);
            // 得到导出对象.
            Object vo = resultList.get(rowNo);
            for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
                // 获得field
                Field field = fields.get(cellNum);
                // 设置实体类私有属性可访问
                field.setAccessible(true);

                ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);

                // 根据指定的顺序获得列号
                int col = cellNum;
                if (StringUtils.isNotBlank(attr.column())) {
                    col = getExcelColNum(attr.column());
                }

                // 创建列
                Cell cell = row.createCell(col);

                // 如果数据存在就填入,不存在填入空格
                Class<?> classType = field.getType();
                Object value = field.get(vo);
                String valueString = "";
                if (value != null) {
                    if (classType.isAssignableFrom(Date.class)) {
                        valueString = DateFormatUtils.format((Date) field.get(vo), DateUtil.YYYY_MM_DD);
                    } else {
                        valueString = String.valueOf(value);
                    }
                }
                cell.setCellValue(valueString);

            }
        }
    }

}