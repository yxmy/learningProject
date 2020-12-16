package com.yx.springboot.demospring.testlist.rsa;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;


public class CerUtils {

	/**
	 * 从KEYSTORE文件中取得公钥
	 * 
	 * @param keyStoreFile
	 * @param storeFilePass
	 * @param keyAlias
	 * @return
	 * @throws Exception
	 */
	public PublicKey getPubKeyFromKS(String keyStoreFile, String storeFilePass,
			String keyAlias) throws Exception {

		// 读取密钥是所要用到的工具类
		KeyStore ks;
		// 公钥类所对应的类
		PublicKey pubkey = null;
		// 得到实例对象
		ks = KeyStore.getInstance("JKS");
		FileInputStream fin;
		// 读取JKS文件
		fin = new FileInputStream(keyStoreFile);
		// 读取公钥
		ks.load(fin, storeFilePass.toCharArray());
		Certificate cert = ks.getCertificate(keyAlias);
		pubkey = cert.getPublicKey();

		return pubkey;
	}

	/**
	 * 从公钥证书中读取公钥
	 * 
	 * @param crtFileName
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPubKeyFromCRT(String crtFileName) throws Exception {
		InputStream is = new FileInputStream(crtFileName);
		CertificateFactory cf = CertificateFactory.getInstance("x509");
		Certificate cerCert = cf.generateCertificate(is);
		return cerCert.getPublicKey();
	}

	/**
	 * 通过PFX文件获得公钥
	 * 
	 * @param PFX文件路径
	 * @param PublicKey
	 * @return
	 */
	public static PublicKey getPukformPfx(String strPfx, String strPassword)
			throws Exception {
		PublicKey pubkey = null;
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		String keyAlias = getAlsformPfx(strPfx, strPassword);
		Certificate cert = ks.getCertificate(keyAlias);
		pubkey = cert.getPublicKey();
		return pubkey;
	}
	
	/**
	 * 通过PFX文件获得公钥
	 * 
	 * @param PFX文件路径
	 * @param PublicKey
	 * @return
	 */
	public static Certificate getCerformPfx(String strPfx, String strPassword)
			throws Exception {
		PublicKey pubkey = null;
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		String keyAlias = getAlsformPfx(strPfx, strPassword);
		Certificate cert = ks.getCertificate(keyAlias);
		return cert;
	}
	

	/**
	 * 从KEYSTORE文件中取得私钥
	 * 
	 * @param keyStoreFile
	 * @param storeFilePass
	 * @param keyAlias
	 * @param keyAliasPass
	 * @return
	 * @throws Exception
	 */
	public PrivateKey getPriKeyFromKS(String keyStoreFile,
			String storeFilePass, String keyAlias, String keyAliasPass)
			throws Exception {
		KeyStore ks;
		PrivateKey prikey = null;
		ks = KeyStore.getInstance("JKS");
		FileInputStream fin;
		fin = new FileInputStream(keyStoreFile);
		// 先打开文件
		ks.load(fin, storeFilePass.toCharArray());
		// 通过别名和密码得到私钥
		prikey = (PrivateKey) ks.getKey(keyAlias, keyAliasPass.toCharArray());
		return prikey;
	}

	/**
	 * 通过PFX文件获得私钥
	 * 
	 * @param 文件路径
	 * @param PFX密码
	 * @return PrivateKey
	 */
	public static PrivateKey getPvkformPfx(String strPfx, String strPassword)
			throws Exception {
		PrivateKey prikey = null;
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		String keyAlias = getAlsformPfx(strPfx, strPassword);
		prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
		return prikey;
	}

	/**
	 * 通过PFX文件获得KEYSTORE
	 * 
	 * @param 文件路径
	 * @param PFX密码
	 * @return KeyStore
	 */
	public static KeyStore getKsformPfx(String strPfx, String strPassword)
			throws Exception {
		FileInputStream fis = null;
		Security
				.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
		fis = new FileInputStream(strPfx);
		// If the keystore password is empty(""), then we have to set
		// to null, otherwise it won't work!!!
		char[] nPassword = null;
		if ((strPassword == null) || strPassword.trim().equals("")) {
			nPassword = null;
		} else {
			nPassword = strPassword.toCharArray();
		}
		ks.load(fis, nPassword);
		if (null != fis) {

			fis.close();

		}
		return ks;

	}

