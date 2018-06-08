package com.nuist.faxe.web.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nuist.faxe.common.controller.fileupload.FileUploadController;
import com.nuist.faxe.common.controller.message.ResponseMessage;
import com.nuist.faxe.common.utils.FileUtil;
import com.nuist.faxe.common.utils.MD5;
import com.nuist.faxe.common.utils.StringUtil;
import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.UploadInfo;
import com.nuist.faxe.web.service.AudioFileService;
import com.nuist.faxe.web.service.UploadInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 音频上传Controller
 *
 * @author ZhouXiang
 **/
@RestController
@RequestMapping("/audio/up")
public class AudioFileUploadController extends FileUploadController {

    @Autowired
    private AudioFileService audioFileService;

    @Autowired
    private UploadInfoService uploadInfoService;

    @Value("${faxe.file.storage.path}")
    private String fileStoragePath;

    /**
     * 初始化上传路径
     */
    @PostConstruct
    public void init() {
        FileUtil.createDirectory (fileStoragePath);
    }

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseMessage upload(@RequestParam("file") MultipartFile file,
                                                @RequestParam("userId") String userId) {
        //md5计算
        String md5 = MD5.make(file);
        String md5Result = audioFileService.checkMD5(md5);
        if(null == md5Result){
            String fileName = super.upload(file).getResult();
            String currentFilePath = Joiner.on("/").join(fileStoragePath, fileName);
            try {
                File newFile = new File(currentFilePath);
                FileUtil.writeFile(newFile, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            insertEntity(StringUtil.randomUUID(),currentFilePath, file.getOriginalFilename(), md5, userId);
        }else{
            insertEntity(md5Result,null, null, null, userId );
        }
        return ResponseMessage.ok();
    }

    @CrossOrigin
    @PostMapping("/multi_upload")
    public ResponseMessage multiUpload(@RequestParam("file") List<MultipartFile> files,
                                                     @RequestParam("userId") String userId) {
        if (CollectionUtils.isNotEmpty(files)) {
            files.forEach(item -> {
                upload(item, userId);
            });
        }
        return ResponseMessage.ok();
    }

    /**
     * 根据值构建 entity 并且保存
     * 返回保存的实体
     * @param currentFilePath
     * @param fileName
     * @param userId
     */
    private void insertEntity(String audioId, String currentFilePath, String fileName, String md5, String userId){
       if(null != md5) {
           //保存audioFile
           AudioFile file = new AudioFile();
           file.setId(audioId);
           file.setMd5(md5);
           file.setOldName(fileName);
           file.setPath(currentFilePath);
           audioFileService.insert(file);
       }
        //保存uploadinfo
        String infoId = StringUtil.randomUUID();
        UploadInfo entity = new UploadInfo();
        entity.setId(infoId);
        entity.setAudioId(audioId);
        //时间设置为现在。
        entity.setUploadTime(new Date());
        entity.setUserId(userId);
        uploadInfoService.insert(entity);
    }
}
