layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $.post(ctx+"/user/queryAllSales",function (res) {
        for (var i = 0; i < res.length; i++) {
            if($("input[name='man']").val() == res[i].id ){
                $("#assignMan").append("<option value=\"" + res[i].id + "\" selected='selected' >" + res[i].name + "</option>");
            }else {
                $("#assignMan").append("<option value=\"" + res[i].id + "\">" + res[i].name + "</option>");
            }
        }
        //重新渲染
        layui.form.render("select");
    });

    form.on("submit(addOrUpdateSaleChance)", function (data) {
        var index = layer.msg('数据提交中，请稍候', {
            icon: 16, time: false, shade: 0.8
        });
        //弹出loading
        var url = ctx + "/sale_chance/add";
        var saleChanceId = $("[name='id']").val();
        if(saleChanceId != null && saleChanceId != ''){
            url = ctx + "/sale_chance/update";
        }
        $.post(url, data.field, function (res) {
            if (res.code == 200) {
                layer.msg("操作成功",{icon:6})
                layer.close(index);
                layer.closeAll("iframe");
                parent.location.reload();
            } else {
                layer.msg(res.msg,{icon:5});
            }
        });
        return false;
    })
$("#close_btn").click(function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});
});
