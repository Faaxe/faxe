package com.nuist.faxe.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件工具类
 *
 * @author ZhouXiang
 **/
public class FileUtil {

    /**
     * 是否存在path文件
     * @param path
     * @return
     */
    public static boolean exists(String path){
        File pathResult = new File(path);
        return pathResult.exists();
    }

    /**
     * 没有path文件就创建一个path文件
     * 返回path文件
     */
    public static File createFile(String path)throws IOException {
        File pathResult = new File(path);
        if (!pathResult.exists()) {
            pathResult.createNewFile();
        }
        return pathResult;
    }
    /**
     * 没有pathName目录就创建一个pathName目录
     */
    public static void createDirectory(String pathName){
        File path = new File(pathName);
        if (!path.isDirectory()) {
            path.mkdirs();
        }else{
            //delete 原有所有文件
            File[] files = path.listFiles();
            //遍历子文件
            for(File file : files)
                file.delete();
        }
    }
    /**
     * 覆盖写入文件
     * @param file
     * @param data
     */
    public static void writeFile(File file, byte[] data) {
        writeFile(file, data, false);
    }
    /**
     * 写入文件
     * append 的 true 或 false 决定是否覆盖写入
     * @param file
     * @param data
     */
    public static void writeFile(File file, byte[] data, boolean append) {
        try {
            FileUtils.writeByteArrayToFile(file, data, append);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得outDir目录下的所有文件（仅一层）
     * @param outDir
     * @return
     */
   public static List<File> getFileList(String outDir){
        File dir = new File(outDir);
        return Stream.of(dir.listFiles()).collect(Collectors.toList());
    }

    /**
     * 删除文件
     *
     * @param newName
     * @throws Exception
     */
    public static void deleteFile(String newName) throws Exception {
        File file = new File(newName);
        file.delete();
    }

    /**
     *保存对象到文件中
     * @param obj
     */
    public static void writeObjectToFile(Object obj, File file){
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            //System.out.println("write object success!");
        } catch (IOException e) {
            //System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取对象
     * @param file
     * @return
     */
    public static Object readObjectFromFile(File file) {
        Object temp=null;
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            //System.out.println("read object success!");
        } catch (IOException e) {
            //System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
