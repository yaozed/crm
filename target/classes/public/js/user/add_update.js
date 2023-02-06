layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var  formSelects = layui.formSelects;


    form.on('submit(addOrUpdateUser)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var formData = data.field;
         var url=ctx+"/user/add";
        if($("[name='id']").val()){
            url=ctx+"/user/update";
        }
        $.post(url,formData,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功",{icon:6});
                top.layer.close(index);
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }else{
                layer.msg(res.msg,{icon:5});
            }
        });
        return false;
    });
    $("#closeBtn").click(function(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

//加载角色下拉框
     var userId=$("[name='id']").val();
 formSelects.config("selectId",{
     type:"post",
     searchUrl: ctx+"/role/queryAllRoles?userId="+userId,
     keyName:'roleName',
     keyVal:'id'
 },true);


});