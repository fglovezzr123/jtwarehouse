package com.wing.socialcontact.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class POIUtil {
	/**
	 * 导出的excel标题样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setBorderBottom((short) 1);
		titleStyle.setBorderLeft((short) 1);
		titleStyle.setBorderRight((short) 1);
		titleStyle.setBorderTop((short) 1);
		titleStyle.setFillForegroundColor((short) 46);
		titleStyle.setFillPattern((short) 1);
		titleStyle.setAlignment((short) 2);// 居中
		titleStyle.setVerticalAlignment((short) 1);// 垂直居中

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontName("新宋体");
		cellFont.setFontHeightInPoints((short) 12);
		cellFont.setBoldweight((short) 700);// 加粗
		titleStyle.setFont(cellFont);

		return titleStyle;
	}

	/**
	 * 导出的excel一般单元格样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment((short) 1);// 垂直居中

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontName("新宋体");
		cellFont.setFontHeightInPoints((short) 12);
		cellStyle.setFont(cellFont);

		return cellStyle;
	}

	/**
	 * 日期格式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getDateCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();

		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment((short) 1);// 垂直居中

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontName("新宋体");
		cellFont.setFontHeightInPoints((short) 12);
		cellStyle.setFont(cellFont);
		cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));
		return cellStyle;
	}

	/**
	 * 货币
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getMoneyCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontName("新宋体");
		cellFont.setFontHeightInPoints((short) 12);

		HSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("#,##0.00")); //两位小数
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cellStyle.setVerticalAlignment((short) 1);// 垂直居中
		cellStyle.setFont(cellFont);

		return cellStyle;
	}

	public static void setCellValue(HSSFCell hssfCell, HSSFCellStyle cellStyle,
			Object value) {
		hssfCell.setCellStyle(cellStyle);
		if (value == null)
			return;
		if (value instanceof String) {
			hssfCell.setCellValue(value.toString());
		} else if (value instanceof Date) {
			hssfCell.setCellValue((Date) value);
		} else if (value instanceof Double) {
			hssfCell.setCellValue((Double) value);
		} else if (value instanceof Integer) {
			hssfCell.setCellValue((Integer) value);
		}
	}

	public static void autoSizeColumn(HSSFRow row) {
		int len = row.getPhysicalNumberOfCells();
		for (short i = 0; i < len; i++) {
			row.getSheet().autoSizeColumn(i);
		}
	}

	public static void export(HttpServletResponse response, HSSFWorkbook wb,
			String fileName) throws IOException {
		BufferedOutputStream out = null;
		fileName = fileName == null ? "" : fileName.trim();
		fileName = new String(fileName.concat(
				DateUtil.currentDateTimeToString() + ".xls").getBytes(
				"GBK"), "iso-8859-1");

		response.setContentType("application/x-download; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		out.close();
	}

	public static void exportForExcle(HttpServletResponse response, HSSFWorkbook wb,
							  String fileName) throws IOException {
		BufferedOutputStream out = null;
		fileName = fileName == null ? "" : fileName.trim();
		fileName = new String(fileName.concat(
				DateUtil.currentDateTimeToString() + ".xls").getBytes(
				"UTF-8"), "iso-8859-1");

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		out = new BufferedOutputStream(response.getOutputStream());
		wb.write(out);
		out.flush();
		out.close();
	}
}
