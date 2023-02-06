package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:姚泽栋
 * @Date: 2023/2/1 - 02 - 01 - 10:40
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;
@Resource
private PermissionMapper permissionMapper;
    public List<TreeModel> queryAllModules(Integer roleId) {
        List<TreeModel> treeModelList = moduleMapper.queryAllModules();
        List<Integer> permissionIds = permissionMapper.queryRoleHasModuleIdByRoleId(roleId);
     //判断角色是否拥有资源ID
        if(permissionIds != null && permissionIds.size()>0){
treeModelList.forEach(treeModel ->{
    if(permissionIds.contains(treeModel.getId())){
        treeModel.setChecked(true);
    }
});
        }
        return treeModelList;
    }
}
