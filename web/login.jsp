<%-- 
    Document   : login
    Created on : May 21, 2026, 10:01:56 AM
    Author     : hoadoan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
       
     <script src="https://www.google.com/recaptcha/api.js" async defer></script>
      
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

.login-box{
    width:400px;
    background:#fff;
    padding:40px;
    border-radius:20px;
    box-shadow:0 15px 40px rgba(0,0,0,.25);
    animation:fadeIn .5s ease;
}

@keyframes fadeIn{
    from{
        opacity:0;
        transform:translateY(20px);
    }
    to{
        opacity:1;
        transform:translateY(0);
    }
}

.login-box h2{
    text-align:center;
    color:#333;
    margin-bottom:10px;
    font-size:30px;
}

.subtitle{
    text-align:center;
    color:#777;
    margin-bottom:30px;
    font-size:14px;
}

.login-box input[type=text],
.login-box input[type=password]{
    width:100%;
    padding:14px;
    margin:10px 0;
    border-radius:10px;
    border:1px solid #ddd;
    font-size:15px;
    transition:.3s;
}

.login-box input:focus{
    outline:none;
    border-color:#4facfe;
    box-shadow:0 0 8px rgba(79,172,254,.4);
}

.g-recaptcha{
    display:flex;
    justify-content:center;
    margin:18px 0;
}

.login-box input[type=submit],
.login-box input[type=reset]{
    width:48%;
    padding:13px;
    border:none;
    border-radius:10px;
    cursor:pointer;
    font-size:15px;
    font-weight:bold;
    transition:.3s;
}

.login-box input[type=submit]{
    background:#4facfe;
    color:white;
}

.login-box input[type=submit]:hover{
    background:#008cff;
}

.login-box input[type=reset]{
    background:#ececec;
}

.login-box input[type=reset]:hover{
    background:#d8d8d8;
}

.button-group{
    display:flex;
    justify-content:space-between;
    margin-top:10px;
}

.divider{
    margin:30px 0;
    border:none;
    border-top:1px solid #ddd;
}

.google-btn{
    display:block;
    width:100%;
    text-align:center;
    background:#fff;
    color:#444;
    padding:13px;
    border:1px solid #ddd;
    border-radius:10px;
    text-decoration:none;
    font-weight:600;
    transition:.3s;
}

.google-btn:hover{
    background:#f5f5f5;
}

.create-link{
    display:block;
    margin-top:20px;
    text-align:center;
    text-decoration:none;
    color:#4facfe;
    font-weight:bold;
}

.create-link:hover{
    text-decoration:underline;
}

.error-msg{
    margin-top:20px;
    text-align:center;
    color:#e53935;
    font-weight:bold;
}
            
        </style>
    </head>
    <body>
        <div class="login-box">
            <h2>Login</h2>

            <form action="MainController" method="POST">
                <input type="text" name="uID" placeholder="Username" required/><br/>
                <input type="password" name="password" placeholder="Password" required/><br/>

             
 <div class="g-recaptcha"
      data-sitekey="YOUR_RECAPTCHA_SITE_KEY">
 </div>
            
                <input type="submit" name="action" value="Login"/>
                <input type="reset" value="Reset"/>
            </form>

            <hr class="divider"/>

            <a href="<%= su26.t4s1.utils.GoogleConfig.AUTH_URL%>" class="google-btn">
                Sign in with Google
            </a>

            <a href="createUser.jsp" class="create-link">Create new account</a>

            <%
                String error_msg = (String) request.getAttribute("ERROR");
                if (error_msg == null) {
                    error_msg = "";
                }
                if (!error_msg.isEmpty()) {
            %>
            <p class="error-msg"><%= error_msg%></p>
            <% }%>
        </div>
    </body>
</html>