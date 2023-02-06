package com.xxxx.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Auther:姚泽栋
 * @Date: 2023/2/1 - 02 - 01 - 10:44
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
public class TreeModel {
    private Integer id;
    @JsonProperty("pId")
    private Integer pId;
    private String name;
private boolean checked  = false;//复选框
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
