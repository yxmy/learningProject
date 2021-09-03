package com.yx.springboot.demospring.testlist.pullInfFromSoFang;

import com.mysql.cj.util.StringUtils;
import com.yx.springboot.demospring.testlist.model.HouseInfoBean;
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
public class PullInfFromSoFang implements CommandLineRunner {

    HttpClient httpClient = HttpClients.createDefault();

    public String searchPage = "http://ty.sofang.com/esfsale/area/bl";
    private String detailPage = "http://ty.sofang.com";

    @Test
    public void start(){
        try{
            List<HouseInfoBean> houseInfoBeanList = new ArrayList<>();
            for(int i=1; i<=20; i++){
                List<HouseInfoBean> list = getInfo(i);
                houseInfoBeanList.addAll(list);
                final int it = (int)(1000 + Math.random()*3000);
                PullInfFromSoFang.log.info("休息:" + it / 1000 + "秒后进行下一次列表页采集");
                Thread.sleep(it);
            }
            if(houseInfoBeanList.size()>0){
                PullInfFromSoFang.log.info("房屋信息抓取成功，开始导出Excel");
                exportExcel(houseInfoBeanList);
                System.out.println("****************导出成功****************");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 列表页查询
     * @throws IOException
     */
    public List<HouseInfoBean> getInfo(int index) throws IOException, InterruptedException {
        List<HouseInfoBean> list = new ArrayList<>();
        StringBuilder sb = null;
        HttpGet httpGet = new HttpGet(searchPage + index);
        httpGet.setHeader("UserEntity-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        if(200 == httpResponse.getStatusLine().getStatusCode()){
            PullInfFromSoFang.log.info("第" + index + "页房屋列表页信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponse.getEntity()));
            final Document indexDoc = Jsoup.parse(String.valueOf(sb));
            final Elements elements = indexDoc.select(".list_free>dl");
            for(Element element : elements){
                HouseInfoBean bean = new HouseInfoBean();
                String value = element.select("dt>a").attr("href");

                final int i = (int)(1000 + Math.random()*3000);
                PullInfFromSoFang.log.info(i / 1000 + "秒之后开始抓取房屋详细信息");
                Thread.sleep(i);

                this.getHouseDetail(detailPage + value, bean);
                list.add(bean);
            }
        }
        return list;
    }


    //一级跳转，查询二手房信息
    public void getHouseDetail(String url, HouseInfoBean bean) throws IOException, InterruptedException {
        StringBuilder sb = null;
        HttpGet httpGetInfo = new HttpGet(url);
        httpGetInfo.setHeader("UserEntity-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        HttpResponse httpResponseHouseDetail = httpClient.execute(httpGetInfo);
        if(200 == httpResponseHouseDetail.getStatusLine().getStatusCode()){
            PullInfFromSoFang.log.info("房屋信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponseHouseDetail.getEntity()));
            final Document document = Jsoup.parse(String.valueOf(sb));
            Elements elements = document.select(".info_r");
            if(elements != null && elements.size() > 0){
                Element element = elements.get(0);//因为页面只存在一个值
                String totalPrice = element.select(".total").text();//总价
                bean.setTotalPrice(totalPrice);
                String averagePrice = element.select(".averages").text();//均价
                bean.setAvaPrice(averagePrice);
                Elements elements1 = element.select(".info>dl");
                if(elements1 != null && elements1.size() >= 3){//不出意外页面上应该存在三个元素
                    String houseType = elements1.get(0).select("dt").text();//户型
                    bean.setHouseType(houseType);
                    String floor = elements1.get(0).select("dd").text();//楼层
                    bean.setFloor(floor);
                    String coveredArea = elements1.get(1).select("dt").text();//建筑面积
                    bean.setCoveredArea(coveredArea);
                    String renovation = elements1.get(1).select("dd").text();//装修
                    bean.setRenovation(renovation);
                    String orientation = elements1.get(2).select("dt").text();//朝向
                    bean.setOrientation(orientation);
                    String buildYear = elements1.get(2).select("dd").text();//建筑年代
                    bean.setBuildYear(buildYear);
                }
                String estateName = element.select(".colorR").attr("title");//楼盘名称
                bean.setEstateName(estateName);
                //楼盘跳转地址
                String address = element.select(".address").attr("title");//地址
                bean.setAddress(address);
                String estateUrl = element.select(".colorR").attr("href");
                PullInfFromSoFang.log.info("房屋信息解析完成......");
                if(!StringUtils.isNullOrEmpty(estateUrl)){

                    final int i = (int)(1000 + Math.random()*6000);
                    PullInfFromSoFang.log.info(i / 1000 + "秒后开始抓取房屋所在楼盘信息...");
                    Thread.sleep(i);

                    getEstateInfo(detailPage + estateUrl, bean);
                }
            }
        }
    }

    //二级跳转，查询楼盘信息
    public void getEstateInfo(String url, HouseInfoBean bean) throws IOException {
        StringBuilder sb = null;
        HttpGet httpGetInfo = new HttpGet(url);
        httpGetInfo.setHeader("UserEntity-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        HttpResponse httpResponseHouseDetail = httpClient.execute(httpGetInfo);
        if(200 == httpResponseHouseDetail.getStatusLine().getStatusCode()) {
            PullInfFromSoFang.log.info("楼盘信息返回成功。开始解析...");
            sb = new StringBuilder(EntityUtils.toString(httpResponseHouseDetail.getEntity()));
            final Document document = Jsoup.parse(String.valueOf(sb));
            Elements elements = document.select(".real_detail>ul>li");//页面样式固定
            String greeningRate = elements.get(6).select("span").text();//绿化率
            bean.setGreeningRate(greeningRate);
            String residentialCategory = elements.get(0).select("span").text();//住宅类别
            bean.setResidentialCategory(residentialCategory);
            String developers = elements.get(12).select("span").text();//开发商
            bean.setDevelopers(developers);
            String propertyFee = elements.get(11).select("span").text();//物业费
            bean.setPropertyFee(propertyFee);
            String propertyCompany = elements.get(13).select("span").text();//物业公司
            bean.setPropertyCompany(propertyCompany);
            String plotRatio = elements.get(7).select("span").text();//容积率
            bean.setPlotRatio(plotRatio);
            String totalFamily = elements.get(5).select("span").text();//总户数
            bean.setTotalFamily(totalFamily);
            String villageArea = elements.get(4).select("span").text();//占地面积
            bean.setVillageArea(villageArea);
            String structure = elements.get(1).select("span").text();//建筑结构
            bean.setStructure(structure);
            PullInfFromSoFang.log.info("楼盘信息解析完成......");
        }
    }


    public void exportExcel(List<HouseInfoBean> list) throws FileNotFoundException {
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
                        hssfCell.setCellValue(list.get(t).getTotalPrice());
                        break;
                    case 1 :
                        hssfCell.setCellValue(list.get(t).getAvaPrice());
                        break;
                    case 2:
                        hssfCell.setCellValue(list.get(t).getCoveredArea());
                        break;
                    case 3:
                        hssfCell.setCellValue(list.get(t).getHouseType());
                        break;
                    case 4 :
                        hssfCell.setCellValue(list.get(t).getBuildYear());
                        break;
                    case 5 :
                        hssfCell.setCellValue(list.get(t).getOrientation());
                        break;
                    case 6:
                        hssfCell.setCellValue(list.get(t).getFloor());
                        break;
                    case 7:
                        hssfCell.setCellValue(list.get(t).getBuildingCategory());
                        break;
                    case 8 :
                        hssfCell.setCellValue(list.get(t).getRenovation());
                        break;
                    case 9 :
                        hssfCell.setCellValue(list.get(t).getStructure());
                        break;
                    case 10:
                        hssfCell.setCellValue(list.get(t).getPropertyRights());
                        break;
                    case 11:
                        hssfCell.setCellValue(list.get(t).getResidentialCategory());
                        break;
                    case 12 :
                        hssfCell.setCellValue(list.get(t).getSupportingFacilities());
                        break;
                    case 13 :
                        hssfCell.setCellValue(list.get(t).getEstateName());
                        break;
                    case 14:
                        hssfCell.setCellValue(list.get(t).getGreeningRate());
                        break;
                    case 15:
                        hssfCell.setCellValue(list.get(t).getAddress());
                        break;
                    case 16 :
                        hssfCell.setCellValue(list.get(t).getDevelopers());
                        break;
                    case 17 :
                        hssfCell.setCellValue(list.get(t).getPropertyFee());
                        break;
                    case 18:
                        hssfCell.setCellValue(list.get(t).getPropertyCompany());
                        break;
                    case 19:
                        hssfCell.setCellValue(list.get(t).getPlotRatio());
                        break;
                    case 20 :
                        hssfCell.setCellValue(list.get(t).getTotalFamily());
                        break;
                    case 21 :
                        hssfCell.setCellValue(list.get(t).getVillageArea());
                        break;
                    case 22:
                        hssfCell.setCellValue(list.get(t).getTransitState());
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
        hssfCell1.setCellValue("均价");
        HSSFCell hssfCell2 = xssfTitle.createCell(2);
        hssfCell2.setCellValue("建筑面积");
        HSSFCell hssfCell3 = xssfTitle.createCell(3);
        hssfCell3.setCellValue("户型");
        HSSFCell hssfCell4 = xssfTitle.createCell(4);
        hssfCell4.setCellValue("年代");
        HSSFCell hssfCell5 = xssfTitle.createCell(5);
        hssfCell5.setCellValue("朝向");
        HSSFCell hssfCell6 = xssfTitle.createCell(6);
        hssfCell6.setCellValue("楼层");
        HSSFCell hssfCell7 = xssfTitle.createCell(7);
        hssfCell7.setCellValue("建筑类别");
        HSSFCell hssfCell8 = xssfTitle.createCell(8);
        hssfCell8.setCellValue("装修");
        HSSFCell hssfCell9 = xssfTitle.createCell(9);
        hssfCell9.setCellValue("结构");
        HSSFCell hssfCell10 = xssfTitle.createCell(10);
        hssfCell10.setCellValue("产权性质");
        HSSFCell hssfCell11 = xssfTitle.createCell(11);
        hssfCell11.setCellValue("住宅类别");
        HSSFCell hssfCell12 = xssfTitle.createCell(12);
        hssfCell12.setCellValue("配套设施");
        HSSFCell hssfCell13 = xssfTitle.createCell(13);
        hssfCell13.setCellValue("楼盘名称");
        HSSFCell hssfCell14 = xssfTitle.createCell(14);
        hssfCell14.setCellValue("绿化率");
        HSSFCell hssfCell15 = xssfTitle.createCell(15);
        hssfCell15.setCellValue("地址");
        HSSFCell hssfCell16 = xssfTitle.createCell(16);
        hssfCell16.setCellValue("开发商");
        HSSFCell hssfCell17 = xssfTitle.createCell(17);
        hssfCell17.setCellValue("物业费");
        HSSFCell hssfCell18 = xssfTitle.createCell(18);
        hssfCell18.setCellValue("物业公司");
        HSSFCell hssfCell19 = xssfTitle.createCell(19);
        hssfCell19.setCellValue("容积率");
        HSSFCell hssfCell20 = xssfTitle.createCell(20);
        hssfCell20.setCellValue("总户数");
        HSSFCell hssfCell21 = xssfTitle.createCell(21);
        hssfCell21.setCellValue("小区占地面积");
        HSSFCell hssfCell22 = xssfTitle.createCell(22);
        hssfCell22.setCellValue("公交状况");
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.start();
    }
}
