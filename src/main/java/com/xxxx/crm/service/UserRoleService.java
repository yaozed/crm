package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/31 - 01 - 31 - 11:21
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {
@Resource
    private UserRoleMapper userRoleMapper;
}
