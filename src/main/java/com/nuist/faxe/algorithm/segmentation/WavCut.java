package com.nuist.faxe.algorithm.segmentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * wav文件切割
 *
 * @author ZhouXiang
 **/
public class WavCut {
    /**
     * 截取wav音频文件
     *
     * @param sourcefile 源文件地址
     * @param targetfile 源文件地址
     * @param startSecond 截取开始时间（秒）
     * @param secondsToCopy 截取结束时间（秒）
     *
     * @return 截取成功返回true，否则返回false
     */
    public static boolean cut(String sourcefile, String targetfile, int startSecond, int secondsToCopy) {
        AudioInputStream inputStream = null;
        AudioInputStream shortenedStream = null;
        try {
            File file = new File(sourcefile);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat format = fileFormat.getFormat();
            inputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerSecond = format.getFrameSize() * (int)format.getFrameRate();
            inputStream.skip(startSecond * bytesPerSecond);
            long framesOfAudioToCopy = secondsToCopy * (int)format.getFrameRate();
            shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
            File destinationFile = new File(targetfile);
            AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null)
                try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (shortenedStream != null)
                try {
                shortenedStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    /**
     *  获取音频文件总时长
     *
     * @param file  文件路径
     * @return
     */
    public static long getTimeLen(File file){
        long tlen = 0;
        if(file!=null && file.exists()){
            Encoder encoder = new Encoder();
            try {
                MultimediaInfo m = encoder.getInfo(file);
                long ls = m.getDuration();
                tlen = ls/1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tlen;
    }
}
