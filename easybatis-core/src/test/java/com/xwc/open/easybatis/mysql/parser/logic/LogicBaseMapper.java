package com.xwc.open.easybatis.mysql.parser.logic;

import com.xwc.open.easybatis.core.support.BaseMapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：
 */
public interface LogicBaseMapper extends BaseMapper<LogicEntity, String> {

    List<LogicEntity> list(LogicFilter filter);

}
