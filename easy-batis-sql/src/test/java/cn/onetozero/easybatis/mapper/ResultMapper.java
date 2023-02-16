package cn.onetozero.easybatis.mapper;

import cn.onetozero.easybatis.entity.ResultUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/13 10:18
 */
@Mapper
public interface ResultMapper extends BaseMapper<ResultUser, String> {
}
