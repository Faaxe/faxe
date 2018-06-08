package com.nuist.faxe.web.mapper;

import com.nuist.faxe.web.domain.UploadInfo;
import com.nuist.faxe.web.domain.UploadInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UploadInfoMapper {
    long countByExample(UploadInfoExample example);

    int deleteByExample(UploadInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(UploadInfo record);

    int insertSelective(UploadInfo record);

    List<UploadInfo> selectByExample(UploadInfoExample example);

    UploadInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UploadInfo record, @Param("example") UploadInfoExample example);

    int updateByExample(@Param("record") UploadInfo record, @Param("example") UploadInfoExample example);

    int updateByPrimaryKeySelective(UploadInfo record);

    int updateByPrimaryKey(UploadInfo record);

    /**
     * 添加方法
     */
    UploadInfo selectByPrimaryKeyWithUserAndAudio(String id);

    List<UploadInfo> listWithUserAndAudio();
}