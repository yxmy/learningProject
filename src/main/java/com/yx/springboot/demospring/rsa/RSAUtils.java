package com.yx.springboot.demospring.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;

/**
 * @author lsnproj
 */
public class RSAUtils {

    public static void main(final String[] args) throws Exception {
        try {
            //            //            final String s =
            //            //                    "K8yMqKKXxCtC8jy/NJ3d3UfabZdmuyqDjzeDgpVO1woNxqe5fGmy8rhppTt/WGxOCzWIxrbHcdBSJub8eE9qXUI8x4n8fStzzfwNa5rOF5sZIVTYx2EbY9IMLif8CiMgTjIhil0H2Kg8weCXArPpHyqmlpjPiLKAVEYvLs8oBqI=";
            //            //            final String n =
            //            //                    "93594321E0FB8CA280101846DA88A7C60A6997577EF93A34147994DC71AEF25B9891B5EB10C7BE63DF9AAF21F461BEE2E073B681C175F63709523165F5FBDD3B31159136AD3A84578B9A7B5B855642993F91147C2C16846EEAC1BE22530378CAE1358C4E527D93068EE37BCCFE4252BF283C7665A2BC179C575F5C0664F3F6CD";
            //            //            final String e = "010001";
            //            //            final RSAPublicKey publickKey = CerUtils.getRSAPublicKeyFromStr(n, e);
            //            //            final byte aaaa[] = RSAUtils.decryptBASE64(s);
            //            //            System.out.println(aaaa.length);
            //            //            final byte[] decByte = RSAUtils.decrypt(aaaa, publickKey);
            //            //            System.out.println("公钥解密后：");
            //            //            final String decStr = new String(decByte);
            //            //            System.out.println(decStr);
            //            //            System.out.println(decStr.length());
            //
            //            //加密
            //            final String message = "my name is lily";
            //            final PrivateKey privateKey =
            //                    CerUtils.getPvkformPfx("d:/reportcer@1@mainKey@53040011@20160816192503.pfx", "12345678");
            //            final byte[] key_message = RSAUtils.encrypt(message, privateKey);
            //            final String base_message = RSAUtils.encryptBASE64(key_message);
            //            System.out.println(base_message);
            //            //解密
            final String s ="33077201mebj1btLfktwAlY5RruFqZeH2+eD7TkjVHKMOn6zGhfoykl9AUnno8POUABop52DUeyMt2Xx0f1b2yiZNzPkRiMHAaumd5oybmT7706z+eNNPNKt0c5M1fRQxf1f75UHLPivPI5cLKBA0scHMLq8W3fVVrSdGhIYsP73oQ2kEwI=";
            final String base64Datas = s.substring(8, s.length());
            final String n1 ="D58747E5F69C8BEA9AA3430CDB7E39D5B72B90AA8A4F71121159BF8840BDED6900A453B435F4A5E1D392E8DFE893098A2D31097235211BC1F87506721A2263A7DBD27B3F767F56A96E0EA91840A4EAF61066D1FD61A621A2CBD0468B7042D081AEED30A69605046A66DCF831314EB032537F7710AE327E76D08ADEC0239B193F";
            final String n = n1.substring(0, 256);
            final String e = "010001";
            final RSAPublicKey publickKey = CerUtils.getRSAPublicKeyFromStr(n1, e);
            final byte aaaa[] = RSAUtils.decryptBASE64(base64Datas);
            System.out.println(aaaa.length);
            final byte[] decByte = RSAUtils.decrypt(aaaa, publickKey);
            System.out.println("公钥解密后：");
            final String decStr = new String(decByte);
            System.out.println(decStr);
            System.out.println(decStr.length());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BASE64解码
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(final String message) throws Exception {
        return Base64Utils.decodeFromString(message);
    }

    /**
     * BASE64编码
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(final byte[] message) throws Exception {
        return Base64Utils.encodeToString(message);
    }

    /**
     * 加密,key可以是公钥，也可以是私钥
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(final String message, final Key key) throws Exception {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }

    /**
     * 解密，key可以是公钥，也可以是私钥，如果是公钥加密就用私钥解密，反之亦然
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(final byte message[], final Key key) throws Exception {
        final Cipher cipher =
                Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    /**
     * 用私钥签名
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] sign(final String message, final PrivateKey key) throws Exception {
        final Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initSign(key);
        signetcheck.update(message.getBytes("ISO-8859-1"));
        return signetcheck.sign();
    }

    /**
     * 用公钥验证签名的正确性
     *
     * @param message
     * @param signStr
     * @return
     * @throws Exception
     */
    public boolean verifySign(final String message, final String signStr, final PublicKey key)
            throws Exception {
        if ((message == null) || (signStr == null) || (key == null)) {
            return false;
        }
        final Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initVerify(key);
        signetcheck.update(message.getBytes("ISO-8859-1"));
        return signetcheck.verify(RSAUtils.toBytes(signStr));
    }

    /**
     * 从文件读取object
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    private Object readFromFile(final String fileName) throws Exception {
        final ObjectInputStream input = new ObjectInputStream(new FileInputStream(
                fileName));
        final Object obj = input.readObject();
        input.close();
        return obj;
    }

    public static String toHexString(final byte[] b) {
        final StringBuilder sb = new StringBuilder(b.length * 2);
        for (final byte element : b) {
            sb.append(RSAUtils.HEXCHAR[(element & 0xf0) >>> 4]);
            sb.append(RSAUtils.HEXCHAR[element & 0x0f]);
        }
        return sb.toString();
    }

    public static final byte[] toBytes(final String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, (2 * i) + 2),
                    16);
        }
        return bytes;
    }

    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
