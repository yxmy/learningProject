package com.yx.springboot.demospring.persexml;

import java.util.*;

public class Table {
    private String name;
    private String className;
/**
 * 取得Table描述的PO的className，如com.seeyon.ctp.organization.po.OrgMember。
 * @return
 */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private final List<Column>        columns;
    private final Map<String, Column> columnMap;

    public Table() {
        this.columns = new ArrayList<Column>();
        this.columnMap = new HashMap<String, Column>();
    }

    public Table(Collection<Column> columns) {
        this();
        for (Column column : columns) {
            this.columns.add(column);
            this.columnMap.put(column.getName(), column);
            column.setTable(this);
        }
    }

    /**
     * 取得Table的名称。
     * @return Table名称，作为程序调用的唯一标识，如orgMember1。
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    /**
     * 取得指定名称的Column。
     * @param name 字段名称
     * @return 存在返回Column对象，不存在返回<code>null</code>。
     */
    public Column getColumn(String name) {
        return this.columnMap.get(name);
    }

    /**
     * 按别名取Column。
     * @param alias Column别名。
     * @return 别名为null或不存在指定别名的Column时返回null。
     */
    public Column getColumnByAlias(String alias) {
        if (alias == null)
            return null;
        for (Map.Entry<String, Column> entry : this.columnMap.entrySet()) {
            Column column = entry.getValue();
            if (alias.equals(column.getAlias()))
                return column;
        }
        return null;
    }
}
