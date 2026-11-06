<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrator Page</title>
        <style>

            *{
                margin:0;
                padding:0;
                box-sizing:border-box;
                font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;
            }

            body{

                background:linear-gradient(135deg,#4facfe,#00f2fe);
                min-height:100vh;
                padding:40px;
            }

            .admin-box{

                max-width:1200px;
                margin:auto;

                background:white;

                border-radius:20px;

                padding:35px;

                box-shadow:0 15px 40px rgba(0,0,0,.2);

            }

            .header{

                display:flex;

                justify-content:space-between;

                align-items:center;

                margin-bottom:30px;

            }

            .header h1{

                font-size:30px;

                color:#222;

            }

            .logout-link{

                background:#ff5252;

                color:white;

                text-decoration:none;

                padding:10px 20px;

                border-radius:8px;

                transition:.3s;

            }

            .logout-link:hover{

                background:#e53935;

            }

            .search-form{

                display:flex;

                gap:10px;

                align-items:center;

                margin-bottom:25px;

            }

            .search-form input[type=text]{

                width:350px;

                padding:12px;

                border-radius:10px;

                border:1px solid #ddd;

                outline:none;

            }

            .search-form input[type=submit]{

                padding:12px 20px;

                border:none;

                border-radius:10px;

                background:#4facfe;

                color:white;

                cursor:pointer;

            }

            .search-form input[type=submit]:hover{

                background:#1d8cff;

            }

            table{

                width:100%;

                border-collapse:collapse;

                overflow:hidden;

                border-radius:10px;

            }

            thead{

                background:#4facfe;

                color:white;

            }

            th{

                padding:14px;

                text-align:center;

            }

            td{

                padding:12px;

                border-bottom:1px solid #eee;

                text-align:center;

            }

            tbody tr:hover{

                background:#f5fbff;

            }

            table input[type=text]{

                width:100%;

                padding:8px;

                border:1px solid #ddd;

                border-radius:6px;

            }

            input[type=submit]{

                padding:8px 14px;

                border:none;

                border-radius:8px;

                background:#4facfe;

                color:white;

                cursor:pointer;

            }

            input[type=submit]:hover{

                background:#1d8cff;

            }

            .delete-link{

                color:#ff3b30;

                text-decoration:none;

                font-weight:bold;

                margin-right:10px;

            }

            .delete-link:hover{

                text-decoration:underline;

            }

            .error-msg{

                color:red;

                font-weight:bold;

                margin:15px 0;

            }

        </style>
    </head>

    <body>
        <div class="admin-box">
            <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'AD'}">
                <c:redirect url="login.jsp"/>
            </c:if>

            <div class="header">

                <h1>
                    Welcome, ${sessionScope.LOGIN_USER.name}
                </h1>

                <a href="MainController?action=Logout"
                   class="logout-link">

                    Logout

                </a>

            </div>

            <form action="SearchController"
                  class="search-form">
                Search:
                <input type="text" name="search" value="${param.search}" required=""/>
                <input type="submit" name="action" value="Search"/>
            </form>

            <c:if test="${empty requestScope.LIST_USER}">
                <p class="error-msg">${requestScope.ERROR}</p>
            </c:if>

            <c:if test="${not empty requestScope.LIST_USER}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>uID</th>
                            <th>Name</th>
                            <th>RoleID</th>
                            <th>Password</th>
                            <th>Status</th>
                            <th>Update</th>
                            <th>Update Status</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${requestScope.LIST_USER}" varStatus="counter">
                        <form action="MainController" method="POST">
                            <tr>
                                <td>${counter.count}</td>

                                <td>
                                    <input type="text" name="uID"
                                           value="${user.uID}" readonly/>
                                </td>

                                <td>
                                    <input type="text" name="name"
                                           value="${user.name}" required/>
                                </td>

                                <td>
                                    <input type="text" name="roleID"
                                           value="${user.roleID}" required/>
                                </td>

                                <td>****</td>

                                <td>
                                    <input type="text"
                                           name="status"
                                           value="${user.status}" readonly/>
                                </td>

                                <td>
                                    <input type="submit"
                                           name="action"
                                           value="Update"/>
                                </td>

                                <td>
                                    <input type="submit"
                                           name="action"
                                           value="Update Status"/>
                                </td>

                                <td>
                                    <c:url var="deleteLink" value="MainController">
                                        <c:param name="action" value="Delete"/>
                                        <c:param name="uID" value="${user.uID}"/>
                                        <c:param name="search" value="${param.search}"/>
                                    </c:url>

                                    <a href="${deleteLink}" class="delete-link">Delete</a>
                                </td>

                            <input type="hidden"
                                   name="search"
                                   value="${param.search}"/>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>