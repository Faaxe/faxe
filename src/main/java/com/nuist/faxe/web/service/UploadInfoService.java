package com.nuist.faxe.web.service;

import com.nuist.faxe.web.domain.UploadInfo;

import java.util.List;

/**
 * @author ZhouXiang
 * @create 2018-04-23 18:51
 **/
public interface UploadInfoService {

    UploadInfo getSingle(String id);

    List<UploadInfo> list();

    int insert(UploadInfo entity);

    boolean deleteSingle(String id);
}
