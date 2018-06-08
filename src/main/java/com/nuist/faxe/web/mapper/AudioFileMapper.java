package com.nuist.faxe.web.mapper;

import com.nuist.faxe.web.domain.AudioFile;
import com.nuist.faxe.web.domain.AudioFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AudioFileMapper {
    long countByExample(AudioFileExample example);

    int deleteByExample(AudioFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(AudioFile record);

    int insertSelective(AudioFile record);

    List<AudioFile> selectByExample(AudioFileExample example);

    AudioFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AudioFile record, @Param("example") AudioFileExample example);

    int updateByExample(@Param("record") AudioFile record, @Param("example") AudioFileExample example);

    int updateByPrimaryKeySelective(AudioFile record);

    int updateByPrimaryKey(AudioFile record);
}