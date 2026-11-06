<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account</title>

        <style>
            *{
                margin:0;
                padding:0;
                box-sizing:border-box;
                font-family:Arial, Helvetica, sans-serif;
            }

            body{
                background:#f5f7fa;
                display:flex;
                justify-content:center;
                align-items:center;
                height:100vh;
            }

            .container{
                width:430px;
                background:white;
                padding:35px;
                border-radius:12px;
                box-shadow:0 8px 25px rgba(0,0,0,0.15);
            }

            h2{
                text-align:center;
                color:#2c3e50;
                margin-bottom:25px;
            }

            .form-group{
                margin-bottom:18px;
            }

            label{
                display:block;
                margin-bottom:6px;
                font-weight:bold;
                color:#555;
            }

            input[type=text],
            input[type=password]{
                width:100%;
                padding:10px;
                border:1px solid #ccc;
                border-radius:6px;
                font-size:15px;
            }

            input[readonly]{
                background:#f2f2f2;
            }

            .error{
                color:red;
                font-size:13px;
                margin-top:4px;
            }

            .button-group{
                display:flex;
                justify-content:space-between;
                margin-top:25px;
            }

            .btn{
                width:48%;
                padding:11px;
                border:none;
                border-radius:6px;
                cursor:pointer;
                font-size:15px;
                font-weight:bold;
                transition:.3s;
            }

            .create{
                background:#28a745;
                color:white;
            }

            .create:hover{
                background:#218838;
            }

            .reset{
                background:#dc3545;
                color:white;
            }

            .reset:hover{
                background:#c82333;
            }

            .general-error{
                text-align:center;
                color:red;
                margin-top:18px;
                font-weight:bold;
            }

            .back{
                text-align:center;
                margin-top:20px;
            }

            .back a{
                color:#007bff;
                text-decoration:none;
            }

            .back a:hover{
                text-decoration:underline;
            }
        </style>

    </head>

    <body>

        <div class="container">

            <h2>Create New Account</h2>

            <form action="MainController" method="POST">

                <div class="form-group">
                    <label>User ID</label>
                    <input type="text" name="uID" required/>
                    <div class="error">${requestScope.USER_ERROR.uID}</div>
                </div>

                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="name" required/>
                    <div class="error">${requestScope.USER_ERROR.name}</div>
                </div>

                <div class="form-group">
                    <label>Role</label>
                    <input type="text" name="roleID" value="US" readonly/>
                    <div class="error">${requestScope.USER_ERROR.roleID}</div>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" required/>
                    <div class="error">${requestScope.USER_ERROR.password}</div>
                </div>

                <div class="form-group">
                    <label>Confirm Password</label>
                    <input type="password" name="confirm" required/>
                    <div class="error">${requestScope.USER_ERROR.confirm}</div>
                </div>

                <div class="button-group">
                    <input class="btn create"
                           type="submit"
                           name="action"
                           value="Create"/>

                    <input class="btn reset"
                           type="reset"
                           value="Reset"/>
                </div>

                <div class="general-error">
                    ${requestScope.USER_ERROR.error}
                </div>

            </form>

            <div class="back">
                <a href="login.jsp">← Back to Login</a>
            </div>

        </div>

    </body>
</html>