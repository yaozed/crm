layui.use(['table','layer',"form"],function() {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/user/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "id", title: '编号', fixed: "true", width: 80},
            {field: 'userName', title: '用户名', minWidth: 50, align: "center"},
            {field: 'email', title: '用户邮箱', minWidth: 100, align: 'center'},
            {field: 'phone', title: '手机号', minWidth: 100, align: 'center'},
            {field: 'trueName', title: '真实姓名', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updateDate', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#userListBar', fixed: "right", align: "center"}
        ]]
    })
    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("userListTable",{
            page:{
                curr:1
            },
            where:{
                userName:$("input[name='userName']").val(),// 用户名
                email:$("input[name='email']").val(),// 邮箱
                phone:$("input[name='phone']").val()    //手机号
            }
            ,page:{
                curr: 1
            }
        })
    });
    // 头工具栏事件
    table.on('toolbar(users)',function (data) {
if(data.event == "add"){//添加用户
    openAddOrUpdateUserDialog();//打开对话框
} else if(data.event == "del"){
    var checkStatus = table.checkStatus(data.config.id)
    //删除多个记录
    delUser(checkStatus.data);
}
    });




    table.on('tool(users)',function (data) {
        if(data.event == "edit"){
            openAddOrUpdateUserDialog(data.data.id);
        }else if(data.event =="del"){
            delUserOne(data.data.id);
        }
    });
    function delUserOne(id){
        layer.confirm('确定删除该记录吗？',{icon:3,title:'用户管理'},function (index) {
            layer.close(index);
            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:{
                  ids:id
                },
                success:function (res) {
                    if(res.code==200){
                        layer.msg("删除成功！",{icon:6})
                        tableIns.reload();
                    }else{
                        layer.msg(res.msg,{icon:5});
                    }
                }
            })
        })
    }

    function delUser(userData){

        if(userData.length==0){
            layer.msg("请选择待删除记录!",{icon:5});
            return;
        }
        layer.confirm('确定删除选中的记录吗？',{icon:3,title:'用户管理'},function (index) {
            layer.close(index);
            // ids=10&ids=20&ids=30
            var ids="ids=";
            for(var i=0;i<userData.length;i++){
                if(i<userData.length-1){
                    ids=ids+userData[i].id+"&ids=";
                }else{
                    ids=ids+userData[i].id;
                }
            }
            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:ids,
                success:function (res) {
                    if(res.code==200){
                       layer.msg("删除成功！",{icon:6})
                        tableIns.reload();
                    }else{
                        layer.msg(res.msg,{icon:5});
                    }
                }
            })
        })
    }

    // 打开添加/修改用户数据页面
    function openAddOrUpdateUserDialog(id) {
        var title = "<h2>用户管理-添加用户管理</h2>";
        var url = ctx + "/user/toAddOrUpdateUserPage";
        //    判断营销机会ID是否为空
        if(id != null && id != ''){
//更新操作
            title = "<h2>用户管理-更新用户</h2>"
            //请求地址传递营销机会的ID
            url += "?id=" + id;
        }
        layui.layer.open({
            type: 2,
            title: title,
            area: ['500px', '620px'],
            content: url,
            maxmini: true
        });
    }
});