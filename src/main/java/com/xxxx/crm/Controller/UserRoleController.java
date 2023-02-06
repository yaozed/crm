package com.xxxx.crm.Controller;

import com.xxxx.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/31 - 01 - 31 - 11:22
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@Controller
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;
}
