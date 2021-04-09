package com.milelu.common.utils;

import cn.hutool.crypto.SecureUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Md5 {

    public static String md5(String data) {
        String md5Str = SecureUtil.md5(data);
        return md5Str;
    }

    public static String md5(File file) {
        String md5Str = SecureUtil.md5(file);
        return md5Str;
    }

    public static String md5(InputStream inputStream) {
        String md5Str = SecureUtil.md5(inputStream);
        return md5Str;
    }

    public static void main(String[] args) throws FileNotFoundException {
      String a=  SecureUtil.md5(new FileInputStream(new File("E:\\tk\\source\\templates\\hope\\新建文本文档.txt")));
      System.out.println(a);
    }

}
