package com.yx.springboot.demospring.testlist.test;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ReadFilmInfoToExcel {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File("C:\\Users\\Yuan_xin\\Desktop\\影片基础信息片目.xlsx");
            FileInputStream in = new FileInputStream(excelFile);
            checkExcelVaild(excelFile);
            Workbook workbook = new XSSFWorkbook(in);
            /**
             * 设置当前excel中sheet的下标：0开始
             */
            int i = 0;
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (i++ == 0) {
                    continue;
                }
                String code = row.getCell(2).toString();
                String type = code.substring(3, 4);
                Cell numCell = row.createCell(13);
                Cell nameCell = row.createCell(14);
                numCell.setCellValue(type);
                nameCell.setCellValue(setType(type));
            }
            FileOutputStream os = new FileOutputStream("C:\\Users\\Yuan_xin\\Desktop\\影片基础信息片目1.xlsx");
            workbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String setType(String type){
        switch (type){
            case "0":
                return "观摩影片";
            case "1":
                return "普通 ";
            case "2":
                return "普通立体";
            case "3":
                return "巨幕";
            case "4":
                return "巨幕立体";
            case "5":
                return "胶片(进口)";
            case "6":
                return "其他特种电影";
            case "7":
                return "其他";
            case "a":
                return "动画片";
            case "b":
                return "纪录片";
            case "c":
                return "科教片";
            default:
                return "未知";
        }
    }

}
