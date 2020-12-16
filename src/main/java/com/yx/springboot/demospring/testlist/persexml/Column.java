package com.yx.springboot.demospring.testlist.persexml;

/**
 * 字段，表的基本组成单位，对应于一个数据库的表字段。
 * @author wangwenyou
 *
 */
public class Column {
    private String   	name;
    private String   	label;
    private Integer  	sort;
    private Integer  	type;
    private String   	rule;
    private Integer 	dataType = DataType.String.getKey();
    private String  	alias;
    private String 		component = "";
    private Table 		table;

    public void setTable(Table table){
    	this.table = table;
    }
    
    public Table getTable(){
    	return table;
    }
    
    /**
     * 取得前端使用的录入组件。
     * @return 录入组件名称，与前端组件的comp对应，如calendar、selectPeople。
     */
    public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	/**
     * 取得字段别名。
     * @return 字段别名，在同一Table中必须唯一。也不能与同一Table中的Column的name冲突。
     * 比如可以为name为ext00112的字段定义一个易识别的别名location。
     */
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 字段名称，必须与PO中的名称一致。
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return null;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 排序号，在页面上的显示顺序
     */
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 元数据类型，如枚举、文本。
     * 
     * @return 0为文本，1为引用了枚举。
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 用户自定义规则（校验规则）。
     * @return
     */
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * 字段的数据类型。
     * @return 如果未设置数据类型，缺省返回DataType.String.getKey()
     */
    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
    /**
     * 取得原始的Label值，亦即I18N的key。
     * @return
     */
    public String getLabelKey(){
    	return this.label;
    }
}
