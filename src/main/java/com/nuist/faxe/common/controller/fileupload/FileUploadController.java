package com.nuist.faxe.common.controller.fileupload;


import com.nuist.faxe.common.controller.message.ResponseMessage;
import com.nuist.faxe.common.entity.ProgressEntity;
import com.nuist.faxe.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * 文件上传Controller
 *
 * @author zhouxiang
 */
public abstract class FileUploadController {
    public static final String SESSION_UPLOAD_PROGRESS = "uploadProgress";

    @Autowired
    protected MultipartProperties multipartProperties;

    @CrossOrigin
    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    /**
     * 获取文件上传进度
     */
    public ResponseMessage<ProgressEntity> progress(HttpSession session) {
        ProgressEntity uploadProgress = (ProgressEntity) session.getAttribute(SESSION_UPLOAD_PROGRESS);
        return ResponseMessage.ok(uploadProgress);
    }
    /**
     * 上传文件
     */
    public ResponseMessage<String> upload(MultipartFile file) {
        String fileNameExtension = getFileExtension(file);
        return ResponseMessage.ok(StringUtil.randomUUID() + fileNameExtension);
    }


    /**
     * 批量上传文件
     */
    public ResponseMessage<List<String>> multiUpload(List<MultipartFile> files) {
        return ResponseMessage.ok(files.stream().map(file -> StringUtil.randomUUID() + getFileExtension(file)).collect(toList()));
    }

    protected String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
