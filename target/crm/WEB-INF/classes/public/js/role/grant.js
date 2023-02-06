$(function() {
    loadModuleData();
});
var zTreeObj;

function loadModuleData(){
    var setting = {
        //使用复选框
        check:{
            enable:true
        },
        data:{
            simpleData:{
                enable:true
            }
        },
        //绑定函数
        callback:{
            onCheck:zTreeOnCheck
        }
    }
    //ajax查询资源列表
    $.ajax({
        type:"get",
        url:ctx +"/module/queryAllModules?roleId="+$("[name='roleId']").val(),
        dataType:"json",
        success:function(data) {
            zTreeObj = $.fn.zTree.init($("#test1"),setting,data);
        }
    });
}
function zTreeOnCheck(event,treeId,treeNode){
    var nodes = zTreeObj.getCheckedNodes(true);
    //判断并且遍历选中的集合
    if(nodes.length>0){
        var mIds = "mIds=";
        for(var i=0;i<nodes.length;i++){
            if(i<nodes.length-1){
mIds += nodes[i].id +"&mIds=";
            }else{
                mIds += nodes[i].id;
            }
        }
    }
    //获取授权id
    var roleId = $("[name='roleId']").val();
    //发送ajax
    $.ajax({
type:"post",
url:ctx +"/role/addGrant",
data:mIds+"&roleId="+roleId,
dataType:"json",
        success:function (data){

        }
    })
};