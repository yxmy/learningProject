package com.yx.springboot.demospring2.test;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by admin on 2018/4/24.
 */
public class TestClass {
    public static void main(String[] args) {
//		String yestersql = FILM_BOXOFFICE_RNAKING_YESTERDAY_SQL
//				.replaceAll(WHERE_CONDITION, " business_date = '2018-04-20'")
//				.replaceAll(TIME_ANCHOR,  " >= '2011-01-01' ")//"between '2015-06-01' and '2015-06-04'"
//				.replaceAll("mtms_sdp", "mtms_rcsdp");
//		System.out.println(FILM_BOXOFFICE_RNAKING_SQL.replaceAll(TIME_ANCHOR, " >= '2018-02-28' "));

        Map<String, String> map = new HashMap<>();
        map.put("1","111");
        map.put("2","222");
        map.put("3","333");
        Map<String, String> coll = map.entrySet().stream().filter(entry->{
            if(entry.getKey().equals("1")){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toMap(v->v.getKey(), v->v.getValue()));
        System.out.println(coll);

        // 数据库驱动类名的字符串
        String driver = "com.mysql.jdbc.Driver";
        // 数据库连接串
        String url = "jdbc:mysql://127.0.0.1:3306/jdbctest";
        // 用户名
        String username = "root";
        // 密码
        String password = "123456";
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            // 1、加载数据库驱动（ 成功加载后，会将Driver类的实例注册到DriverManager类中）
            Class.forName(driver);
            // 2、获取数据库连接
            conn = DriverManager.getConnection(url, username, password);
            // 3、获取数据库操作对象
            stmt = conn.createStatement();
            // 4、定义操作的SQL语句
            String sql = "select * from yxcinema";
            // 5、执行数据库操作
            rs = stmt.executeQuery(sql);
            // 6、获取并操作结果集
            List<Cinema> cinemas = new ArrayList<>();
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCode(rs.getString("code"));
                cinema.setShortName(rs.getString("short_name"));
                cinema.setDate(rs.getString("registration_date"));
                cinemas.add(cinema);
            }

            stmt2 = conn.createStatement();
            String sql2 = "select * from yxcinemaapp";
            // 5、执行数据库操作
            rs2 = stmt2.executeQuery(sql2);
            // 6、获取并操作结果集
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<Cinema> cinemaApps = new ArrayList<>();
            while (rs2.next()) {
                Cinema cinema = new Cinema();
                cinema.setCode(rs2.getString("code"));
                cinema.setShortName(rs2.getString("short_name"));
                cinema.setDate(sdf.format(rs2.getDate("created_date")));
                cinemaApps.add(cinema);
            }
            int i = 0;
            List<UseCinema> list2 = new ArrayList<>();
            for (Cinema a : cinemas) {
                for (Cinema b : cinemaApps) {
                    if (a.getCode().equalsIgnoreCase(b.getCode()) && !(a.getDate().equals(b.getDate()))) {
                        UseCinema useCinema = new UseCinema();
                        useCinema.setCode(a.getCode());
                        useCinema.setShortName(a.getShortName());
                        useCinema.setDate1(a.getDate());
                        useCinema.setDateApp(b.getDate());
                        list2.add(useCinema);
                        i++;
                    }
                }
            }
            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet("电影信息");

            for(int t = 0;t<list2.size();t++){
                HSSFRow xssfR = sheet.createRow(t);
                for(int x=0;x<4;x++){
                 HSSFCell hssfCell =  xssfR.createCell(x);
                 switch (x){
                     case 0 :
                         hssfCell.setCellValue(list2.get(t).getCode());
                         break;
                     case 1 :
                         hssfCell.setCellValue(list2.get(t).getShortName());
                         break;
                     case 2:
                         hssfCell.setCellValue(list2.get(t).getDate1());
                         break;
                     case 3:
                         hssfCell.setCellValue(list2.get(t).getDateApp());
                         break;
                      default:
                         //...;
                         break;
                 }

                }
            }

            FileOutputStream os = new FileOutputStream("C:\\Users\\admin\\Desktop\\1.xls");
            wb.write(os);
            os.close();

            System.out.println("**********************");
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7、关闭对象，回收数据库资源
            if (rs != null) { //关闭结果集对象
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) { // 关闭数据库操作对象
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt2 != null) { // 关闭数据库操作对象
                try {
                    stmt2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) { // 关闭数据库连接对象
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Setter
    @Getter
    public static class Cinema{
        private String code;

        private String shortName;

        private String date;
    }

    @Setter
    @Getter
    public static class UseCinema{
        private String code;

        private String shortName;

        private String date1;

        private String dateApp;
    }


}
