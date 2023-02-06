package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface UserMapper extends BaseMapper<User, Integer> {

    public User queryUserByUserName(String userName);

    public List<Map<String, Object>> queryAllSales();
}