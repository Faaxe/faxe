package com.nuist.faxe.web.controller;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * 音频下载Controller
 *
 * @author ZhouXiang
 **/
@RestController
@RequestMapping("/audio/down")
public class AudioFileDownloadController {

    public static final String USER_AGENT = "User-Agent";

    public static final String FIRE_FOX = "firefox";

    @Value("${faxe.file.storage.path}")
    private String fileStoragePath;

    @Value("${faxe.file.out.path}")
    private String fileOutPath;

    @CrossOrigin
    @GetMapping("/download/upload/{fileName:.+}")
    public void download(@PathVariable(value = "fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        downloadFileFromPath(fileStoragePath, fileName, request, response);
    }

    /**
     * eg. /download/out/1/drwr4t.mp3/片段1
     */
    @CrossOrigin
    @GetMapping("/download/out/{userId}/{name}/{fileName:.+}")
    public void download(@PathVariable(value = "userId") String userId, @PathVariable(value = "name") String name, @PathVariable(value = "fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        downloadFileFromPath(Joiner.on("/").join(fileOutPath, userId, name), fileName, request, response);
    }

    /**
     *  下载文件
     */
    private void downloadFileFromPath(String path, String fileName, HttpServletRequest request, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String downLoadPath = null;

        try {
            // firefox浏览器
            if (request.getHeader(USER_AGENT).toLowerCase().indexOf(FIRE_FOX) > 0) {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                // 其他浏览器包括IE浏览器和google浏览器
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }

            response.setHeader("conent-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            downLoadPath = Joiner.on("/").join(path, fileName);

            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
