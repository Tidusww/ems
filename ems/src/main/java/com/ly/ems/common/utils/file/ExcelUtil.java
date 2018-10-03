package com.ly.ems.common.utils.file;

import com.ly.ems.common.utils.DateUtil;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -6051701806551481211L;

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	public final static int sheetSize = 10000;
	public final static int diskSize = 100000;


    /**
     * 根据模板
     * @param is 模板
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
            logger.error("生成Excel出错", e);
            throw new EMSBusinessException("生成Excel出错");
        }
        return workbook;
    }


    /**
     * 导入时从Excel中读取数据
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


	public static Workbook getWorkbook(Class<?> clazz, List<?> resultList) {

		try {
			List<Field> fields = ReflectUtil.getFieldByAnnotation(ExcelAttribute.class, clazz);
			// 产生工作薄对象
			Workbook workbook = new SXSSFWorkbook(diskSize);

			// 数据源数量
			int listSize = 0;
			if (resultList != null && resultList.size() >= 0) {
				listSize = resultList.size();
			}
			// 工作簿页数
			double sheetNo = Math.ceil(listSize / sheetSize);

			for (int i = 0; i <= sheetNo; i++) {
				// 创建工作簿
				Sheet sheet = workbook.createSheet();
				// 设置工作表的名称
				workbook.setSheetName(i, "Sheet" + (i + 1));
				// 创建
				Row row;
				Cell cell;
				// 创建第一行
				row = sheet.createRow(0);
				for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
					//
					Field field = fields.get(cellNum);
					// 获取注解信息
					ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
					int col = cellNum;
					// 根据指定的顺序获得列号
					if (StringUtils.isNotBlank(attr.column())) {
						col = getExcelCol(attr.column());
					}
					// 创建列
					cell = row.createCell(col);

					sheet.setColumnWidth(i,
							(int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 1.5
									* 256));

					// 设置列中写入内容为String类型
					cell.setCellType(Cell.CELL_TYPE_STRING);
					// 写入列名
					cell.setCellValue(attr.name());
				}

				// 创建内容列
				int startNo = i * sheetSize;
				int endNo = Math.min(startNo + sheetSize, listSize);
				for (int j = startNo; j < endNo; j++) {
					row = sheet.createRow(j + 1 - startNo);
					// 得到导出对象.
					Object vo = resultList.get(j);
					for (int k = 0; k < fields.size(); k++) {
						// 获得field
						Field field = fields.get(k);
						// 设置实体类私有属性可访问
						field.setAccessible(true);
						ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
						int col = k;
						// 根据指定的顺序获得列号
						if (StringUtils.isNotBlank(attr.column())) {
							col = getExcelCol(attr.column());
						}

						cell = row.createCell(col);
						// 如果数据存在就填入,不存在填入空格
						Class<?> classType = (Class<?>) field.getType();
						String value = null;
						if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
							value = DateFormatUtils.format((Date) field.get(vo), DateUtil.YYYY_MM_DD);
						}
						cell.setCellValue(
								field.get(vo) == null ? "" : value == null ? String.valueOf(field.get(vo)) : value);

					}
				}

			}
			return workbook;
		} catch (Exception e) {
			logger.warn("Excel writeExcelFromList Exception" + e);
			return null;
		}
	}

	/**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 *
	 * @param col
	 */
	private static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

//	public static Workbook getWorkbook(Class clazz, List<String> keys, List<String> titles, List resultList) {
//
//		try {
//
//			List<Method> methods = ReflectUtil.getMethod(keys, ExportOrderDetailDTO.class);
//			// 产生工作薄对象
//			Workbook workbook = new SXSSFWorkbook(diskSize);
//
//			// 创建工作簿
//			Sheet sheet = workbook.createSheet();
//			// 设置工作表的名称
//			workbook.setSheetName(0, "Sheet1");
//
//			createTitles(titles, sheet);
//
//			for (int j = 0; j < resultList.size(); j++) {
//				Row row = sheet.createRow(j + 1);
//				Cell cell = null;
//				// 得到导出对象.
//				Object vo = resultList.get(j);
//				for (int k = 0; k < methods.size(); k++) {
//					Object o = methods.get(k).invoke(vo);
//					if(o!=null && o instanceof Number) {
//					  cell = row.createCell(k,Cell.CELL_TYPE_NUMERIC);
//					  if(o instanceof Integer || o instanceof Long) {
//						  cell.setCellValue(o ==null ? 0 : ((Number)o).longValue() );
//					  }
//					  else {
//					     cell.setCellValue(o ==null ? 0 : ((Number)o).doubleValue() );
//					  }
//					}else {
//						String value = null;
//						if (o != null && o.getClass().isAssignableFrom(Date.class)) {
//							value = DateFormatUtils.format((Date) o, DateUtil.YYYY_MM_DD);
//						}
//						cell =row.createCell(k);
//						cell.setCellValue(o == null ? "" : value == null ? String.valueOf(o) : value);
//					}
//				}
//			}
//			return workbook;
//		} catch (Exception e) {
//			logger.warn("Excel writeExcelFromList Exception" + e);
//			return null;
//		}
//	}

	public static void createTitles(List<String> titles, Sheet sheet) {
		// 创建
		Row row;
		Cell cell;
		// 创建第一行
		row = sheet.createRow(0);
		for (int col = 0; col < titles.size(); col++) {
			// 创建列
			cell = row.createCell(col);

			// 设置列中写入内容为String类型
			cell.setCellType(Cell.CELL_TYPE_STRING);
			// 写入列名
			cell.setCellValue(titles.get(col));
		}
	}

}