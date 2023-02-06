package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.utils.*;
import com.xxxx.crm.vo.User;
import com.xxxx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    public UserModel login(String userName, String userPwd) {
        /**
         * 1.参数校验
         *    用户名  非空
         *    密码    非空
         * 2.根据用户名  查询用户记录
         * 3.用户存在性校验
         *     不存在   -->记录不存在  方法结束
         * 4.用户存在
         *     校验密码
         *        密码错误 -->密码不正确   方法结束
         * 5.密码正确
         *     用户登录成功  返回用户信息
         */
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空!");
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null == user, "用户不存在或已注销!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))), "用户密码不正确，请重新输入!");
        return buildUserInfo(user);
    }

    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
//通过用户Id查询用户记录,返回用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //判断用户记录是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在!");
        //参数校验
        checkPasswordParams(userId, oldPwd, newPwd, repeatPwd);
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "修改密码失败");
    }

    private void checkPasswordParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User temp = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == temp, "用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原始密码!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输入确认密码!");
        AssertUtil.isTrue(!(temp.getUserPwd().equals(Md5Util.encode(oldPassword))), "原始密码不正确!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "新密码输入不一致!");
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码与原始密码不能相同!");
    }

    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }


    /**
     * 1.参数校验
     * 用户名 非空 值唯一
     * email  非空  格式合法
     * 手机号非空  格式合法
     * 2.默认参数设置
     * isValid  1
     * createDate  系统时间
     * updateDate 系统时间
     * 默认密码设置   123456
     * 3.执行添加
     */
    //用户增加操作
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(),null);
   user.setIsValid(1);
   user.setCreateDate(new Date());
   user.setUpdateDate(new Date());
   //设置默认密码
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user)!=1,"添加用户失败");
        /*
         *  用户角色关联
         * 用户ID   角色ID
         **/
    relationUserRole(user.getId(),user.getRoleIds());
    }

    private void relationUserRole(Integer userId, String roleIds) {
        //
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,"用户角色分配失败!");
        }
        if(StringUtils.isNotBlank(roleIds)){
         //将用户角色数据设置到集合中，执行批量添加
         List<UserRole> userRoleList = new ArrayList<>();
         String [] roleIdArray = roleIds.split(",");
         for(String roleId : roleIdArray){
             UserRole userRole = new UserRole();
             userRole.setRoleId(Integer.parseInt(roleId));
             userRole.setUserId(userId);
             userRole.setCreateDate(new Date());
             userRole.setUpdateDate(new Date());
             userRoleList.add(userRole);
         }
         //批量添加用户角色用户
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList) != userRoleList.size(),"用户角色分配失败");
        }
    }

    private void checkUserParams(String userName, String email, String phone,Integer userId) {
//判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");;
        //判断用户名的唯一性
        //通过用户名查询用户对象
        User temp = userMapper.queryUserByUserName(userName);
        //如果用户对象为空，则表示用户名可用，如果用户对象不为空，则表示用户名不可用
        AssertUtil.isTrue(null!=temp&& !(temp.getId().equals(userId)),"用户名已存在,请重新输入");
        //邮箱非空
        AssertUtil.isTrue(StringUtils.isBlank(email),"用户邮箱不能为空");
        //手机号 非空
        AssertUtil.isTrue(StringUtils.isBlank(phone),"用户手机号不能为空");
        //手机号 格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式不正确");

    }
    //修改
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        AssertUtil.isTrue(null == user.getId(),"待更新记录不存在");
        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(null == temp,"待更新记录不存在！");
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(),user.getId());
        //设置默认值
        user.setUpdateDate(new Date());
        //更新操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)!=1,"用户更新失败！");
        relationUserRole(user.getId(),user.getRoleIds());
    }
    //删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"待删除记录不存在!");
        AssertUtil.isTrue(userMapper.deleteBatch(ids)!=ids.length,"用户记录删除失败!");

        for(Integer userId :ids){
            Integer count = userRoleMapper.countUserRoleByUserId(userId);
            if(count>0){
                AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,"删除用户!");

            }
        }


    }
}


