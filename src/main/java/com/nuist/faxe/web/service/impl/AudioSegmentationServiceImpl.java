package com.nuist.faxe.web.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nuist.faxe.algorithm.jave.AudioDepress;
import com.nuist.faxe.algorithm.jave.AudioImpress;
import com.nuist.faxe.algorithm.segmentation.AudioSegmentation;
import com.nuist.faxe.common.utils.FileUtil;
import com.nuist.faxe.web.service.AudioSegmentationService;
import com.nuist.faxe.web.vo.SegmentationInfo;
import com.nuist.faxe.web.vo.SimpleFileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 实现类
 *
 * @author ZhouXiang
 * @create 2018-04-19 10:41
 **/
@Service
public class AudioSegmentationServiceImpl implements AudioSegmentationService {

    @Value("${faxe.file.out.path}")
    private String fileOutPath;

    @Value("${faxe.file.storage.path}")
    private String fileStoragePath;

    @Override
    public Map<Integer, Integer> audioSegmentation(String userId, String name, boolean cut, Integer selectValue) throws Exception{
        String file = Joiner.on("/").join(fileStoragePath, name);
        //mp3 => wav
        String pathEndWithWav = AudioDepress.singleMp3ToWav(file);
        //输出路径
        String outDir = Joiner.on("/").join(fileOutPath, userId, name);
        FileUtil.createDirectory(outDir);
        //segmentation
        AudioSegmentation audioSegmentation = getAudioSegmentationByValue(selectValue);
        if(0 != selectValue && 1!= selectValue){
            //词默认不切割
            cut = false;
        }
        Map result = audioSegmentation.audioSegmentation(pathEndWithWav, outDir, cut);
        //保存result
        File mapFile = FileUtil.createFile(Joiner.on("/").join(outDir, "map.txt"));
        FileUtil.writeObjectToFile(result, mapFile);
        if(cut) {
            //wav => mp3
           AudioImpress.multipleWavToMp3(new File(outDir));
        }
        return result;
    }

    /**
     * 选择颗粒进行分段
     *
     * @param selectValue
     * @return
     */
    private AudioSegmentation getAudioSegmentationByValue(Integer selectValue){
        if(0 == selectValue){
            //段
            return new AudioSegmentation(5, 1,2000, "段");
        }else if(1 == selectValue){
            //句
            return new AudioSegmentation(8, 0.8,1000, "句");
        }else {
            //词
            return new AudioSegmentation(10, 0.7,100, "词");
        }
    }

    @Override
    public SegmentationInfo getSegmentationInfo(String userId, String name) {
        SegmentationInfo info = new SegmentationInfo();
        //路径
        String outDir = Joiner.on("/").join(fileOutPath, userId, name);
        String mapPath = Joiner.on("/").join(outDir, "map.txt");
        if(FileUtil.exists(mapPath)){
            Map map = (Map<Integer, Integer>)(FileUtil.readObjectFromFile(new File(mapPath)));
            List<Integer> list = Lists.newArrayList();
            Set<Integer> keys = map.keySet();
            Iterator<Integer> iterator = keys.iterator();
            while (iterator.hasNext()) {
                Integer start = iterator.next();
                if(start > 0){
                    list.add(start);
                }
            }
            info.setLocationList(list);
        }
        List<SimpleFileInfo> fileInfoList = Lists.newArrayList();
        if(FileUtil.exists(outDir)){
            FileUtil.getFileList(outDir).forEach(file -> {
                if(file.getName().endsWith(".mp3")){
                    fileInfoList.add(
                            new SimpleFileInfo(file.getName(), file.getPath().substring(14))
                    );
                }
            });
            //先排序
            Collections.sort(fileInfoList);
            info.setFileInfoList(fileInfoList);
        }
        return info;
    }
}
