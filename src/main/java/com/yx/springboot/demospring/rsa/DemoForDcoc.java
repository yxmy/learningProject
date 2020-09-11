package com.yx.springboot.demospring.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Locale;

/**
 * 节目中心
 */
@Slf4j
public class DemoForDcoc {

    public static KeyPair loadKeyPair(final String rsaKeyFileRoot, final String clientNo,
                                      final String keyStorePassword, final String privateKeyPassword) {
        try {
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            final File file = new File(rsaKeyFileRoot + "/" + clientNo + ".pfx");
            final byte[] clientPfx = IOUtils.toByteArray(new FileInputStream(file));
            ks.load(new ByteArrayInputStream(clientPfx),
                    keyStorePassword.toCharArray());

            final PrivateKey privateKey =
                    (PrivateKey) ks.getKey("1", privateKeyPassword.toCharArray());

            final Certificate certificate = ks.getCertificate("1");
            final PublicKey publicKey = certificate.getPublicKey();

            final KeyPair keyPair = new KeyPair(publicKey, privateKey);
            return keyPair;
        } catch (final Exception e) {
            DemoForDcoc.log.error("加载客户端证书失败: " + clientNo, e);
            return null;
        }
    }

    public static String formatOfBusinessTime(final Date dateTime) {
        return DateFormatUtils.format(dateTime, "yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
    }

    public void downloadFile() throws UnsupportedEncodingException, FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //请求生成
        final String clientNo = "vodt";
        final String authKey = "VODT@2017";
        final String businessDate = "2019-09-16";

        //证书保存在resources下的rsakey文件下
        File file = ResourceUtils.getFile("classpath:rsakey");
        final KeyPair keyPair = DemoForDcoc.loadKeyPair(file.getAbsolutePath(), clientNo, "12345678", "12345678");

        final Date timestamp = new Date();
        final String nonce = String.valueOf(RandomUtils.nextLong());

        StringBuffer sb = new StringBuffer();
        sb.append(clientNo).append("#");
        sb.append(authKey).append("#");
        sb.append(formatOfBusinessTime(timestamp)).append("#");
        sb.append(nonce);
        final String signatureStr = sb.toString();
        final byte[] md5 = DigestUtils.md5(signatureStr);
        final java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(md5);
        final byte[] signed = signature.sign();
        final String signatureResult = Base64.encodeBase64String(signed);
        System.out.println("signatureStr: " + signatureStr);
        System.out.println("signature : " + signatureResult);

        String storageFilePath = "D:/";
        String type = "filmsIncrement";
//        String type = "films";
        sb = new StringBuffer();
        sb.append("http://59.252.101.3:10040/dss/data/baseinfo/filminfo/download/");
        sb.append(clientNo).append("/");
        sb.append(formatOfBusinessTime(timestamp)).append("/");
        sb.append(nonce).append("?");
        sb.append("businessDate=").append(businessDate).append("&");
        sb.append("type=").append(type).append("&");
        sb.append("signature=").append(UriUtils.encode(signatureResult, "UTF-8"));
        System.out.println("QueryUrl: " + sb.toString());
        //发送请求下载文件
        String tempFileName = type + ".json.zip";
        sendGet(sb.toString(), storageFilePath, tempFileName);
    }


    /**
     * 请求发送
     * @param url
     * @param filePath
     * @param localFileName
     * @return
     */
    public String sendGet(String url ,String filePath,String localFileName) {
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            //得到输入流
            try(InputStream inputStream = connection.getInputStream()) {
                //获取自己数组
                byte[] getData = readInputStream(inputStream);

                //文件保存位置
                File saveDir = new File(filePath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                File saveFile = new File(filePath + localFileName);
                try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                    fos.write(getData);
                }
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
