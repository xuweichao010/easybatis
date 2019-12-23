package com.xwc.open.example.dao;

import com.xwc.open.easybatis.interfaces.BaseMapper;
import com.xwc.open.example.entity.UserEnum;
import org.apache.ibatis.annotations.Mapper;

/**
 * 创建人：徐卫超
 * 时间：2019/12/20 17:36
 * 功能：枚举测试Mapper
 * 备注：
 */
@Mapper
public interface UserEnumMapper extends BaseMapper<UserEnum,String> {
}
