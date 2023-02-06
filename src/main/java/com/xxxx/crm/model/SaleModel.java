package com.xxxx.crm.model;

/**
 * @Auther:JiShuaiHao
 * @Date:2023/1/7
 */
public class SaleModel {
    private Integer id;
    private String name;


    public SaleModel() {
    }

    public SaleModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "SaleModel{id = " + id + ", name = " + name + "}";
    }
}
