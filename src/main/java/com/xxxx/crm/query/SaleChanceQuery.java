package com.xxxx.crm.query;


import com.xxxx.base.BaseQuery;
//营销机会
public class SaleChanceQuery extends BaseQuery {
    //分页参数
    // 客户名
    private String customerName;
    // 创建人
    private String createMan;
    // 分配状态(0=未分配,1=已分配)
    private Integer state;
//客户开发计划 条件查询
private Integer devResult;//开发状态
private Integer assignMan;//指派人

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan(Integer userId) {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }
}
