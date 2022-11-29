package com.xwc.easy.core.method;

import com.xwc.easy.core.table.method.MethodEntity;
import com.xwc.open.easy.parse.supports.EasyMapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/27 20:57
 */
public interface TestBaseMapper extends BaseMapper<MethodEntity, Long> {

    int insert(MethodEntity entity);

    int update(MethodEntity entity);

    int delete(Long id);

    int query(Long id);
}
