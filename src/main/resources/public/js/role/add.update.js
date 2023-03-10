layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;



    form.on('submit(addOrUpdateRole)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
     var formData = data.field;
        var url = ctx+"/role/add";
        var roleId = $("[name='id']").val();
        if(roleId!= null && roleId != '') {
            url = ctx + "/role/update";
        }
        $.post(url,formData,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功!",{icon:6});
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

});