package com.zhongan.scorpoin.signature;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;

import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.common.ProtocolParameterEnum;
import com.zhongan.scorpoin.signature.codec.Base64;

/**
 * 众安开放平台签名工具，后期需要将jar包提供给接入的开发者<br/>
 * 请确保该工具引用的类均在com.zhongan.openapi.security.signature包里<br/>
 * 
 * @author linwusheng
 */
public class SignatureUtils {

    /**
     * 加密并签名<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥，privateKey是指开发者自己的私钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥，privateKey是指众安的私钥<br>
     * 
     * @param bizContent
     * @param publicKey
     * @param privateKey
     * @param charset
     * @param isEncrypt
     * @param isSign
     * @return 加密、签名后的K-V对
     * @throws Exception
     */
    public static String encryptAndSign(String bizContent, String publicKey, String privateKey, String charset,
                                        boolean isEncrypt, boolean isSign) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = SignConstants.CHARSET_GBK;
        }
        if (isEncrypt) {
            //加密
            String encrypted = rsaEncrypt(bizContent, publicKey, charset);
            sb.append("bizContent=").append(encrypted);
            if (isSign) {
                String sign = rsaSign(encrypted, privateKey, charset);
                sb.append("&sign=").append(sign);
            }
        } else if (isSign) {
            // 不加密，但需要签名
            sb.append("bizConent=").append(bizContent);
            String sign = rsaSign(bizContent, privateKey, charset);
            sb.append("&sign=").append(sign);
        }
        return sb.toString();
    }

    /**
     * 加密并签名<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥，privateKey是指开发者自己的私钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥，privateKey是指众安的私钥<br>
     * 
     * @param map
     * @param publicKey
     * @param privateKey
     * @param charset
     * @param isEncrypt
     * @param isSign
     * @return
     * @throws Exception
     */
    public static Map<String, Object> encryptAndSign(Map<String, Object> map, String publicKey, String privateKey,
                                                     String charset, boolean isEncrypt, boolean isSign)
            throws Exception {
        Set<String> keySet = map.keySet();
        Map<String, Object> businessParamMap = new HashMap<String, Object>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        List<String> codeList = ProtocolParameterEnum.getCodeList();
        for (String key : keySet) {
            if (!codeList.contains(key)) {
                businessParamMap.put(key, map.get(key));
            } else {
                newMap.put(key, map.get(key));
            }
        }

        if (StringUtils.isEmpty(charset)) {
            charset = SignConstants.CHARSET_UTF_8;
        }
        if (isEncrypt) {
            //加密
            String encrypted = rsaEncrypt(businessParamMap, publicKey, charset);
            newMap.put(ProtocolParameterEnum.BIZ_CONTENT.getCode(), encrypted);
            if (isSign) {
                String sign = rsaSign(newMap, privateKey, charset);
                newMap.put(ProtocolParameterEnum.SIGN.getCode(), sign);
            }
        } else if (isSign) {
            // 不加密，但需要签名
            newMap.put(ProtocolParameterEnum.BIZ_CONTENT.getCode(), getSignContent(businessParamMap));
            String sign = rsaSign(newMap, privateKey, charset);
            newMap.put(ProtocolParameterEnum.SIGN.getCode(), sign);
        }

        return newMap;
    }

    /**
     * 验签并解密
     * <p>
     * 对于<b>开发者</b>，publicKey是指众安的公钥，privateKey是指开发者自己的私钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥，privateKey是指众安的私钥<br>
     * 
     * @param params
     * @param publicKey 公钥
     * @param privateKey 私钥
     * @param isCheckSign 是否验签
     * @param isDecrypt 是否解密
     * @return 解密后明文，验签失败则异常抛出
     * @throws Exception
     */
    public static String checkSignAndDecrypt(Map<String, Object> params, String publicKey, String privateKey,
                                             boolean isCheckSign, boolean isDecrypt) throws Exception {
        String bizContent = (String) params.get(ProtocolParameterEnum.BIZ_CONTENT.getCode());
        String sign = (String) params.get(ProtocolParameterEnum.SIGN.getCode());
        params.remove(ProtocolParameterEnum.SIGN.getCode());
        String content = getSignContent(params);
        System.out.println("签名字符串是：" + content);
        String charset = (String) params.get(ProtocolParameterEnum.CHARSET.getCode());
        if (StringUtils.isEmpty(charset)) {
            charset = SignConstants.CHARSET_UTF_8;
        }
        if (isCheckSign) {
            checkRSASign(sign, content, publicKey, charset);
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, privateKey, charset);
        }

        return bizContent;
    }

    /**
     * RSA加密<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥<br>
     * 
     * @param map
     * @param publicKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static String rsaEncrypt(Map<String, Object> map, String publicKey, String charset) throws Exception {
        String bizContent = getSignContent(map);
        System.out.println("待加密的字符创：" + bizContent);
        return rsaEncrypt(bizContent, publicKey, charset);
    }

    /**
     * 公钥加密<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥，privateKey是指开发者自己的私钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥，privateKey是指众安的私钥<br>
     * 
     * @param content 待加密内容
     * @param publicKey 公钥
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     * @throws Exception
     */
    public static String rsaEncrypt(String content, String publicKey, String charset) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509(SignConstants.SIGN_TYPE_RSA,
                new ByteArrayInputStream(publicKey.getBytes()));
        Cipher cipher = Cipher.getInstance(SignConstants.SIGN_TYPE_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > SignConstants.MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, SignConstants.MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * SignConstants.MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
        out.close();

        return StringUtils.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
    }

    /**
     * 私钥解密<br>
     * 
     * @param content 待解密内容
     * @param privateKey 私钥
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     * @throws Exception
     */
    public static String rsaDecrypt(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SignConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance(SignConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64.decodeBase64(content.getBytes()) : Base64
                    .decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密  
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > SignConstants.MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, SignConstants.MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * SignConstants.MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new Exception("EncodeContent = " + content + ",charset = " + charset, e);
        }
    }

    /**
     * RSA签名
     * 
     * @param params
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static String rsaSign(Map<String, Object> params, String privateKey, String charset) throws Exception {
        String content = getSignContent(params);
        System.out.println("RSA待签名串：" + content);
        return rsaSign(content, privateKey, charset);
    }

    /**
     * RSA签名
     * 
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static String rsaSign(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SignConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes("utf-8")));

            java.security.Signature signature = java.security.Signature.getInstance(SignConstants.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    /**
     * RSA签名校验<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥<br>
     * 
     * @param paramMap
     * @param publicKey
     * @throws Exception
     */
    public static void checkRSASign(Map<String, Object> paramMap, String publicKey) throws Exception {
        if (null == paramMap || !paramMap.keySet().contains(ProtocolParameterEnum.SIGN.getCode())) {
            throw new Exception("请求的参数无效！");
        }
        String sign = (String) paramMap.get(ProtocolParameterEnum.SIGN.getCode());
        paramMap.remove(ProtocolParameterEnum.SIGN.getCode());
        String content = getSignContent(paramMap);
        checkRSASign(sign, content, publicKey, SignConstants.CHARSET_UTF_8);
    }

    /**
     * RSA签名校验<br>
     * 对于<b>开发者</b>，publicKey是指众安的公钥<br>
     * 对于<b>众安开放平台</b>，publicKey是指开发者的公钥<br>
     * 
     * @param sign
     * @param content
     * @param publicKey
     * @param charset
     * @throws Exception
     */
    public static void checkRSASign(String sign, String content, String publicKey, String charset) throws Exception {
        if (!rsaCheckContent(content, sign, publicKey, charset)) {
            throw new Exception("rsaCheck failure: sign=" + sign + ", content=" + content + ", charset=" + charset);
        }
    }

    /**
     * 对map里的每一个key值从a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推。排序完成之后，再把所有数组值以json字符连接起来，如：
     * {"channelId":"1","channelUserNo":"1","identityNo":"1"} 这串字符串便是待签名字符串。
     * 
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, Object> sortedParams) {
        JSONObject js = new JSONObject(sortedParams.size(), true);
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            //            String value = (String) sortedParams.get(key);
            //            if (StringUtils.areNotEmpty(key, value)) {
            js.put(key, sortedParams.get(key));
            //            }
        }
        return js.toJSONString();
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = StreamUtils.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);
        PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(encodedKey);
        return keyFactory.generatePrivate(ps);
    }

    public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset)
            throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature.getInstance(SignConstants.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent=" + content + ",sign=" + sign + ",charset=" + charset, e);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtils.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

}
