package com.yx.springboot.demospring.testlist.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelCompare {

    public static void main(String[] args) throws Exception {
        try {
            List<String> newStringsList = getData("C:\\Users\\Yuan_xin\\Desktop\\截图\\新系统\\统计报表\\院线影片分影厅查询_202001_北京长城沃美电影院线有限公司.xls");
            List<String> oldStringsList = getData("C:\\Users\\Yuan_xin\\Desktop\\截图\\老环境截图\\统计报表\\院线影片分影厅查询_202001_北京长城沃美电影院线有限公司.xls");
            System.out.println("first: " + newStringsList.size());
            System.out.println("second: " + oldStringsList.size());
            if (oldStringsList.size() > 0) {
                for (String str : newStringsList) {
                    boolean flag = oldStringsList.contains(str);
                    if (!flag) {
                        System.out.println("字段不一致，新环境字段为：" + str);
                    }
                }
            }
            System.out.println("对比完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getData(String path){
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File(path);
            try (FileInputStream in = new FileInputStream(excelFile)){
                Workbook workbook = new HSSFWorkbook(in);
                /**
                 * 设置当前excel中sheet的下标：0开始
                 */
                List<String> stringsList = new ArrayList<>();
                Sheet sheet = workbook.getSheetAt(0);
                StringBuilder stringBuilder = new StringBuilder();
                for (Row row : sheet) {
                    for (int i = 1; i < row.getLastCellNum(); i++) {
                        stringBuilder.append(row.getCell(i));
                    }
                    stringsList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
                return stringsList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
