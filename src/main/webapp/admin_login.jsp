<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员登录页面</title>
    <link href="/css/back/style.css" rel="stylesheet">
    <link href="/css/bootstrap/3.3.6/bootstrap.min.css">
    <style type="text/css">
        *{
            padding: 0;
            margin: 0;
        }

        body {
            background-image: url("img/background/adminLogin.jpg");/*设置背景图片*/
            background-size: 100% 100%;
        }
    </style>

    <script type="text/javascript" src="/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".login").click(function () {
                var adminName = $(".adminName").val();
                var adminPassword = $(".adminPassword").val();
                var checkCode = $(".checkCode").val();
                var page = "UsernamePasswordLogin.admin";

                //ajax登录请求
                $.post(
                    page,
                    {"adminName":adminName,"adminPassword":adminPassword,"checkCode":checkCode},

                    function (message) {
                        if(message != "success"){    //登录校验失败时，显示错误提示信息，并刷新图形验证码
                            $(".errorMessage").html(message);
                            refreshGraphicsCode();
                        } else{    //登录校验成功，跳转至后台管理主页
                            window.location.href="admin_category_list";
                        }
                    }
                )
            });

            $(".form-control").focus(function () {
                $(".errorMessage").html("&nbsp;")    //当把鼠标放在光标上时，隐藏错误提示信息
            });
        });

        //当用户点击图片时，刷新图形验证码
        function refreshGraphicsCode(){
            $("img").attr("src","createGraphicsCode?date="+new Date());
        }
    </script>
</head>
<body>
<div class="container" style="text-align: center">
    <div class="col-md-4"></div>
    <div class="col-md-4" style="background-color:#eee;margin: 10% auto;height: 360px;width: 600px">
        <br>
        <div style="text-align: center;">
            <h2>体育商城后台管理系统</h2>
        </div>
        <br><br>
        <div style="text-align: center;">
            <div class="form-group">
                <label>用户名:</label>
                <input type="text" name="adminName" class="adminName form-control" style="width:300px; height:30px;">
            </div>
            <br>
            <div class="form-group">
                &nbsp;&nbsp;&nbsp;<label>密码:</label>
                <input type="password" name="adminPassword" class="adminPassword form-control" style="width:300px; height:30px;">
            </div>
            <br>
            <div class="form-group">
                <label>验证码:</label>
                <input type="text" name="checkCode" class="checkCode form-control" style="width:200px; height:30px;">&nbsp;&nbsp;&nbsp;<img class="GraphicsCode" src="createGraphicsCode" width="80px" height="40px" align="absmiddle" onclick="refreshGraphicsCode()"/>
            </div>
            <br>
            <span class="errorMessage" style="color: red;font-size: 15px;font-family: Arial;letter-spacing: 1px;font-weight: bold">&nbsp;</span>
            <br><br>
            <div class="form-group">
                <a href="#" class="login"><button class="btn btn-success form-control" style="background-color: #4cae4c;width: 300px;height: 30px">登 录</button></a>
            </div>
        </div>
    </div>
    <div class="col-md-4"></div>
</div>
</body>
</html>
