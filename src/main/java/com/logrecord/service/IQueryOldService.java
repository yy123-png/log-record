package com.logrecord.service;

/**
 * @author yeyu
 * @since 2022-05-11 11:00
 * 查询旧数据接口
 * 需要用Key来标识一个唯一的查询旧值的服务
 */
public interface IQueryOldService {

    String key();

    Object apply(Object param);
}
