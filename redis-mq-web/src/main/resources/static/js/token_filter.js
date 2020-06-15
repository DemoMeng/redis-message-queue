function ct() {
    //layuiAdmin 自定义的表名  sessionData关闭页面即删除token
    //如果前端本地表中的token为空，则表示未登录
    layui.use('layer',function(){
        let token = layui.data("tokens").token;
        console.log(token);
        layui.$.ajax({
            url:"http://localhost:3040/system/ct",
            data:null,
            type:"GET",
            headers:{'Accept':'application/json','token':token},
            dataType:"JSON",
            contentType:"application/json;charset=UTF-8",
            success:function(data){
                let code = data.code;
                if(code == 500) {
                    location.href = "http://localhost:3040/index/login";
                }
            },
            error:function(data){
                layui.$.alert('发生未知错误',data.msg);
            }
        });
    })
};
