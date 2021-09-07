package com.yx.springboot.demospring.pulldata.pullInfFrom5i5j;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class ReadExcelAndWrite {

    public static void getXlsExcelData(File file) {
        InputStream is;
        try {
            is = new FileInputStream(file);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            // 获取每一个工作薄
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // 获取当前工作薄的每一行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    //第三列数据
                    HSSFCell three = hssfRow.getCell(0);
                    String name = three.getStringCellValue();
                    System.out.println("开始查询：" + name + "经纬度");
                    String lanAndLat = EntCoordSync.getCoordinate(name);
                    if(StringUtils.isNotBlank(lanAndLat)){
                        String arr [] = lanAndLat.split(",");
                        hssfRow.createCell(2).setCellValue(arr[0]);
                        hssfRow.createCell(3).setCellValue(arr[1]);
                    }
                }
            }
            File file1 = new File("/home/pc-yx/桌面/太原市POI_New.xls");
            if (!file.exists()){
                file.createNewFile();
            }
            OutputStream  os = new FileOutputStream(file1);
            hssfWorkbook.write(os);
            is.close();
            os.close();
            System.out.println("导出结束！！！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadExcelAndWrite.getXlsExcelData(new File("/home/pc-yx/桌面/太原市POI.xls"));
    }

}
