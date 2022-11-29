package com.xwc.easy.core.method;

import com.xwc.easy.core.table.method.MethodEntity;
import com.xwc.easy.core.table.method.MethodEntityQuery;
import com.xwc.open.easy.parse.supports.EasyMapper;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/27 20:57
 */
public interface TestBaseMapper extends BaseMapper<MethodEntity, Long> {

    int customEntityParameterAttribute(MethodEntity entity);

    int customCollectionEntityParameterAttribute(List<MethodEntity> entity);

    int baseParameterAttribute(Long id);

    List<MethodEntity> collectionParameterAttribute(List<Long> ids);

    int objectParameterAttribute(MethodEntityQuery query);

    int mapParameterAttribute(Map<Object, Object> map);
}
