/**  
 * @Project: tjy
 * @Title: ExcelUtils.java
 * @Package com.oa.commons.util
 * @date 2016-6-6 下午02:34:28
 * @Copyright: 2016 
 */
package com.tojoy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * 类名：ExcelUtils
 * 功能：excel工具类
 * 详细：
 * 作者：zcl
 * 版本：1.0
 * 日期：2016-6-6 下午02:34:28
 *
 */
public class ExcelUtils {
	 
	/***
	 * 读取excel文件中的内容
	 * @param is 
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcelAll(InputStream is ){
		List<Map> list = new ArrayList<Map>();
		 
		try { 
	     
	    
		Workbook wb = WorkbookFactory.create(is); 
		 //获取第一页
		Sheet sheet = wb.getSheetAt(0); 
		//遍历每行(去掉表头)
		for(int rowsNum =1 ; rowsNum<= sheet.getLastRowNum(); rowsNum++){
			if(null != sheet.getRow(rowsNum)){
			 
				Map<Integer, String> map = new HashMap<Integer, String>();
				Row row = sheet.getRow(rowsNum);   
				 
				//遍历每列
				for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
					if(null!=row.getCell(cellNum)){
						String value =""; 
						Cell cell = row.getCell(cellNum);
						int cellType = cell.getCellType(); 
						if(cellType== Cell.CELL_TYPE_NUMERIC){ 
							int dataFormat = cell.getCellStyle().getDataFormat(); 
							  if (dataFormat == 14 ||dataFormat == 28||dataFormat == 22|| dataFormat == 178||dataFormat == 58 || dataFormat == 180 || dataFormat == 181
							    || dataFormat == 182) { 
							    Date date = cell.getDateCellValue();
								if (date != null) {
									value =  new SimpleDateFormat("yyyy-MM-dd").format(date);
								} 
							  }   else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							} 
						}else if(cellType==Cell.CELL_TYPE_STRING){
							value =cell.getStringCellValue();
						} 
						map.put(cellNum, value);
						//System.out.println(cellNum+"------"+value);
					}
				}
				 
			 list.add(map);
			} 
		} 
		} catch (FileNotFoundException e) { 
		e.printStackTrace();
		} catch (InvalidFormatException e) { 
		e.printStackTrace();
		} catch (IOException e) { 
		e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return list;
	}
	/***
	 * 只读取第一列,去重
	 * @param is 
	 * @return set 
	 */ 
	public static Set<String> readExcelOne(InputStream is ){
		 
		Set<String> set = new HashSet<String>();
		try { 
	    
	    
		Workbook wb = WorkbookFactory.create(is); 
		 //获取第一页
		Sheet sheet = wb.getSheetAt(0); 
		//遍历每行(去掉表头)
		for(int rowsNum =1 ; rowsNum<= sheet.getLastRowNum(); rowsNum++){
			if(null != sheet.getRow(rowsNum)){ 
				Row row = sheet.getRow(rowsNum);   
				  
				//读取第一列
				int cellNum = 0;
				if(null!=row.getCell(cellNum)){
					String value =""; 
					Cell cell = row.getCell(cellNum);
					int cellType = cell.getCellType(); 
					if(cellType== Cell.CELL_TYPE_NUMERIC){ 
						int dataFormat = cell.getCellStyle().getDataFormat(); 
						  if (dataFormat == 14 ||dataFormat == 28||dataFormat == 22|| dataFormat == 178||dataFormat == 58 || dataFormat == 180 || dataFormat == 181
						    || dataFormat == 182) { 
						    Date date = cell.getDateCellValue();
							if (date != null) {
								value =  new SimpleDateFormat("yyyy-MM-dd").format(date);
							} 
						  }   else {
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						} 
					}else if(cellType==Cell.CELL_TYPE_STRING){
						value =cell.getStringCellValue();
					}  
					//System.out.println(cellNum+"------"+value);
					set.add(value);
				} 
			} 
		} 
		} catch (FileNotFoundException e) { 
		e.printStackTrace();
		} catch (InvalidFormatException e) { 
		e.printStackTrace();
		} catch (IOException e) { 
		e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return set;
	}
	/**
	 * 导出excel文件
	 * @param data	导出的数据
	 * @param tcs	自定义设置
	 * @param fileName	文件名称
	 * @param request
	 * @param response
	 * @return
	 */
//	public static String export(List data,List<SyTableCustom> tcs,String fileName,HttpServletRequest request,HttpServletResponse response){
//		OutputStream  os=null;
//		try{
//			// 创建新的Excel 工作簿
//			HSSFWorkbook workbook = new HSSFWorkbook();
//			HSSFSheet sheet = workbook.createSheet();
//			//第二行添加类型名称
//			HSSFRow secondRow = sheet.createRow(1);
//			for(int j=0;j<tcs.size();j++){
//				SyTableCustom tc=tcs.get(j);
//				HSSFCell cell = secondRow.createCell(j);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				if(StringUtils.isBlank(tc.getFieldAnotherTitle())){
//					cell.setCellValue(tc.getFieldTitle());
//				}else{
//					cell.setCellValue(tc.getFieldAnotherTitle());
//				}
//			}
//			//从第三行开始添加数据
//			for(int i=0;i<data.size();i++){
//				HSSFRow row = sheet.createRow(i+2);
//				for(int j=0;j<tcs.size();j++){
//					SyTableCustom tc=tcs.get(j);
//					HSSFCell cell = row.createCell(j);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					//根据属性名获取值
//					Object pro=PropertyUtils.getProperty(data.get(i), tc.getFieldName());
//					String value="";
//					if(pro!=null){
//						if(tc.getFieldType()==3){
//							//日期(YYYY-MM-DD)
//							value=DateUtil.date2String((Date)pro);
//						}else if(tc.getFieldType()==4){
//							//日期时间(YYYY-MM-DD HH:MM:SS)
//							value=DateUtil.timestamp2String((Timestamp)pro);
//						}else if(tc.getFieldType()==5){
//							//用户id(转换用户姓名)
//							value=MyCache.getInstance().getTrueName((String)pro);
//						}
//						else if(tc.getFieldType()==6){
//							value=MyCache.getInstance().getDeptName((String)pro);
//							//部门id(转换部门名称)
//						}
//						else if(tc.getFieldType()==7){
//							//用户id(转换用户登录名)
//							value=MyCache.getInstance().getUserName((String)pro);
//						}else{
//							value=pro.toString();
//						}
//					}
//					cell.setCellValue(value);
//				}
//			}
//			//以下载的形式
//			response.setContentType("application/octet-stream"); 
//			
//			// 根据不同浏览器 设置response的Header
//	        String userAgent = request.getHeader("User-Agent").toLowerCase();
//	        
//	        if(userAgent.indexOf("msie")!=-1){
//	        	//ie浏览器
//	        	response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF8")); 
//	        	
//	        }else{
//	        	response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));  
//	        }
//			
//			os=response.getOutputStream();
//			workbook.write(os);
//			os.flush();
//			return null;
//		}catch(Exception e) {
//			 e.printStackTrace();
//	    	 response.reset(); 
//	    	 return "exception";//返回异常页面
//		}finally{
//			if(os!=null){
//           	 try {
//					os.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//            }
//		}
//	}
	/**
	 * 传入data 数据模型
	 * @param data
	 * @param tcs
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
//	public static String export(DataGrid data,List<SyTableCustom> tcs,String fileName,HttpServletRequest request,HttpServletResponse response){
//		
//		return export(data.getRows(), tcs, fileName, request, response);
//	}
	
	
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		//xls格式 
		String path ="D://phonenumber.xls";
		//xlsx格式  
		String path1 ="D://phonenumber.xlsx"; 
		/*
	    
	 	List<Map> list =   ExcelUtils.readExcelAll(path);
		for (Map map : list) {
			System.out.println(map.size()+"--"+map.get(0)+"--"+map.get(1));   
		} 
		
		List<Map> list1 = ExcelUtils.readExcelAll(path1);
		for (Map map : list1) {
			System.out.println(map.size()+"++"+map.get(0)+"++"+map.get(1));   
		}*/
		
		InputStream is;
		try {
			is = new FileInputStream(path);
			Set<String> set = ExcelUtils.readExcelOne(is);
			for (String s: set) {
				System.out.println(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream is1;
		try {
			is1 = new FileInputStream(path1);
			Set<String> set1 = ExcelUtils.readExcelOne(is1);
			for (String s: set1) {
				System.out.println(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