	/**
	 * 通过PFX文件获得别名
	 * 
	 * @param 文件路径
	 * @param PFX密码
	 * @return 别名
	 */
	public static String getAlsformPfx(String strPfx, String strPassword)
			throws Exception {
		String keyAlias = null;
		KeyStore ks = getKsformPfx(strPfx, strPassword);
		Enumeration<String> enumas = ks.aliases();
		keyAlias = null;
		// we are readin just one certificate.
		if (enumas.hasMoreElements()) {
			keyAlias = (String) enumas.nextElement();
		}
		return keyAlias;
	}

	/**
	 * 用私钥对文件进行加密
	 * 
	 * @param filePath
	 *            需要加密的文件路径
	 * @param PriKey
	 *            私钥
	 * @param outFilePath
	 *            加密后的文件路径
	 * @param needEncode
	 *            是否需要base64编码(解决传输过程中乱码问题,否则只有文本格式文件解密成功)
	 * @throws Exception
	 */
	public void encryptWithPrv(String filePath, PrivateKey PriKey,
			String outFilePath, boolean needEncode) throws Exception {

		// A.加密原始文件
		File file = new File(filePath);
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream bout = new ByteArrayOutputStream(); // 创建一个字节输出流

		byte[] tmpbuf = new byte[1024];
		int count = 0, count1 = 0;
		while ((count = in.read(tmpbuf)) != -1) {
			bout.write(tmpbuf, 0, count);
			tmpbuf = new byte[1024];
			count1++;
		}
		in.close();
		RSAPrivateKey priKey = (RSAPrivateKey) PriKey;
		byte[] orgData = bout.toByteArray();
		byte[] orgDataBase64;
		if (needEncode) {
			// 1.对文件数据进行Base64转码,解决TXT外的其他格式加密解密后出现乱码的错误的
			orgDataBase64 = Base64.encode(orgData);
		} else {
			orgDataBase64 = orgData;
		}
		// 2. 用私钥对数据流进行加密
		byte[] raw = encrypt(priKey, orgDataBase64);
		// 3.输出加密后的数据流到文件
		if (null != outFilePath && !"".equals(outFilePath)) {
			file = new File(outFilePath);
			OutputStream out = new FileOutputStream(file);
			out.write(raw);
			out.close();
		}
	}

