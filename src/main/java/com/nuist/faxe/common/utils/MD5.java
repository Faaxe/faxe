package com.nuist.faxe.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5生成
 *
 * @author ZhouXiang
 **/
public class MD5 {

    public static String make(MultipartFile file){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(file.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
