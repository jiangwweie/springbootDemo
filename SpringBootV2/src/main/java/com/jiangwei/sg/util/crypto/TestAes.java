package com.jiangwei.sg.util.crypto;

import java.io.UnsupportedEncodingException;

public class TestAes {
    //加密。
    public static void main(String[] args) {
        String context = "thisisaes";
        AES aes = new AES("2930a074d6c34888a1bd173d0fd2a62d".toCharArray());
        byte[] plainData = new byte[0];
        try {
            plainData = context.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(aes.encrypt(plainData));
    }
//    //解密
//    @Test
//    public void test(){
//        String s = "z+wi/Q+nCshdHc2CxF/GVw==";
//        AES aes = new AES("2930a074d6c34888a1bd173d0fd2a62d".toCharArray());
//        byte[] plainData=aes.decrypt(s);
//    }
}
