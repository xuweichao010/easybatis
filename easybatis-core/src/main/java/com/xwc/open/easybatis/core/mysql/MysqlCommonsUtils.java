package com.xwc.open.easybatis.core.mysql;

import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.model.table.Mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/8/2
 * 描述：
 */
public class MysqlCommonsUtils {

    public static List<Mapping> insertColumn(TableMeta tableMeta) {
        ArrayList<Mapping> list = new ArrayList<>();
        if (tableMeta.getId().getIdType() != IdType.AUTO) {
            list.add(tableMeta.getId());
        }
        list.addAll(tableMeta.getColumnMetaList());
        list.addAll(tableMeta.getAuditorList().stream()
                .sorted(Comparator.comparingInt(s -> s.getType().ordinal())).collect(Collectors.toList()));
        if (tableMeta.getLogic() != null) {
            list.add(tableMeta.getLogic());
        }
        return list;
    }

    public static List<Mapping> selectColumn(TableMeta tableMeta) {
        ArrayList<Mapping> list = new ArrayList<>();
        list.add(tableMeta.getId());
        list.addAll(tableMeta.getColumnMetaList());
        list.addAll(tableMeta.getAuditorList().stream()
                .sorted(Comparator.comparingInt(s -> s.getType().ordinal()))
                .filter(auditorMapping -> !auditorMapping.isSelectIgnore()).collect(Collectors.toList()));
        return list;
    }

    public static List<Mapping> updateColumn(TableMeta tableMeta) {
        ArrayList<Mapping> list = new ArrayList<>();
        list.addAll(tableMeta.getColumnMetaList());
        list.addAll(tableMeta.getAuditorList().stream().filter(item -> !item.isUpdateIgnore())
                .sorted(Comparator.comparingInt(s -> s.getType().ordinal())).collect(Collectors.toList()));
        return list;
    }

}