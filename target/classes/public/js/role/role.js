layui.use(['table','layer'],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //角色列表展示
    var  tableIns = table.render({
        id : "roleTable",
        elem: '#roleList',
        url : ctx+'/role/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",

        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'roleName', title: '角色名', minWidth:50, align:"center"},
            {field: 'roleRemark', title: '角色备注', minWidth:100, align:'center'},
            {field: 'createDate', tItle: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#roleListBar',fixed:"right",align:"center"}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function () {
        tableIns.reload({

            where:{
                // 角色名
                roleName:$("[name='roleName']").val()
            },
            page:{
                curr:1
            }
        })
    });

    // 头工具栏事件
    table.on('toolbar(roles)',function (data) {
       if(data.event == "add"){
           openAddOrUpdateRoleDialog();
       }else if(data.event == "grant"){
          var checkStatus = table.checkStatus(data.config.id);
      openAddGrantDialog(checkStatus.data);
       }
    });
    //
    //
    table.on('tool(roles)',function (data) {
        var layEvent =data.event;
        if(layEvent == "edit"){
            openAddOrUpdateRoleDialog(data.data.id);
        }else if(layEvent == "del"){

            deleteRole(data.data.id);
        }
    });


    //
    //
    function openAddOrUpdateRoleDialog(roleId) {
        var title="角色管理-角色添加";
        var url=ctx+"/role/addOrUpdateRolePage";
        if(roleId != null && roleId != ''){
            title="角色管理-角色更新";
            url+="?roleId="+roleId;
        }
        layui.layer.open({
            title:title,
            type:2,
            area:["500px","400px"],
            maxmin:true,
            content:url
        })
    }

function deleteRole(roleId){
    layer.confirm("确认删除当前记录?",{icon: 3, title: "角色管理"},function (index) {
    layer.close(index);
        $.ajax({
 type:"post",
            url: ctx +"/role/delete",
            data:{
     roleId:roleId
            },
            success:function (result) {
                if (result.code == 200) {
                    layer.msg("删除成功", {icon: 6});
                    tableIns.reload();
                } else {
                    layer.msg(result.msg, {icon: 5});
                }
            }
        })
    })
}
function openAddGrantDialog(data){
        if(data.length ==0){
            layer.msg("请选择要授权的角色!",{icon:5});
            return;
        }
        //只支持单个授权
    if(data.length>1){
        layer.msg("暂不支持批量角色授权!",{icon:5});
        return;
    }
    var url = ctx +"/module/toAddGrantPage?roleId="+data[0].id;
    var title = "<h3>角色管理-角色授权</h3>";
    layui.layer.open({
        title:title,
        content:url,
        type:2,
    area:["600px","600px"],
        maxmin:true
    });
}

});
