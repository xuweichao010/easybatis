package com.xwc.open.easybatis.mysql.update;

import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;
import com.xwc.open.easybatis.model.BaseEntity;
import com.xwc.open.easybatis.mysql.insert.BaseInsertEntity;
import com.xwc.open.easybatis.mysql.insert.MixtureBaseMapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：插入测试mapper
 */
public interface BaseUpdateMapper extends BaseMapper<BaseUpdateEntity, String> {
    @UpdateSql
    Object updateEntity(BaseUpdateEntity entity);

}
