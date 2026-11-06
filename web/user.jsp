<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>

        <style>
            *{
                margin:0;
                padding:0;
                box-sizing:border-box;
                font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;
            }

            body{
                height:100vh;
                display:flex;
                justify-content:center;
                align-items:center;
                background:linear-gradient(135deg,#4facfe,#00f2fe);
            }

            .user-box{
                width:450px;
                background:#fff;
                padding:40px;
                border-radius:20px;
                box-shadow:0 15px 40px rgba(0,0,0,.2);
            }

            h2{
                text-align:center;
                color:#333;
                margin-bottom:30px;
                font-size:32px;
            }

            .info{
                margin-bottom:18px;
                font-size:17px;
                display:flex;
                justify-content:space-between;
                border-bottom:1px solid #eee;
                padding-bottom:10px;
            }

            .label{
                font-weight:bold;
                color:#555;
            }

            .value{
                color:#222;
            }

            .menu-btn{
                display:block;
                width:100%;
                margin-top:30px;
                text-align:center;
                padding:15px;
                background:#4facfe;
                color:white;
                text-decoration:none;
                border-radius:10px;
                font-size:17px;
                font-weight:bold;
                transition:.3s;
            }

            .menu-btn:hover{
                background:#008cff;
                transform:translateY(-2px);
            }
        </style>

    </head>

    <body>

        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <div class="user-box">

            <h2>Welcome</h2>

            <div class="info">
                <span class="label">UID</span>
                <span class="value">${sessionScope.LOGIN_USER.uID}</span>
            </div>

            <div class="info">
                <span class="label">Name</span>
                <span class="value">${sessionScope.LOGIN_USER.name}</span>
            </div>

            <div class="info">
                <span class="label">Role</span>
                <span class="value">${sessionScope.LOGIN_USER.roleID}</span>
            </div>

            <div class="info">
                <span class="label">Password</span>
                <span class="value">${sessionScope.LOGIN_USER.password}</span>
            </div>

            <a class="menu-btn" href="MainController?action=ViewProduct">
                🍹 Go to Drink Menu
            </a>

        </div>

    </body>
</html>