package com.nuist.faxe.web.service.impl;

import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.AudioFileExample;
import com.nuist.faxe.web.mapper.AudioFileMapper;
import com.nuist.faxe.web.service.AudioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现类
 *
 * @author ZhouXiang
 **/
@Service
public class AudioFileServiceImpl implements AudioFileService {

    @Autowired
    private AudioFileMapper mapper;

    /**
     * 检测md5是否存在
     * 若存在则返回AudioFileId
     * 若不存在返回null
     *
     * @param md5
     * @return
     */
    @Override
    public String checkMD5(String md5) {
        AudioFileExample example = new AudioFileExample();
        AudioFileExample.Criteria criteria = example.createCriteria();
        criteria.andMd5EqualTo(md5);
        //查找 (理论上只有0/1个)
        List<AudioFile> result = mapper.selectByExample(example);
        if(null != result && result.size() == 1){
            return result.get(0).getId();
        }
        return null;
    }

    @Override
    public int insert(AudioFile entity) {
        return mapper.insert(entity);
    }
}
