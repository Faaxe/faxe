package com.nuist.faxe.web.service.impl;

import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.UploadInfo;
import com.nuist.faxe.web.mapper.AudioFileMapper;
import com.nuist.faxe.web.mapper.UploadInfoMapper;
import com.nuist.faxe.web.service.UploadInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现类
 *
 * @author ZhouXiang
 **/
@Service
public class UploadInfoServiceImpl implements UploadInfoService {

    @Autowired
    private UploadInfoMapper uploadInfoMapper;

    @Autowired
    private AudioFileMapper audioFileMapper;

    /**
     * 返回单个UploadInfo实体
     *
     * @param id
     * @return
     */
    public UploadInfo getSingle(String id) {
        return uploadInfoMapper.selectByPrimaryKeyWithUserAndAudio(id);
    }

    /**
     * 查询全部
     *
     * @return list
     */
    public List<UploadInfo> list(){
        return uploadInfoMapper.listWithUserAndAudio();
    }

    /**
     * 插入一条上传记录
     */
    public int insert(UploadInfo entity){
        return uploadInfoMapper.insert(entity);
    }
    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    public boolean deleteSingle(String id) {
        long count = uploadInfoMapper.deleteByPrimaryKey(id);
        return 1 == count;
    }
}