	/**
	 * 用公钥进行解密
	 * @param filePath
	 * @param pubKey
	 * @param needDecode
	 * @param outFilePath
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptWithPub(String filePath, PublicKey pubKey, 
			boolean needDecode, String outFilePath) throws Exception {
		byte[] dataBase64 = null;
		// 从私钥pfx中取得公钥
		RSAPublicKey rsaPubKey = (RSAPublicKey) pubKey;
		byte[] pubModBytes = rsaPubKey.getModulus().toByteArray();
		byte[] pubPubExpBytes = rsaPubKey.getPublicExponent().toByteArray();
		RSAPublicKey recoveryPubKey = generateRSAPublicKey(pubModBytes,
				pubPubExpBytes);
		// 解密文件
		File encFile = new File(filePath);
		// 1. 读取文件到byte流
		byte[] encBytes = Base64.readBytes(encFile);
		// 2.用公钥对byte流进行解密
		byte[] data = decrypt(recoveryPubKey, encBytes);
		// 3.对解密后的数据进行Base64解码
		if (needDecode) {
			dataBase64 = Base64.decode(data);
		} else {
			dataBase64 = data;
		}
		// 4.输出解密后的文件
		if (null != outFilePath && !"".equals(outFilePath)) {
			File file = new File(outFilePath);
			OutputStream out = new FileOutputStream(file);
			out = new FileOutputStream(file);
			out.write(dataBase64);
			out.flush();
			out.close();
		}
		return dataBase64;
	}

	/**
	 * @param modulus
	 * @param publicExponent
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		keyFac = KeyFactory.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// getInstance(String algorithm, Provider provider)
		// 为指定提供程序中的指定算法生成KeyFactory 对象
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		// generatePublic(KeySpec keySpec) 根据所提供的密钥规范（密钥材料）生成公钥对象。
	}

	public static byte[] encrypt(Key key, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// getInstance(String transformation, String provider)
		// 创建一个实现指定转换的 Cipher 对象，该转换由指定的提供程序提供。
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// ENCRYPT_MODE 用于将 cipher 初始化为加密模式的常量。
		// init(int opmode, Key key) 用密钥初始化此 cipher。
		int blockSize = cipher.getBlockSize();
		// getBlockSize
		// public final int getBlockSize()返回块的大小（以字节为单位）
		// 返回：
		// 块的大小（以字节为单位），如果基础算法不是块 cipher，则返回 0
		// 获得加密块大小，如:加密前数据为128个byte，
		// 而key_size=1024 加密块大小为127 byte,加密后为128个byte;因此共有2个加密块，第一个127
		// byte第二個为1個byte
		int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
		int leavedSize = data.length % blockSize;
		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
				: data.length / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (data.length - i * blockSize > 0) {
			if (data.length - i * blockSize > blockSize)
				cipher.doFinal(data, i * blockSize, blockSize, raw, i
						* outputSize);
			else
				cipher.doFinal(data, i * blockSize,
						data.length - i * blockSize, raw, i * outputSize);
			// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把
			// byte[]放到ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到
			// 了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
			i++;
		}
		return raw;
	}

	/**
	 * 解密数据
	 * @param Key  key
	 * @param byte[] raw
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key, byte[] raw) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, key);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int j = 0;
		while (raw.length - j * blockSize > 0) {
			bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
			j++;
		}
		return bout.toByteArray();
	}
	/**
	 * 签名
	 * @param data
	 * @param priKeyByte
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	 public static byte[] sign(byte[] data,  PrivateKey priKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {  
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
//	        // 获取私钥  
//	        KeySpec spec = new PKCS8EncodedKeySpec(priKeyByte);  
//	        // 生成私钥  
//	        PrivateKey priKey = keyFactory.generatePrivate(spec);  
	  
//	        Signature signature = Signature.getInstance("MD5withRSA");  
	        Signature signature = Signature.getInstance("NONEwithRSA");  
	        signature.initSign(priKey);  
	        signature.update(data);  
	  
	        return signature.sign();  
	    } 
	 /**
	  * 解签  
	  * @param data  原文
	  * @param pubKey  公钥
	  * @param sign   签名
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @throws InvalidKeySpecException
	  * @throws InvalidKeyException
	  * @throws SignatureException
	  */
	 public static boolean verify(byte[] data,  PublicKey pubKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,  
     SignatureException {  
//			 KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
//			 // 获取公钥  
//			 KeySpec spec = new X509EncodedKeySpec(pubKeyByte);  
//			 // 生成公钥  
//			 PublicKey pubKey = keyFactory.generatePublic(spec);  
//			 Signature signature = Signature.getInstance("MD5withRSA");  
//		 SHA1withDSA
//		 SHA1withRSA
//		 SHA256withRSA
			 Signature signature = Signature.getInstance("NONEwithRSA");  
			 signature.initVerify(pubKey);  
			 signature.update(data);  
			 return signature.verify(sign);  
}  

	 
	
	 /**
	  * 根据modulus和exponent值生成公钥  
	  * @param modulus  十六进制值的字符
	  * @param exponent 十六进制值的字符
	  * @return
	  */
	 public static RSAPublicKey getRSAPublicKeyFromStr(String modulus,String exponent ){
		 BigInteger m=	new BigInteger(modulus,16);	
		 BigInteger e=	new BigInteger(exponent,16);	
		 try {
			 RSAPublicKeySpec spec = new RSAPublicKeySpec(m,e);
			 KeyFactory factory = KeyFactory.getInstance("RSA");
			 RSAPublicKey pub = (RSAPublicKey)factory.generatePublic(spec);
			 return pub;
		 }
		 catch (Exception e1) {
			 e1.printStackTrace();
		}
		 return null;
	 }
	 
	 
//	 public static void main(String[] args) throws Exception {  
////		 String modulus="DEF30ED9DA052B1E3F967ACB3DDB9C762813B55F0F531C662C3C846A3B592FB871990D57C146B8CE7F7DE02CB271ED5D528540F448669AAD0FBE00D2D23908364A7A7CF3FC73D266036CA041F31846473B58F85FF8B06FD0C997D9A624A0BC001D8558D4D80D37D51F50D297302A251555F24DCC69303565DDF6D734CC9277E1";
////		 String exponent="10001";
////		    
////		 BigInteger m=	new BigInteger(modulus,16);	
////		 BigInteger e=	new BigInteger(exponent,16);	
////		 
//		 try {
//			 RSAPublicKey pubKeyFromKS=(RSAPublicKey)getPukformPfx("d:/client.pfx","12345678");
////			 RSAPublicKeySpec spec = new RSAPublicKeySpec(m,e);
//			 KeyFactory factory = KeyFactory.getInstance("RSA");
////			 PublicKey pub = factory.generatePublic(spec);
////			System.out.println(pubKeyFromKS.equals(pub));
//			 System.out.println();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//		
//		//解签
//		try {
////			String yuanwen="26ed78eddabb7ab97112365a319bd3a9e9071998";
////			String jiegou ="fDsL+eCx0tztI9KXjMXivet5olsDsOHjISWGVvYedDMAt+cfuL9mxDDxoUU9CwnfLZ/NQ5eRZlLH3YTs2inOCDAPHAeDw1GGs4IqImJXat6qTlBMEOIaShLmfgZfU3lwGeVE2L6YUsfW+IviLRbeEXDiCkUQSVDGKkCnQ9h3fuw=";		
//			String yuanwen="2eb3b8f49b12e904974c3f21a787471b4fb9bb8b";
//			String jiegou ="SpmLmwORccF7YzXh3/QFgTNXivHpilYDrHvpF5wxW+fwK7ZF2W64jYH8jUYIqDPwyj/MmAWW0Rale1Ja/jTBTJL9S8bfdNbeDDjgWuSnB7W0kO/+f3o8S7yaHWPIO00Morg9zVL3tUD9PGRxDBhNDbKJtl0Y7R3/hqDLTqbsQI8=";		
//			String m="def30ed9da052b1e3f967acb3ddb9c762813b55f0f531c662c3c846a3b592fb871990d57c146b8ce7f7de02cb271ed5d528540f448669aad0fbe00d2d23908364a7a7cf3fc73d266036ca041f31846473b58f85ff8b06fd0c997d9a624a0bc001d8558d4d80d37d51f50d297302a251555f24dcc69303565ddf6d734cc9277e1";
//			String e="10001";
//			
////			String m="DEF30ED9DA052B1E3F967ACB3DDB9C762813B55F0F531C662C3C846A3B592FB871990D57C146B8CE7F7DE02CB271ED5D528540F448669AAD0FBE00D2D23908364A7A7CF3FC73D266036CA041F31846473B58F85FF8B06FD0C997D9A624A0BC001D8558D4D80D37D51F50D297302A251555F24DCC69303565DDF6D734CC9277E1";
////			String e="10001";
//			RSAPublicKey pubKey=CerUtils.getRSAPublicKeyFromStr(m,e);
//			 boolean j= CerUtils.verify(yuanwen.getBytes(), pubKey, Base64.decode(jiegou.getBytes()));
//			 System.out.println(j);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//		//签名
//		PrivateKey priKeyFromKs =CerUtils.getPvkformPfx("d:/testclient1.pfx","12345678");
//		String dsSignatureValue=new String (Base64.encode(CerUtils.sign("2eb3b8f49b12e904974c3f21a787471b4fb9bb8b".getBytes(),priKeyFromKs)));
//		System.out.println(dsSignatureValue);
//		
////		
////		 System.out.println(Sha1Utils.SHA1("123"));
////		 String a="wS8p3nRCLMq8PsdbqpCXQvOkqqTsebSbODutSe/7WIWngHg2YbfQIjL54zVAIR3PsrrP+ff3RSBVQ6pd7zf0MbGwhwyJ6WA7p5E7/4B0dzpko/6TAlqlU7H7IqRaAph8gsj54HL5wcTyEtcU8Y1sS99ApuMppvj47eVGcnZ/wTg=";
////		 String b="ypIDRUAxxAmOX0+dIfBGjRdUNaMQmnzEZklBEiktSsf8SUnant0bu0zf8oTSVz7TgxDl36cX4FlkqLOZrgWcgq6n49XfBK6vOWpXhjnfT3YfwaQNJYScfuBbpkB8b/qpdV5GyAhn4TJNNY6lyKSUuDB9boKNWc+J5AQpbFZOkb4=";
////		 String c="ypIDRUAxxAmOX0+dIfBGjRdUNaMQmnzEZklBEiktSsf8SUnant0bu0zf8oTSVz7TgxDl36cX4FlkqLOZrgWcgq6n49XfBK6vOWpXhjnfT3YfwaQNJYScfuBbpkB8b/qpdV5GyAhn4TJNNY6lyKSUuDB9boKNWc+J5AQpbFZOkb4=";
////		 String d="10001";
////			
////			 System.out.println(Sha1Utils.SHA1(b));
////			RSAPublicKey pubKey=CerUtils.getRSAPublicKeyFromStr(c,d);
////			boolean j= CerUtils.verify("5f3434fd21529ff2e6ffdb47b8f443217a2b1e82".getBytes(), pubKey, b.getBytes());
////			System.out.println(j);
////			
////			
////				PrivateKey priKeyFromKs =CerUtils.getPvkformPfx("d:/testclient1.pfx","12345678");
////				String dsSignatureValue=new String (Base64.encode(CerUtils.sign("5f3434fd21529ff2e6ffdb47b8f443217a2b1e82".getBytes(),priKeyFromKs)));
////				System.out.println(dsSignatureValue);
////				
////				System.out.println(new String (new BASE64Encoder().encode(CerUtils.sign(b.getBytes(),priKeyFromKs))));
////				
////				System.out.println(DigestUtils.md5Hex(CerUtils.sign(b.getBytes(),priKeyFromKs)));
////				System.out.println(new String (Base64.encode("yuanzhang\r\nyuanzhang".getBytes())));
////				System.out.println("yuanzhangyuanzhang");
////				System.out.println(new String (Base64.encode(CerUtils.sign("yuanzhang\nyuanzhang".getBytes(),priKeyFromKs))));
////				 RSAPublicKey pubKeyFromKS=(RSAPublicKey)getPukformPfx("d:/testclient1.pfx","12345678");
////			
////				
////				 boolean j1= CerUtils.verify("yuanzhang\nyuanzhang".getBytes(), pubKey,Base64.decode( Base64.encode(CerUtils.sign("yuanzhang\nyuanzhang".getBytes(),priKeyFromKs))));
////					System.out.println(j1);
//////	    	try {
////	    		PublicKey pubKeyFromCer=getPubKeyFromCRT("d:/cacert.pem");
////				getKsformPfx("d:/testclient1.pfx","12345678");
////				PrivateKey priKeyFromKs =getPvkformPfx("d:/testclient1.pfx","12345678");
////				PublicKey pubKeyFromKS=getPukformPfx("d:/testclient1.pfx","12345678");
//////	    	System.out.println(new String (Base64.encode(bs)));
//////	    	System.out.println(new String (decrypt(pubKeyFromKS,bs)));
////	    	
////				
////			        
////			
////			byte[] bs1=sign("abc".getBytes(),priKeyFromKs);
////			System.out.println(verify("abc".getBytes(),pubKeyFromKS,bs1));
////	    	String bsText=new String (Base64.encode(sign("abc".getBytes(),priKeyFromKs)));
////	    	System.out.println(bsText);
////	    	
////	    	//验签php的结果
//////	    	String atext="Fb5gHrHtPouW61P8XzWyoJhT6fOXM8GoedVG/9EfDpKgo7rBW381FHyhKisRzia8SBxxI0zw+LcitiAqnBJatF7ntuzz+mbwwyTxfiBBsAar5nzLVOgVN5co7RFqKwGQ4SltcgGjjmLEbRA2430m3d0XCnhPkrXFedUUvJL4ofI=";
//////	    	byte[] bs2=Base64.decode(atext.getBytes());
//////	    	System.out.println(verify("abc".getBytes(),pubKeyFromKS,bs2));
////	    	
////	    	//先sha1 再rsa加密
////	    	byte[] bs=encrypt(priKeyFromKs,Sha1Utils.SHA1("abc").getBytes());
////	    	System.out.println(new String (Base64.encode(bs)));
////	    	
////	    	
////	    	} catch (Exception e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////	    	
////	    	
////	    	CertificateFactory cf;
////			try {
////				//获取ca为客户端颁发的证书
////				Certificate c1=getCerformPfx("d:/testclient1.pfx","12345678");
////				cf = CertificateFactory.getInstance("X.509");
////				//获取ca自己的证书
////				FileInputStream in2=new FileInputStream("d:/cacert.pem");
////		    	Certificate cac=cf.generateCertificate(in2);
////		    	PublicKey pbk=cac.getPublicKey();
////		    	//校验证书是否合法
////		    	boolean pass=false;
////		    	try {
////		    		c1.verify(pbk);
////		    		pass=true;
////				} catch (Exception e) {
////					e.printStackTrace();
////					pass=false;
////				}
////		    	
////		    	in2.close();
////			} catch (Exception e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////	    	
//	    	
//	    }  
	 
	 
}

    
   
