package com.nuist.faxe.algorithm.jave;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

/**
 * 解码
 *
 * @author ZhouXiang
 **/
public class AudioDepress {

    /**
     * 转化单个文件
     * @param path
     * @return
     */
    public static String singleMp3ToWav(String path) throws Exception {
        //文件后缀替换 .mp3 => .wav
        String newName = path.replace(".mp3", ".wav");

        File source = new File(path);
        File target = new File(newName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_alaw");
        audio.setBitRate(new Integer(64000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(8000));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);

        return newName;
    }
}
