package com.xxxx.crm.Controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    ResultInfo resultInfo = new ResultInfo();

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {

        UserModel userModel = userService.login(userName, userPwd);
        /**
         * 登录成功后
         *    1.将用户登录信息存入session
         *    2.将用户信息返回给客户端 有客户端(cookie)保存
         */
        resultInfo.setResult(userModel);
        return resultInfo;
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        ResultInfo resultInfo = new ResultInfo();
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
//        userService.updateUserPassword(userId,oldPassword,newPwd,repeatPwd);
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);

        return resultInfo;
    }


    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }
    @RequestMapping("toSettingPage")
    public String toSettingPage() {
        return "user/setting";
    }

    @PostMapping("login02")
    @ResponseBody
    public ResultInfo login02(String userName, String userPwd) {
        UserModel userModel = userService.login(userName, userPwd);
        /**
         * 登录成功后
         *    1.将用户登录信息存入session
         *    2.将用户信息返回给客户端 有客户端(cookie)保存
         */
        resultInfo.setResult(userModel);
        return resultInfo;
    }


    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    //分页多条件查询用户列表
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success("用户添加成功！");
    }
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户更新成功！");
    }
    //打开或者添加用户页面
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request){
        System.out.println(id);
        //判断id是否为空
        if(id != null){
            User user = userService.selectByPrimaryKey(id);
            request.setAttribute("userInfo",user);
        }
        return "user/add_update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功");
    }
}


