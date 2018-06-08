package com.nuist.faxe.common.entity;

import com.nuist.faxe.common.controller.message.ResponseMessage;
import lombok.Data;

/**
 * 专门用于封装分页数据的实体
 *
 * @author ZhouXiang
 * @create 2018-04-26 21:33
 **/
@Data
public class Msg<T> {
    private int code;

    private String msg;

    private int count;

    private T data;

    public static <T> Msg<T>  ok(T data, int count) {
        return new Msg<T> ()
                .data(data)
                .code(0)
                .count(count);
    }

    public static <T> Msg<T>  error(String msg) {
        return new Msg<T> ()
                .msg(msg)
                .code(400);
    }

    public Msg<T> code(int code) {
        this.code = code;
        return this;
    }

    public Msg<T> data(T data) {
        this.data = data;
        return this;
    }

    public Msg<T> count(int count) {
        this.count = count;
        return this;
    }

    public Msg<T> msg(String msg) {
        this.msg = msg;
        return this;
    }
}
