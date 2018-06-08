package com.nuist.faxe.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * @author ZhouXiang
 * @create 2018-04-23 18:56
 **/
@Data
public class UploadInfoVO {
    private String id;

    private String userId;

    private String userName;

    private String audioId;

    private String audioPath;

    private String audioOldName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date uploadTime;
}
