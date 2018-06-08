package com.nuist.faxe.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouXiang
 **/
@Data
public class SegmentationInfo {

    private List<Integer> locationList;

    private List<SimpleFileInfo> fileInfoList;
}
