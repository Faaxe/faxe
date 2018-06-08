package com.nuist.faxe.web.vo;

import lombok.Data;

/**
 * @author ZhouXiang
 **/
@Data
public class SimpleFileInfo implements Comparable {

    private String name;

    private String path;

    public SimpleFileInfo(String name, String path){
        this.name = name;
        this.path = path;
    }

    @Override
    public int compareTo(Object o) {
        SimpleFileInfo tmp = (SimpleFileInfo)o;
        //tmp.getName() like  段0.mp3
        String name = tmp.getName();
        int len = name.length();
        Integer targetIndex = Integer.parseInt(name.substring(1, len - 4));
        String thisName = this.getName();
        len = thisName.length();
        Integer index = Integer.parseInt(thisName.substring(1, len - 4));
        return index.compareTo(targetIndex);
    }
}
