package com.yx.springboot.demospring.test;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Demo2{

    public List<String> list = new ArrayList<>();

    public void set(){
        for(int i = 0; i < 100; i++){
            list.add("i" + i);
            if (i == 50 ) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        get();
                    }
                });

            }
            System.out.println("set list size ：" + list.size());
        }
    }

    public void get(){
        ListIterator<String> stringListIterator = list.listIterator();
        while (stringListIterator.hasNext()){
            System.out.println(stringListIterator.next());
            stringListIterator.remove();
            System.out.println("get list size ：" + list.size());
        }
    }

    public static void main(String[] args) {
//        String code = "001203752010";
//        String type = code.substring(code.length() - 4);
//        System.out.println(type);
        try{
            HttpResponse httpResponse;
            try(CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost("http://114.247.46.136/externalApi/movies/getMovies2.do");

                List<NameValuePair> value = new LinkedList<NameValuePair>();
                value.add(new BasicNameValuePair("startOffset", "0"));
                value.add(new BasicNameValuePair("size", "50"));
//                value.add(new BasicNameValuePair("movieType", ""));

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(value, Consts.UTF_8);
                post.setEntity(entity);
                post.setProtocolVersion(HttpVersion.HTTP_1_0);
                httpResponse = client.execute(post);
            }
            if (200==httpResponse.getStatusLine().getStatusCode()) {
                String result = EntityUtils.toString(httpResponse.getEntity());
                System.out.println("返回json字符串为" +  result);
//				if (StringUtils.isNotBlank(result)) {
//					Map<String, Object> jsonMap = JsonUtils.jsonToMap(result.toString());
//					Integer totalNum = (Integer)jsonMap.get("total");
//					String newJson = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);
//					List<Movies> list = JsonUtils.jsonToPojoList(newJson, new TypeReference<List<Movies>>(){});
//					for(Movies movies : list){
//						updateMovie(movies);
//					}
//					for(int i = list.size(); i < totalNum; i+=50){
//						List<Movies> movies = sendPostRequest(i);
//						for(Movies mo : movies){
//							updateMovie(mo);
//						}
//					}
//				}else{
//					MoivesAPIController.log.info("影片请求返回结果为空或为null");
//				}
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void test(){
        File file = new File("");

    }
}
