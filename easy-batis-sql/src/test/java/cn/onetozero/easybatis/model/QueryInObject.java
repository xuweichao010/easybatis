package cn.onetozero.easybatis.model;

import cn.onetozero.easy.annotations.conditions.In;
import lombok.Data;

import java.util.List;

/**
 * 类描述：对象IN查询
 * @author  徐卫超 (cc)
 * @since 2023/2/4 9:53
 */
@Data
public class QueryInObject {

    @In("age")
    private List<Integer> ages;

    private String id;
}
