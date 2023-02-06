layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var tableIns = table.render({
        id:'saleChanceTable',
        elem: '#saleChanceList',
        url: ctx + '/sale_chance/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],//每页页数的可选项
        limit: 10,//默认每页显示的数量
        toolbar: "#toolbarDemo",
        id: "saleChanceListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'chanceSource', title: '机会来源', align: "center"},
            {field: 'customerName', title: '客户名称', align: 'center'},
            {field: 'cgjl', title: '成功几率', align: 'center'},
            {field: 'overview', title: '概要', align: 'center'},
            {field: 'linkMan', title: '联系人', align: 'center'},
            {field: 'linkPhone', title: '联系电话', align: 'center'},
            {field: 'description', title: '描述', align: 'center'},
            {field: 'createMan', title: '创建人', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'uname', title: '分配人', align: 'center'},
            {field: 'assignTime', title: '分配时间', align: 'center'},
            {
                field: 'state', title: '分配状态', align: 'center',
                templet: function (d) {
                    return formatterState(d.state);
                }
            },
            {
                field: 'devResult', title: '开发状态', align: 'center', templet: function (d) {
                    return formatterDevResult(d.devResult);
                }
            },
            {title: '操作', templet: '#saleChanceListBar', fixed: "right", align: "center", minWidth: 150}
        ]]
    });

    function formatterState(state) {
        if (state == 0) {
            return "<div style='color:yellow '>未分配</div>";
        } else if (state == 1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    function formatterDevResult(value) {
        /**
         * 0-未开发
         * 1-开发中
         * 2-开发成功
         * 3-开发失败
         */
        if (value == 0) {
            return "<div style='color: yellow'>未开发</div>";
        } else if (value == 1) {
            return "<div style='color: #00FF00;'>开发中</div>";
        } else if (value == 2) {
            return "<div style='color: #00B83F'>开发成功</div>";
        } else if (value == 3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }

    $(".search_btn").click(function () {
        //多条件查询
        tableIns.reload({
            where: {
                customerName: $("[name='customerName']").val(),
                createMan: $("[name='createMan']").val(),
                state: $("#state").val()
            }
            , page: {
                curr: 1
            }
        });
    })


    //头工具栏事件
    table.on('toolbar(saleChances)', function (data) {
        if (data.event == "add") {
            openSaleChanceDialog();
        } else if (data.event == "del") {
            delSaleChance(data);
        }
    });
    // 打开添加营销机会数据页面
    function openSaleChanceDialog(saleChanceId) {
        var title = "<h2>营销机会管理-添加营销机会</h2>";
        var url = ctx + "/sale_chance/toSaleChancePage";
        //判断营销机会ID是否为空
        if(saleChanceId != null && saleChanceId != ''){
//更新操作
            title = "<h2>营销机会管理-修改营销机会</h2>"
            //请求地址传递营销机会的ID
            url += '?saleChanceId=' + saleChanceId;
        }
        layer.open({
            type: 2,
            title: title,
            area: ['500px', '620px'],
            content: url,
            maxmini: true
        });
    }


//单条删除
table.on('tool(saleChances)',function(data){
//判断类型
    if(data.event == "edit"){//编辑操作,代开窗框
var saleChanceId = data.data.id;
        openSaleChanceDialog(saleChanceId);
    }else if(data.event == "del"){//删除操作，打开窗口
        layer.confirm('确定删除当前数据？', {icon: 3, title: "机会数据管理"}, function (index) {
            layer.close(index);
            $.ajax({
                type: "post",
                url: ctx + "/sale_chance/del",
                data: {
                 ids:data.data.id
                },
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg, {icon: 5});
                    }
                }
            })
        })
    }
});



    /**
     //  * 批量删除
     //  * @param datas
     //  */
    function delSaleChance(data) {
        //获取ID属性
        var checkStatus = table.checkStatus(data.config.id);
        var saleChanceData = checkStatus.data;

        // var saleChanceData = data;
        if (saleChanceData.length < 1) {
            layer.msg("请选择删除记录!", {icon: 5});
            return;
        }
        layer.confirm('确定删除选中的机会数据？', {
            icon:3,title:'营销机会管理'
        }, function (index) {
            layer.close(index);
            var ids = "ids=";
            for (var i = 0; i < saleChanceData.length; i++) {
                if (i < saleChanceData.length - 1) {
                    ids = ids + saleChanceData[i].id + "&ids=";
                } else {
                    ids = ids + saleChanceData[i].id
                }
            }
            $.ajax({
                type: "post",
                url: ctx + "/sale_chance/del",
                data: ids,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        //提示成功
                        layer.msg("删除成功",{icon:6});
                        //刷新表格
                        tableIns.reload();
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            })
        });
    }


});

