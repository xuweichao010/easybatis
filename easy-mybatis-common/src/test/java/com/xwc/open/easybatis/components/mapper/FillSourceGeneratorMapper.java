package com.xwc.open.easybatis.components.mapper;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.entity.FillUser;
import com.xwc.open.easybatis.entity.NormalUser;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
public interface FillSourceGeneratorMapper extends EasyMapper<FillUser, String> {

    int simpleInsert(NormalUser normalUser);
}
