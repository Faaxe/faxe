package com.nuist.faxe.algorithm.jave;

import com.google.common.collect.Lists;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 编码
 *
 * @author ZhouXiang
 **/
public class AudioImpress {

    /**
     * 转化单个文件
     * @param path
     * @return
     */
    public static String singleWavToMp3(String path) throws Exception {
        //文件后缀替换 .wav => .mp3
        String newName = path.replace(".wav", ".mp3");

        File source = new File(path);
        File target = new File(newName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);

        return newName;
    }
    /**
     * 转化一个dir目录下所有 .wav文件（仅有一层）
     * @param dir
     */
    public static List<String> multipleWavToMp3(File dir) {
        //确保dir为目录
        if(!dir.isDirectory()) return null;

        List<String> newFileNames = Lists.newArrayList();
        File[] files = dir.listFiles();
        Arrays.stream(files).forEach(file -> {
            String path = file.getAbsolutePath();
            if(path.endsWith(".wav")){
                try{
                    //单个转换，将新文件路径添加到newFileNames中
                    String newFileName = singleWavToMp3(path);
                    newFileNames.add(newFileName);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        //返回包含转换好的路径list
        return newFileNames;
    }
}
