package com.xxxx.crm.Controller;

import com.xxxx.base.BaseController;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.annotation.RequiredPermission;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequiredPermission(code="1010")
    @RequestMapping("index")
    public String index() {
        return "saleChance/salechance";
    }


    //营销机会
    //flag为1，是客户开发计划，否则是营销机会
    @RequiredPermission(code="101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery, Integer flag,HttpServletRequest request) {
        if( flag != null && flag ==1 ){
            //查询客户开发计划
            //设置分配状态
saleChanceQuery.setState(StateStatus.STATED.getType());
//设置指派人
Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
saleChanceQuery.getAssignMan(userId);
        }

        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    @RequiredPermission(code="101002")
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request) {
String userName = CookieUtil.getCookieValue(request,"userName");
saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会添加成功");

    }
    //进入添加或者修改页面
@RequestMapping("toSaleChancePage")
public String toSaleChancePage(Integer saleChanceId ,HttpServletRequest request){
      if(saleChanceId != null) {
          SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
request.setAttribute("saleChance",saleChance);
      }
    return "saleChance/add_update";
}


    @RequiredPermission(code="101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance( SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功");
    }
    @RequiredPermission(code="101003")
    @PostMapping("del")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success("机会数据删除成功!");
    }

    //更新营销机会开发状态
    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态成功!");
    }
}
