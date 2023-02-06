package com.xxxx.crm.Controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:姚泽栋
 * @Date: 2023/2/1 - 02 - 01 - 10:42
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@RequestMapping("module")
@Controller
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;
@RequestMapping("queryAllModules")
@ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

@RequestMapping("toAddGrantPage")
public String toAddGrantPage(Integer roleId, HttpServletRequest request){
request.setAttribute("roleId",roleId);
    return "role/grant";
}
}
