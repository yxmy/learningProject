package com.yx.springboot.demospring.testlist.pullbusstation;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PullBusStation {

    HttpClient httpClient = HttpClients.createDefault();
    public String searchPage = "http://bus.mapbar.com/taiyuan/poi_";

    public List<String> getInfo(String index) throws IOException, InterruptedException {
        List<String> list = new ArrayList<>();
        StringBuilder sb = null;
        HttpGet httpGet = new HttpGet(searchPage + index);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        if(200 == httpResponse.getStatusLine().getStatusCode()){
            PullBusStation.log.info("信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponse.getEntity()));
            final Document indexDoc = Jsoup.parse(String.valueOf(sb));
            final Elements elements = indexDoc.select(".ChinaTxt>dd>a");
            for(Element element : elements){
                String value = element.attr("href");
                String text = element.text();
                list.add("太原市" + text + "-公交站");
            }
        }
        return list;
    }

    public void exportExcel(List<String> list) throws FileNotFoundException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("公交信息");
        for(int t = 0;t<list.size();t++){
            HSSFRow xssfR = sheet.createRow(t);
            HSSFCell hssfCell =  xssfR.createCell(0);
            hssfCell.setCellValue(list.get(t));
        }
        FileOutputStream os = new FileOutputStream("/home/pc-yx/桌面/公交信息.xls");
        try {
            wb.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pullInfo(){
        try {
            List<String> list = new ArrayList<>();
            String arr [] = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R",
            "S","T","W","X","Y","Z"};
            for(String index : arr){
                final int i = (int)(1000 + Math.random()*3000);
                PullBusStation.log.info(i / 1000 + "秒之后开始进行下次抓取");
                Thread.sleep(i);
                List<String> datas = getInfo(index);
                list.addAll(datas);
            }
            exportExcel(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
