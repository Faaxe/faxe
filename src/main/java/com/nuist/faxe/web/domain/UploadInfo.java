package com.nuist.faxe.web.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UploadInfo {
    private String id;

    private String userId;

    private User user;

    private String audioId;

    private AudioFile audioFile;

    private Date uploadTime;
}