package com.nuist.faxe;

import javax.sound.sampled.AudioInputStream;
import java.io.File;

/**
 * @author ZhouXiang
 **/
public class EncodeTest {
    public static void main(String[] args) throws Exception{
        AudioInputStream audio_file =
                javax.sound.sampled.AudioSystem.getAudioInputStream(
                        new File("C:\\Users\\Administrator\\Desktop\\前端\\A1b.wav"));
        //获取帧长
        int frameLength = audio_file.getFormat().getSampleSizeInBits();
        //获取帧数
        long frameNumber = audio_file.getFrameLength();
        //大小
        int frameSize = audio_file.getFormat().getFrameSize();
        System.out.println(frameLength +" \n "+ frameNumber+"\n"+frameSize);
    }
}
