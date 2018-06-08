package com.nuist.faxe.web.service;

import com.nuist.faxe.web.domain.AudioFile;

/**
 * @author ZhouXiang
 **/
public interface AudioFileService {

    String checkMD5(String md5);

    int insert(AudioFile entity);
}
