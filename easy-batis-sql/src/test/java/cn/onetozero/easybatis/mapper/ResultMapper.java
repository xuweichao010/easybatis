package cn.onetozero.easybatis.mapper;

import cn.onetozero.easybatis.entity.ResultUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/13 10:18
 */
@Mapper
public interface ResultMapper extends BaseMapper<ResultUser, String> {
}
