package com.ch.date1008;

/**
 * 体征采集数据类
 */

public class DataBean {

    private String name;//输入项名称
    private String unit;//输入项单位
    private String min;//数值最小值
    private String max;//数值最大值
    private String defaultValue;//默认值
    private String current;//当前值
    private String minDefault;//默认最低范围值
    private String maxDefault;//默认最高范围值


    public DataBean(String name, String unit, String min, String current, String max) {
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
        this.current = current;
    }

    public DataBean() {
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMaxDefault() {
        return maxDefault;
    }

    public void setMaxDefault(String maxDefault) {
        this.maxDefault = maxDefault;
    }

    public String getMinDefault() {
        return minDefault;
    }

    public void setMinDefault(String minDefault) {
        this.minDefault = minDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
