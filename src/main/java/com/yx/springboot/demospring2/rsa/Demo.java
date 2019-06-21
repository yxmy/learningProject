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
        final String clientNo = "jhyh";
        final String authKey = "JHYH@2019";

        final KeyPair keyPair = Demo.loadKeyPair("/home/pc-yx/rsa", clientNo, "12345678", "12345678");

        final Date timestamp = new Date(1495378612913L);
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
        System.out.println();

        sb = new StringBuffer();
        sb.append("http://59.252.101.3:10049/data/qrcode/query/");
        sb.append(clientNo).append("/");
        sb.append(formatOfBusinessTime(timestamp)).append("/");
        sb.append(nonce).append("?");
        sb.append("signature=").append(UriUtils.encode(signatureResult, "UTF-8"));

        System.out.println("QueryUrl: " + sb.toString());
        System.out.println();

    }

}
