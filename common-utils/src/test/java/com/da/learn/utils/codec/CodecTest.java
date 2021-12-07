package com.da.learn.utils.codec;

import org.junit.Test;

public class CodecTest {

    @Test
    public void DESTest() throws Exception {
        String encode = DESUtil.encode("内容，哈哈,123", "password01!");
        System.out.println(encode);
        String decode = DESUtil.decode(encode, "password01!");
        System.out.println(decode);
    }

    @Test
    public void AESTest() throws Exception {
        String key = "cde";
        String data = "abc";
        String encrypted = AESUtil.encrypt(data, key);
        String decrypted = AESUtil.decrypt(encrypted, key);
        System.out.println("加密后的密文\n" + encrypted);
        System.out.println("解密后的报文:\n" + decrypted);
    }

    @Test
    public void MD5Test() throws Exception {
        String encode = MD5Util.md5Encode("abcHhaha", "utf-8");
        System.out.println(encode);
    }

    @Test
    public void RSATestGetKey() throws Exception {
        //生成公钥和私钥
        RSAUtil.Key key = RSAUtil.genKeyPair();
        System.out.println(key);

    }


    @Test
    public void RSATestPrivate() throws Exception {
        //加密字符串
        String message = "df723820";
        String encrypt = RSAUtil.privateEncrypt(message, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvr2ituKNF/ICU4/0ThJuYh+oWdE14JIH4T/TVWIkTeIwlUqkSd99waVRP7gtTAAtRFM4CPLgqon7vaeUisqTGuXTqC7I0htNmdIO7fWC+/sM2YDYxrJVHlmJAb2lYAfWYU8kWk7mHUT936bsmRqEVSIHwIQXSPoY3Og/+4aXy1AgMBAAECgYAdzWuXyovpK2GmbAB7JFXLuLsKkI1afykrkYhiPulnr8TYrO7jVJ5BhF5QtkaEmC1CF0dD/7s3y/jR41Pn9dbHiSQdOpbRu33Shxf+QkEzVc7B162bvJkuKa8GobSTEILZQ9HPg3ZyZPBPZE5c6wZd7F3ddMFZs0K3cSVDg7PVYQJBAP0luySs/tIB+zP9j6myak391B3kwlJhyp+GISnVHQ9OfEX10d25WK6byaUX7QiLTKI2JMsMSHY1HzANgAgjhP0CQQCNf33HL4DFaMb0w9Xcb6i+J4vavquAtpEbLDHJ0Q0N54WlCuEYLSu1w9hjidv6chE67p37MHgRfkglOfLAL4AZAkEAiwxUlTqo5XXNI6udY9rqZViNULY8vr+UardkM/QpeCTmQg9abW3HdnF/+zz/J/hL6bXP0/A1RSi8rspbjxzjiQJAaRQ9ttcbHP8DiS+JuUsemCBMJuUj/dEWj4RRcRuTQzklMXyTzt10sWZAYyM0vpdJHmen4KuM7wjF0Qdos7pSuQJAAj5o1clOUZk0umWFTayui8C6laMeDzfNd7t/vsjifBQFxHlNDDYLT5WqtdYVkTyKPk7W5MeTtmA4iwjtncHfDw==");
        System.out.println(encrypt);
        String decrypt = RSAUtil.publicDecrypt(encrypt, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL69orbijRfyAlOP9E4SbmIfqFnRNeCSB+E/01ViJE3iMJVKpEnffcGlUT+4LUwALURTOAjy4KqJ+72nlIrKkxrl06guyNIbTZnSDu31gvv7DNmA2MayVR5ZiQG9pWAH1mFPJFpO5h1E/d+m7JkahFUiB8CEF0j6GNzoP/uGl8tQIDAQAB");
        System.out.println(decrypt);
    }


    @Test
    public void RSATestPublic() throws Exception {
        //加密字符串
        String message = "df723820";
        String encrypt = RSAUtil.publicEncrypt(message, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL69orbijRfyAlOP9E4SbmIfqFnRNeCSB+E/01ViJE3iMJVKpEnffcGlUT+4LUwALURTOAjy4KqJ+72nlIrKkxrl06guyNIbTZnSDu31gvv7DNmA2MayVR5ZiQG9pWAH1mFPJFpO5h1E/d+m7JkahFUiB8CEF0j6GNzoP/uGl8tQIDAQAB");
        System.out.println(encrypt);
        String decrypt = RSAUtil.privateDecrypt(encrypt, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvr2ituKNF/ICU4/0ThJuYh+oWdE14JIH4T/TVWIkTeIwlUqkSd99waVRP7gtTAAtRFM4CPLgqon7vaeUisqTGuXTqC7I0htNmdIO7fWC+/sM2YDYxrJVHlmJAb2lYAfWYU8kWk7mHUT936bsmRqEVSIHwIQXSPoY3Og/+4aXy1AgMBAAECgYAdzWuXyovpK2GmbAB7JFXLuLsKkI1afykrkYhiPulnr8TYrO7jVJ5BhF5QtkaEmC1CF0dD/7s3y/jR41Pn9dbHiSQdOpbRu33Shxf+QkEzVc7B162bvJkuKa8GobSTEILZQ9HPg3ZyZPBPZE5c6wZd7F3ddMFZs0K3cSVDg7PVYQJBAP0luySs/tIB+zP9j6myak391B3kwlJhyp+GISnVHQ9OfEX10d25WK6byaUX7QiLTKI2JMsMSHY1HzANgAgjhP0CQQCNf33HL4DFaMb0w9Xcb6i+J4vavquAtpEbLDHJ0Q0N54WlCuEYLSu1w9hjidv6chE67p37MHgRfkglOfLAL4AZAkEAiwxUlTqo5XXNI6udY9rqZViNULY8vr+UardkM/QpeCTmQg9abW3HdnF/+zz/J/hL6bXP0/A1RSi8rspbjxzjiQJAaRQ9ttcbHP8DiS+JuUsemCBMJuUj/dEWj4RRcRuTQzklMXyTzt10sWZAYyM0vpdJHmen4KuM7wjF0Qdos7pSuQJAAj5o1clOUZk0umWFTayui8C6laMeDzfNd7t/vsjifBQFxHlNDDYLT5WqtdYVkTyKPk7W5MeTtmA4iwjtncHfDw==");
        System.out.println(decrypt);
    }

}