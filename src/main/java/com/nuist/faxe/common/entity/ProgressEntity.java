package com.nuist.faxe.common.entity;


import lombok.Data;

@Data
public class ProgressEntity {

    /**
     * 到目前为止读取文件的比特数
     */
    private long alreadReadBytes = 0L;
    /**
     * 文件总大小
     */
    private long totalContentBytes = 0L;
    /**
     * 目前正在读取第几个文件
     */
    private int items;

}