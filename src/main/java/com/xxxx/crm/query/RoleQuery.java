package com.xxxx.crm.query;

import com.xxxx.base.BaseQuery;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/31 - 01 - 31 - 16:53
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
public class RoleQuery extends BaseQuery {
    private String roleName;//角色名

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
