package com.xxxx.crm.enums;

/**
 * @Auther:JiShuaiHao
 * @Date:2023/1/15
 */
public enum CustomerServeState {
    // 创建
    CREATED("fw_001"),
    // 分配
    ASSIGNED("fw_002"),
    // 处理
    PROCED("fw_003"),
    // 反馈
    FEED_BACK("fw_004"),
    // 归档
    ARCHIVED("fw_005");

    private String state;

    CustomerServeState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
