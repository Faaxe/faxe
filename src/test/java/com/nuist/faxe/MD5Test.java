package com.nuist.faxe;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nuist.faxe.common.utils.FileUtil;
import com.nuist.faxe.web.vo.SimpleFileInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author ZhouXiang
 * @create 2018-04-23 20:07
 **/
public class MD5Test {
    public static void main(String[] args) throws Exception {
        String outDir = "E:\\毕设\\faxe-ui\\out\\000001\\67e45a.mp3";
        List<SimpleFileInfo> fileInfoList = Lists.newArrayList();
        if (FileUtil.exists(outDir)) {
            FileUtil.getFileList(outDir).forEach(file -> {
                if (file.getName().endsWith(".mp3")) {
                    fileInfoList.add(
                            new SimpleFileInfo(file.getName(), file.getPath().substring(14))
                    );
                }
            });

            //
            Collections.sort(fileInfoList);
            List<SimpleFileInfo> t = fileInfoList;
        }
    }
}
