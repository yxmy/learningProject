package com.yx.mygroup.pulldata.pullInfFrom5i5j;

import com.yx.mygroup.model.HouseInfoBeanOfRent;
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
import org.springframework.boot.CommandLineRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Component
public class PullInfFrom5i5j implements CommandLineRunner {

    HttpClient httpClient = HttpClients.createDefault();

    public String searchPage = "https://ty.5i5j.com/zufang/n";
    private String detailPage = "https://ty.5i5j.com";

    @Test
    public void start(){

            List<HouseInfoBeanOfRent> houseInfoBeanOfRentList = new ArrayList<>();
            for(int i=1; i<=114; i++){
                try{
                List<HouseInfoBeanOfRent> list = getInfo(i);
                houseInfoBeanOfRentList.addAll(list);
                final int it = (int)(1000 + Math.random()*4000);
                PullInfFrom5i5j.log.info("休息:" + it / 1000 + "秒后进行第" + (i+1) + "页采集");
                Thread.sleep(it);
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            if(houseInfoBeanOfRentList.size()>0){
                PullInfFrom5i5j.log.info("房屋信息抓取成功，开始导出Excel");
                try{
                    exportExcel(houseInfoBeanOfRentList);
                }catch (Exception e){
                    e.printStackTrace();
                }
                PullInfFrom5i5j.log.info("****************导出成功****************");
            }

    }
    /**
     * 列表页查询
     * @throws IOException
     */
    public List<HouseInfoBeanOfRent> getInfo(int index) throws IOException, InterruptedException {
        List<HouseInfoBeanOfRent> list = new ArrayList<>();
        StringBuilder sb = null;
        HttpGet httpGet = new HttpGet(searchPage + index);
        httpGet.setHeader("UserEntity-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        if(200 == httpResponse.getStatusLine().getStatusCode()){
            PullInfFrom5i5j.log.info("第" + index + "页房屋列表页信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponse.getEntity()));
            final Document indexDoc = Jsoup.parse(String.valueOf(sb));
            final Elements elements = indexDoc.select(".pList>li");
            boolean flag = false;
            for(Element element : elements){
                HouseInfoBeanOfRent bean = new HouseInfoBeanOfRent();
                String value = element.select(".listImg>a").attr("href");

                final int i = (int)(3000 + Math.random()*3000);
                PullInfFrom5i5j.log.info(i / 1000 + "秒之后开始抓取房屋详细信息");
                Thread.sleep(i);

                flag = this.getHouseDetail(detailPage + value, bean);
                list.add(bean);
                if(!flag){
                    break;
                }
            }
        }
        return list;
    }


    //一级跳转，查询二手房信息
    public boolean getHouseDetail(String url, HouseInfoBeanOfRent bean) throws IOException, InterruptedException {
        StringBuilder sb = null;
        HttpGet httpGetInfo = new HttpGet(url);
        httpGetInfo.setHeader("UserEntity-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        HttpResponse httpResponseHouseDetail = httpClient.execute(httpGetInfo);
        if(200 == httpResponseHouseDetail.getStatusLine().getStatusCode()){
            PullInfFrom5i5j.log.info("房屋信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponseHouseDetail.getEntity()));
            final Document document = Jsoup.parse(String.valueOf(sb));
            //*****************第一块数据*******************
            Elements elements = document.select(".housesty>.fl>.jlquannei");//会有四个元素
            if(elements != null && elements.size() >= 4){
                Element element1 = elements.get(0);
                String rentPrice = element1.select(".jlinfo").text();//租金
                bean.setRentPrice(rentPrice);
                Element element2 = elements.get(1);
                String houseType = element2.select(".jlinfo").text();//户型
                bean.setHouseType(houseType);
                Element element3 = elements.get(2);
                String coveredArea = element3.select(".jlinfo").text();//面积
                bean.setCoveredArea(coveredArea);
            }
            //*****************第二块数据*******************
            Elements elements1 = document.select(".zushous>ul>li");//会有八个元素
            if(elements1 != null && elements1.size() >= 0){
                for(Element element : elements1){
                    String value = element.select("li").text();
                    this.setBeanValue(value, bean);
                }
            }
            //*****************第三块数据*******************
            Elements elements2 = document.select(".fytese>li");//会有五个元素
            if(elements2 != null && elements2.size() >= 5){
                Element element1 = elements2.get(3);
                String supportingFacilities = element1.select("label").text();//周边配套
                bean.setSupportingFacilities(supportingFacilities);
                Element element2 = elements2.get(2);
                String traffic = element2.select("label").text();//交通出行
                bean.setTraffic(traffic);
            }
            PullInfFrom5i5j.log.info("房屋信息解析完成......");
            return true;
        }else{
            return false;
        }
    }

    private void setBeanValue(String value, HouseInfoBeanOfRent bean){
        if(value.contains("小区")){
            bean.setCommunityName(value);
        }else if(value.contains("楼层")){
            bean.setFloor(value);
        }else if(value.contains("朝向")){
            bean.setOrientation(value);
        }else if(value.contains("装修")){
            bean.setRenovation(value);
        }else if(value.contains("楼型")){
            bean.setBuildingType(value);
        }else if(value.contains("年代")){
            //
        }else if(value.contains("出租方式")){
            bean.setRentingStyle(value);
        }else if(value.contains("看房时间")){
            //
        }else if(value.contains("商圈")){
            bean.setTradingArea(value);
        }else{
            //
        }
    }

    public void exportExcel(List<HouseInfoBeanOfRent> list) throws FileNotFoundException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("房源信息");
        HSSFRow hssfRowTitle = sheet.createRow(0);
        setExcelTitle(hssfRowTitle);
        for(int t = 0;t<list.size();t++){
            HSSFRow xssfR = sheet.createRow(t+1);
            for(int x=0;x<23;x++){
                HSSFCell hssfCell =  xssfR.createCell(x);
                switch (x){
                    case 0 :
                        hssfCell.setCellValue(list.get(t).getRentPrice());
                        break;
                    case 1 :
                        hssfCell.setCellValue(list.get(t).getCoveredArea());
                        break;
                    case 2:
                        hssfCell.setCellValue(list.get(t).getHouseType());
                        break;
                    case 3:
                        hssfCell.setCellValue(list.get(t).getCommunityName());
                        break;
                    case 4 :
                        hssfCell.setCellValue(list.get(t).getFloor());
                        break;
                    case 5 :
                        hssfCell.setCellValue(list.get(t).getOrientation());
                        break;
                    case 6:
                        hssfCell.setCellValue(list.get(t).getRenovation());
                        break;
                    case 7:
                        hssfCell.setCellValue(list.get(t).getBuildingType());
                        break;
                    case 8 :
                        hssfCell.setCellValue(list.get(t).getRentingStyle());
                        break;
                    case 9 :
                        hssfCell.setCellValue(list.get(t).getTradingArea());
                        break;
                    case 10:
                        hssfCell.setCellValue(list.get(t).getSupportingFacilities());
                        break;
                    case 11:
                        hssfCell.setCellValue(list.get(t).getTraffic());
                        break;
                    default:
                        //...;
                        break;
                }

            }
        }

        FileOutputStream os = new FileOutputStream("C:\\Users\\admin\\Desktop\\房源信息.xls");
        try {
            wb.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExcelTitle(HSSFRow xssfTitle){
        HSSFCell hssfCell0 = xssfTitle.createCell(0);
        hssfCell0.setCellValue("总价");
        HSSFCell hssfCell1 = xssfTitle.createCell(1);
        hssfCell1.setCellValue("面积");
        HSSFCell hssfCell2 = xssfTitle.createCell(2);
        hssfCell2.setCellValue("户型");
        HSSFCell hssfCell3 = xssfTitle.createCell(3);
        hssfCell3.setCellValue("小区名称");
        HSSFCell hssfCell4 = xssfTitle.createCell(4);
        hssfCell4.setCellValue("楼层");
        HSSFCell hssfCell5 = xssfTitle.createCell(5);
        hssfCell5.setCellValue("朝向");
        HSSFCell hssfCell6 = xssfTitle.createCell(6);
        hssfCell6.setCellValue("装修");
        HSSFCell hssfCell7 = xssfTitle.createCell(7);
        hssfCell7.setCellValue("楼型");
        HSSFCell hssfCell8 = xssfTitle.createCell(8);
        hssfCell8.setCellValue("出租方式");
        HSSFCell hssfCell9 = xssfTitle.createCell(9);
        hssfCell9.setCellValue("商圈");
        HSSFCell hssfCell10 = xssfTitle.createCell(10);
        hssfCell10.setCellValue("配套设施");
        HSSFCell hssfCell11 = xssfTitle.createCell(11);
        hssfCell11.setCellValue("交通");
    }

    @Override
    public void run(String... strings) throws Exception {
        this.start();
    }
}
