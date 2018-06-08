package com.nuist.faxe.web.domain;

import lombok.Data;

@Data
public class AudioFile {
    private String id;

    private String path;

    private String oldName;

    private String md5;
}