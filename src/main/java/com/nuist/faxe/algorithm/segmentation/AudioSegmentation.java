package com.nuist.faxe.algorithm.segmentation;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.util.*;

/**
 * 分段
 *
 * @author ZhouXiang
 **/
public class AudioSegmentation {

    //低于E_th的连续帧的最低个数
    private  int T;//
    //静音因子
    private  double B;//
    //设置帧长
    private  int frameLength;//

    private String describtion;

    public AudioSegmentation(int T, double B, int frameLength, String describtion){
        this.T = T;
        this.B = B;
        this.frameLength = frameLength;
        this.describtion = describtion;
    }
    /**
     * cut 是否剪切
     *
     * inPath 为 .wav文件的存放位置
     * 将wav文件切割成小片段（.wav格式）放到outDir目录下
     * @param inPath
     * @param outDir
     */
    public  Map<Integer, Integer> audioSegmentation(String inPath, String outDir, boolean cut)throws Exception {
        AudioInputStream audio_file =
                javax.sound.sampled.AudioSystem.getAudioInputStream(new File(inPath));
        //获取帧数
        long frameNumber = audio_file.getFrameLength();
        //每帧字节数
        int frameSize = audio_file.getFormat().getFrameSize();
        //E(i)为第i个连续帧（长度为frameLength）的短时能量
        int E_Size = (int)frameNumber/frameLength;
        long[] E = new long[E_Size];
        long E_max = 0L, E_min = Long.MAX_VALUE;
        long E_sum = 0L;
        for (int i = 0; i < E_Size; i++) {
            E[i] = 0L;
            for (int j = 0; j < frameLength; j++) {
                //字节缓冲区
                byte []buff = new byte[frameSize];
                //读入对应字节放置缓冲区buff
                audio_file.read(buff);
                //frameSignal 存放int数据
                int frameSignal = getInt(buff);
                //E[i] += frameSignal*frameSignal;
                //简化短时能量公式
                E[i] += Math.abs(frameSignal);
            }
            if (E_max < E[i]) E_max = E[i];
            if (E_min > E[i]) E_min = E[i];
            E_sum += E[i];
        }
        double E_mean = 1.0 * E_sum / E_Size;

        // 设E_max、E_min、E_mean分别表示音频流短时帧能量的最大值、最小值和均值
        // 用Eoff表示能量的浮动范围
        // 能量门限 E_th = B*E_off + E_min
        //               = B*Min{ 0.5*(E_max - E_min), E_mean - E_min} + E_min
        double E_th = B * Double.min(0.5 * (E_max - E_min), E_mean - E_min) + E_min;

        // 选择合适的静音因子 B（0<=B<=1）
        // B的取值由实验确定
        // 当超过连续T帧的音频短时帧能量小于能量门限则认为该音频片段为静音段，否则为有声段。

        //获得wav时长代码
        long total_second = WavCut.getTimeLen(new File(inPath));
        // uint 值为连续帧的秒数
        double uint = 1.0 * total_second / E_Size;

        Map map = screen(E, E_Size, E_th, uint);
        if(cut) {
            int partIndex = 0;
            Set<Integer> keys = map.keySet();
            Iterator<Integer> iterator = keys.iterator();
            while (iterator.hasNext()) {
                Integer start = iterator.next();
                Integer seconds = (Integer) map.get(start) - start;
                //wav切割 out to => outDir/片段0.wav
                WavCut.cut(inPath, Joiner.on("/").join(outDir, describtion + partIndex + ".wav"),
                        start, seconds );
                partIndex++;
            }
        }
        return map;
    }

    /**
     * 分段计算
     *
     * @param E
     * @param E_Size
     * @param E_th
     * @return 有声片段
     */
    private  Map<Integer,Integer> screen(long []E, int E_Size, double E_th, double uint){
        int count = 0;//计数器
        int start = 0;//start 记录 E[index] < E_th 的下标 index
        List<Integer> result = Lists.newArrayList();
        List<Integer> SecResult = Lists.newArrayList();
        for(int i = 0; i < E_Size; i++){
            if(E[i] < E_th){
                if(count ==0 ){
                    start = i;
                }
                count++;
            }else{
                //超过连续T
                if(count > T ){
                    //记录起始位置[start ,start + count]
                    result.add(start);
                    result.add(start + count-1);
                    //计算
                    //int sec = (int)(( start*uint + (start + count-1)*uint )/2);
                    //int sec = (int)(( start*uint + 3*(start + count-1)*uint )/4);
                    int sec = (int)((start + count-1)*uint );
                    SecResult.add(sec);
                }
                //清零
                count = 0;
            }
            //避免最后一个连续帧也小于E_th的情况没有统计
            if(i == E_Size-1 && E[i] < E_th){
                //超过连续10
                if(count > T ){
                    //记录起始位置[start ,start + count]
                    result.add(start);
                    result.add(E_Size - 1);
                    //计算
                    //int sec = (int)(( start*uint + (E_Size - 1)*uint )/2);
                    //int sec = (int)(( start*uint + 3*(E_Size - 1)*uint )/4);
                    //SecResult.add(sec);
                }
            }
        }
        //存储在map中
        Map startAndEnd = new LinkedHashMap<Integer, Integer>();
        int listSize = SecResult.size();
        if(result.size() > 0 && 0 < result.get(0)){
            startAndEnd.put(0, SecResult.get(0));
        }
        for(int index = 0; listSize > 0 && index < listSize-1; index++){
            startAndEnd.put(SecResult.get(index), SecResult.get(index+1));
        }
        if(result.size() > 0 && (E_Size - 1) >= result.get(result.size() - 1)){
            startAndEnd.put(SecResult.get(SecResult.size() - 1), (int)(uint*E_Size));
        }
        return startAndEnd;
    }

    private  int getInt(byte[] buff){
        return buff[0];
    }

}
