package com.yx.springboot.demospring.rsa;

import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;

/**
 * 二维码解析
 * @author yx
 */
public class QrCode {

    public static void main(String[] args) throws Exception {
        //二维码
        String qrCode = "44131501GQY6SkcMLLtHjUD5VN/m0aKI2WY3ehrGbFkxEKIa/dPKYj1c6rYRmarnWRaAzTZus0z/V4ByZ/Wgt9M4MVMu7roM0W8OWvB12uKilLp7rchHihNmPwFlc1tEPoCRe3maS5FhzUTdTWkw88dZuQOwxtg1kjH3E8d3m6x3/RH7kvk=";
        final String base64Data = qrCode.substring(8);
        //影院公钥
        final String publicKey = "9D643D2DDF9179DB0D2B55D7E224BA10CFD94E9D3EE0EB632FAB97A4BDDDEDF973B668D106AF23CE05E44F7569552CB1A7B1FC0401BAB63C34E51D3AE630CA7A3AD70229C5D0DD6B30E8F6BFE30E3DD8FEFF7ABC6FBB66855AFFED2B3A728EBA4DFE2A5CDC62D6171265285DF64CAAD9088781BC461408AF50DE37FAE023D8A7|010001";
        final String n = publicKey.substring(0, 256);
        final String e = "010001";
        RSAPublicKey publicKey1 = CerUtils.getRSAPublicKeyFromStr(n, e);
        byte [] noBase = RSAUtils.decryptBASE64(base64Data);
        byte [] decryptData  = RSAUtils.decrypt(noBase, publicKey1);
        final String decData = new String(decryptData);
        int qrCodeLength = 107;
        if (qrCodeLength == decData.length()) {
            final String ticketCode = decData.substring(0, 16);
            System.out.println("影票编码========：" + ticketCode);
            final String screenCode = decData.substring(16, 16 + 16).trim();
            System.out.println("影厅编码========：" + screenCode);
            final String filmCode = decData.substring(16 + 16, 16 + 16 + 12).trim();
            System.out.println("影片编码========：" + filmCode);
            final String sessionCode = decData.substring(16 + 16 + 12, 16 + 16 + 12 + 16).trim();
            System.out.println("场次编码========：" + sessionCode);
            final String sessionDatetime = decData.substring(16 + 16 + 12 + 16, 16 + 16 + 12 + 16 + 19).trim().replace("T", " ");
            System.out.println("放映时间========：" + sessionDatetime);
            final String seatCode = decData.substring(16 + 16 + 12 + 16 + 19, 16 + 16 + 12 + 16 + 19 + 16).trim();
            System.out.println("座位编码========：" + seatCode);
            final BigDecimal price = new BigDecimal(decData.substring(16 + 16 + 12 + 16 + 19 + 16, 16 + 16 + 12 + 16 + 19 + 16 + 6).trim());
            System.out.println("影票票价========：" + price);
            final BigDecimal service = new BigDecimal(decData.substring(16 + 16 + 12 + 16 + 19 + 16 + 6, 16 + 16 + 12 + 16 + 19 + 16 + 6 + 6).trim());
            System.out.println("票服务费========：" + service);
        }
    }
}
