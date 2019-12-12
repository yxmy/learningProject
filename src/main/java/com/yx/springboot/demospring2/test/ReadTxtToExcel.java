package com.yx.springboot.demospring2.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadTxtToExcel {

    public static List<String> readTxt(String txtPath) {
        File file = new File(txtPath);
        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                List<String> list = new ArrayList<>();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    list.add(text);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void exportExcel(List<String> list) throws FileNotFoundException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("host原系统整理");
        for(int t = 0;t<list.size();t++){
            HSSFRow hssfRow = sheet.createRow(t+1);
            String text = list.get(t);
            if (!text.contains(",")) {
                HSSFCell hssfCell = hssfRow.createCell(0);
                hssfCell.setCellValue(text);
            } else {
                for (int x = 0; x < 2; x++) {
                    HSSFCell hssfCell = hssfRow.createCell(x);
                    switch (x) {
                        case 0:
                            hssfCell.setCellValue(text.substring(0,text.indexOf(",")));
                            break;
                        case 1:
                            hssfCell.setCellValue(text.substring(text.indexOf(",") + 1));
                        default:
                            //...;
                            break;
                    }

                }
            }
        }

        FileOutputStream os = new FileOutputStream("C:\\Users\\gs\\Desktop\\host原系统整理.xls");
        try {
            wb.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> list = readTxt("C:\\Users\\gs\\Desktop\\host-name.txt");
        exportExcel(list);
        System.out.println("success!!");
    }

}
