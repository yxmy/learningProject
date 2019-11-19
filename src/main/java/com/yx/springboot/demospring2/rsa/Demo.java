package com.yx.springboot.demospring2.rsa;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Locale;

import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.util.UriUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo {

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
            Demo.log.error("加载客户端证书失败: " + clientNo, e);
            return null;
        }
    }

    public static String formatOfBusinessTime(final Date dateTime) {
        return DateFormatUtils.format(dateTime, "yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
    }

    public static void main(final String[] argv)
            throws UnsupportedEncodingException, SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        final String clientNo = "ali";
        final String authKey = "ALI@2017";
        final String businessDate = "2019-09-16";
        final String businessMonth = "2019-08";
        final String filmCode = "091201342019";

        final KeyPair keyPair = Demo.loadKeyPair("d:/tmp", clientNo, "12345678", "12345678");

        final Date timestamp = new Date();
        final String nonce = String.valueOf(RandomUtils.nextLong());

        StringBuffer sb = new StringBuffer();
        sb.append(clientNo).append("#");
        sb.append(authKey).append("#");
        sb.append(formatOfBusinessTime(timestamp)).append("#");
//        sb.append(businessDate).append("#");
//        sb.append(filmCode).append("#");
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
        System.out.println();

        sb = new StringBuffer();
        sb.append("http://192.168.20.13:58501/data/baseinfo/cinemaandchain/query/"); //app17 使用curl执行
        sb.append(clientNo).append("/");
        sb.append(formatOfBusinessTime(timestamp)).append("/");
        sb.append(nonce).append("?");
//        sb.append("businessDate=").append(businessDate).append("&");
//        sb.append("filmCode=").append(filmCode).append("&");
        sb.append("page=0&size=50&");
        sb.append("signature=").append(UriUtils.encode(signatureResult, "UTF-8"));
        System.out.println("QueryUrl: " + sb.toString());
        System.out.println();



        final boolean verify = verify(signatureStr, signatureResult, keyPair.getPublic());
        System.out.println(verify);

    }

    public static boolean verify(final String content, final String sign, final PublicKey pubKey) {
        try {
            final byte[] md5 = DigestUtils.md5(content);

            final java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");

            signature.initVerify(pubKey);
            signature.update(md5);
            final boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;
        } catch (final Exception e) {
            log.error("校验签名失败:{}", ExceptionUtils.getFullStackTrace(e));
        }

        return false;
    }

}
