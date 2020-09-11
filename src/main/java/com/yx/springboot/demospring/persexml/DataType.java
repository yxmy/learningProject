package com.yx.springboot.demospring.persexml;

/**
 * 字段数据类型枚举。
 * @author wangwenyou
 *
 */
public enum DataType implements EnumsCode{
	
	String(0, "文本"),
	Integer(1, "整数"),
	Double(2, "小数"),
	Enums(3, "枚举"),
	Date(4, "日期"),
	DateTime(5, "日期时间"),
	Person(6, "common.select.person.label"),
	Department(7, "metadata.column.select.deparment"),
	Account(8, "metadata.column.select.unit");

    private int key;
    private String text;
    
    DataType(int key, String text) {
        this.key = key;
        this.text = text;
    }

    public int getKey() {
        return this.key;
    }

    public int key() {
        return this.key;
    }
    
    /**
     * 根据key得到枚举类型
     * @param key
     * @return
     */
    public static DataType valueOf(int key) {
        DataType[] enums = DataType.values();
        if (enums != null) {
            for (DataType enum1 : enums) {
                if (enum1.key() == key) {
                    return enum1;
                }
            }
        }
        return null;
    }

    @Override
    public java.lang.String getValue() {
        return null;
    }

    @Override
    public java.lang.String getText() {
        return null;
    }
}
