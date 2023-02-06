package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CusDevPlanMapper;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {


    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;
    @Resource
    private SaleChanceMapper saleChanceMapper;
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery cusDevPlanQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        PageInfo<CusDevPlan> pageInfo=new PageInfo<CusDevPlan>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan) {
checkCusDevPlanParams(cusDevPlan);
//是否有效 默认有效
        cusDevPlan.setIsValid(1);
        //创建时间，当前系统时间
        cusDevPlan.setCreateDate(new Date());
        //修改时间  系统当前时间
        cusDevPlan.setUpdateDate(new Date());
        //执行添加操作，判断受影响的行数
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) != 1,"计划项数据添加失败!");
    }

    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        Integer sId = cusDevPlan.getSaleChanceId();
        AssertUtil.isTrue(null == sId || saleChanceMapper.selectByPrimaryKey(sId) ==null,"数据异常请重试");
//计划内容 非空
   AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容不能为空");
   //计划时间 非空
   AssertUtil.isTrue(null == cusDevPlan.getPlanDate(),"计划时间不能为空");
    }
//
@Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *    id 记录必须存在
         *    机会id 非空 记录必须存在
         *    计划项内容非空
         *    计划项时间非空
         * 2. 参数默认值
         *    updateDate  系统时间
         * 3.执行更新 判断结果
         */
        AssertUtil.isTrue(null==cusDevPlan.getId() || cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId())==null,"数据异常请重试!");
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)!=1,"记录项更新失败!");
    }
//
//
    public void deleteCusDevPlan(Integer id){

        AssertUtil.isTrue( null==id,"待删除的记录不存在!");
        CusDevPlan cusDevPlan= cusDevPlanMapper.selectByPrimaryKey(id);
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)!=1,"计划项数据删除失败删除失败!");
    }
//
//



}
