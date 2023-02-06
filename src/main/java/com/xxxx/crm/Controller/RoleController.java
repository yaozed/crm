package com.xxxx.crm.Controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/31 - 01 - 31 - 8:48
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){

        return roleService.queryAllRoles(userId);
    }
   @GetMapping("list")
   @ResponseBody
    public Map<String,Object> selectByParams(RoleQuery roleQuery){
        return  roleService.queryByParamsForTable(roleQuery);
   }
   //进入角色管理页面
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }
    //添加
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role){
        roleService.addRole(role);
        return success("角色添加成功");
    }
    @RequestMapping("addOrUpdateRolePage")
    public  String addOrUpdateRolePage(Integer roleId, HttpServletRequest request){
        if(roleId != null){
            Role role = roleService.selectByPrimaryKey(roleId);
request.setAttribute("role",role);
        }
        return "role/add_update";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色修改成功!");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRole(roleId);
        return success("角色删除成功!");
    }
    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId,Integer[] mIds){
        roleService.addGrant(roleId,mIds);
        return success("角色授权成功!");
    }
}
