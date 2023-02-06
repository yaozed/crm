package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
//查询所有角色列表

   public List<Map<String,Object>> queryAllRoles(Integer userId);

  public Role selectByRoleName(String roleName);
}