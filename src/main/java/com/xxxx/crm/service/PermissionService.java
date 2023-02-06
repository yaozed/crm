package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:姚泽栋
 * @Date: 2023/2/1 - 02 - 01 - 16:15
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
@Resource
private PermissionMapper permissionMapper;
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {

        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }
}
