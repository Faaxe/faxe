package com.nuist.faxe.web.service;

import com.nuist.faxe.web.vo.SegmentationInfo;

import java.util.List;
import java.util.Map;

/**
 * 分段Service
 *
 * @author ZhouXiang
 **/
public interface AudioSegmentationService {

    Map<Integer, Integer> audioSegmentation(String userId, String name, boolean cut, Integer selectValue) throws Exception;

    SegmentationInfo getSegmentationInfo(String userId, String name);
}
