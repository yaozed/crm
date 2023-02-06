package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.enums.DevResult;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        List<SaleChance> saleChances = saleChanceMapper.selectByParams(saleChanceQuery);
        PageInfo<SaleChance> pageInfo=new PageInfo<>(saleChances);
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }
    /**
     * 1.参数校验
     *      customerName  客户名非空
     *      linkMan  非空
     *      linkPhone  非空 11位手机号
     * 2. 设置相关参数默认值
     *       state 默认未分配   如果选择分配人  state 为已分配状态
     *       assignTime 默认空   如果选择分配人  分配时间为系统当前时间
     *       devResult  默认未开发  如果选择分配人 devResult 为开发中 0-未开发  1-开发中 2-开发成功 3-开发失败
     *       isValid  默认有效(1-有效  0-无效)
     *       createDate  updateDate:默认系统当前时间
     * 3.执行添加 判断添加结果
     */
@Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
     checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone(),saleChance.getCgjl(),saleChance.getOverview(),saleChance.getDescription());

saleChance.setIsValid(1);
saleChance.setCreateDate(new Date());
saleChance.setUpdateDate(new Date());
//判断是否设置指派人
if(StringUtils.isBlank(saleChance.getAssignMan())){
    //为空
    saleChance.setState(StateStatus.UNSTATE.getType());
    saleChance.setAssignMan(null);
    saleChance.setDevResult(DevResult.UNDEV.getStatus());
}else{
    //不为空
    saleChance.setState(StateStatus.STATED.getType());
    saleChance.setAssignTime(new Date());
    saleChance.setDevResult(DevResult.DEVING.getStatus());
}
//添加操作
    AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"添加营销机会失败");
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone,Integer cgjl, String overview, String description) {
    AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空");
    AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空");
    AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系号码不能为空");
    AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"手机号码格式不正确");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
        AssertUtil.isTrue(null == saleChance.getId(), "待更新记录不存在");
//通过主键查询对象
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
//判断数据库对应的记录存在
        AssertUtil.isTrue(temp == null, "待更新记录不存在");
        //参数校验
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(), saleChance.getLinkPhone(),saleChance.getCgjl(),saleChance.getOverview(),saleChance.getDescription());
//2设置默认值
        saleChance.setUpdateDate(new Date());
        //判断原始数据是否存在
        if (StringUtils.isBlank(temp.getAssignMan())) {//不存在
//判断修改后的值是否存在
            if (!StringUtils.isBlank(saleChance.getAssignMan())) {//修改前为空，修改后有值
//时间
                saleChance.setAssignTime(new Date());
                //分配状态已分配
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            } else {//存在
                //判断修改后的值是否存在
                if (StringUtils.isBlank(saleChance.getAssignMan())) {
                    //设置时间为null
                    saleChance.setAssignTime(null);
                    saleChance.setState(StateStatus.UNSTATE.getType());
                    saleChance.setDevResult(DevResult.UNDEV.getStatus());
                } else {//修改前有值,修改后有值
                    //判断前后是否是同一个用户
                    if (!saleChance.getAssignMan().equals(temp.getAssignMan())) {
                        //更新指派时间
                        saleChance.setAssignTime(new Date());
                    }else{
                        //设置指派时间为修改前的时间
                        saleChance.setAssignTime(temp.getAssignTime());
                    }
                }
            }
        }
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) !=1,"更新营销机会失败");
    }

//删除
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除记录!");
        AssertUtil.isTrue(deleteBatch(ids)!=ids.length,"记录删除失败!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
    //判断id是否为空
        AssertUtil.isTrue(null == id,"待更新记录不存在!");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == saleChance,"待更新记录不存在!");
 //设置开发状态
 saleChance.setDevResult(devResult);
 //执行更新操作
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!= 1,"开发状态失败!");
}


}
