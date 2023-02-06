package com.xxxx.crm.Controller;

import com.xxxx.base.BaseController;
//import com.xxxx.crm.query.CusDevPlanQuery;
//import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.base.ResultInfo;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.vo.CusDevPlan;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/9 - 01 - 09 - 19:42
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
    @Controller
    @RequestMapping("cus_dev_plan")
    public class CusDevPlanController extends BaseController {

        @Resource
        private SaleChanceService saleChanceService;

        @Resource
        private CusDevPlanService cusDevPlanService;

        @RequestMapping("index")
        public String index() {
            return "cusDevPlan/cus_dev_plan";
        }


        @RequestMapping("toCusDevPlanPage")
        public String toCusDevPlanDataPage(Integer id, HttpServletRequest request) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            request.setAttribute("saleChance",saleChance);
            return "cusDevPlan/cus_dev_plan_data";
        }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryCusDevPlansByParams(cusDevPlanQuery);
    }
    //添加客户计划
    @PostMapping("add")
    @ResponseBody
public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
      cusDevPlanService.addCusDevPlan(cusDevPlan);
      return success("计划项添加成功！");
    }
@RequestMapping("addOrUpdateCusDevPlanPage")
public String addOrUpdateCusDevPlanPage(HttpServletRequest request,Integer sId,Integer id){
           request.setAttribute("sId",sId);
      //通过计划项id查询记录
    CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
    request.setAttribute("cusDevPlan",cusDevPlan);
            return "cusDevPlan/add_update";
}



        @RequestMapping("update")
        @ResponseBody
        public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan) {
            cusDevPlanService.updateCusDevPlan(cusDevPlan);
            return success("计划项数据更新成功");
        }

        @RequestMapping("delete")
        @ResponseBody
        public ResultInfo deleteCusDevPlan(Integer id) {
            cusDevPlanService.deleteCusDevPlan(id);
            return success("计划项数据删除成功");
        }


    }
