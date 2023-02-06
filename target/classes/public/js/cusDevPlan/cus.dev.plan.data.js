layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //计划项数据展示
    var  tableIns = table.render({
        elem: '#cusDevPlanList',
        url : ctx+'/cus_dev_plan/list?saleChanceId='+$("[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "cusDevPlanListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'planItem', title: '计划项',align:"center"},
            {field: 'exeAffect', title: '计划时间效果',align:"center"},
            {field: 'planDate', title: '执行时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#cusDevPlanListBar"}
        ]]
    });

table.on('toolbar(cusDevPlans)',function (data){
if(data.event == "add"){
    //打开添加或者修改计划项的页面
    openAddOrUpdateCusDevPlanDialog();
}else if(data.event =="success"){//开发成功
updateSaleChanceDevResult(2);
}else if(data.event == "failed"){//开发失败
updateSaleChanceDevResult(3);
}
});
//修改或者删除
    table.on('tool(cusDevPlans)',function (data){
        if(data.event == "edit") {
            //打开添加或者修改计划项的页面
            openAddOrUpdateCusDevPlanDialog(data.data.id);
        }else if(data.event == "del"){
            //删除计划项
            deleteCusDevPlan(data.data.id)
        }
    });

    function openAddOrUpdateCusDevPlanDialog(id) {
        var title="计划项管理管理-添加计划项";
        var url=ctx+"/cus_dev_plan/addOrUpdateCusDevPlanPage?sId="+$("[name='id']").val();
        //判断计划项id是否为空
        if(id != null && id!= ''){
            //更新操作
            title = "计划项管理 - 更新计划项";
            url += "&id=" +id;
        }
        layui.layer.open({
            title:title,
            type:2,
            area:["500px","300px"],
            maxmin:true,
            content:url
        })
    }
function deleteCusDevPlan(id){
        //弹出确认框，询问是否删除
    layer.confirm('你确认要删除该记录吗?',{icon:3,titile:'开发项数据管理'},function (index){
        //发送ajax请求
        $.post(ctx + '/cus_dev_plan/delete',{id:id},function (result){
          if(result.code == 200){
//提示成功
              layer.msg('删除成功',{icon:6});
              //刷新数据表格
              tableIns.reload();
          }  else {
              layer.msg(result.msg,{icon:5});
          }
        });
    });
}




function updateSaleChanceDevResult(devResult){
        layer.confirm('您确认执行该操作吗?',{icon:3,title:"更改开发状态"},function (index){
//得到需要被更新的营销机会id
         var sId = $("[name='id']").val();
         $.post(ctx+ '/sale_chance/updateSaleChanceDevResult',{id:sId,devResult:devResult},function (result){
           if(result.code == 200){
               layer.msg('更新成功!',{icon:6});
               //关闭窗口
               layer.closeAll("iframe");
               //刷新父页面
               parent.location.reload();
           }  else{
layer.msg(result.msg,{icon:5});
           }
         });
        });
}



});
