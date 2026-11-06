<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drink Menu</title>

        <style>

            *{
                margin:0;
                padding:0;
                box-sizing:border-box;
                font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;
            }

            body{
                min-height:100vh;
                background:linear-gradient(135deg,#4facfe,#00f2fe);
                padding:40px;
            }

            .container{
                max-width:900px;
                margin:auto;
                background:white;
                padding:35px;
                border-radius:18px;
                box-shadow:0 15px 35px rgba(0,0,0,.18);
            }

            h1{
                text-align:center;
                color:#333;
                margin-bottom:10px;
            }

            .subtitle{
                text-align:center;
                color:#666;
                margin-bottom:30px;
            }

            table{
                width:100%;
                border-collapse:collapse;
                margin-bottom:25px;
            }

            thead{
                background:#4facfe;
                color:white;
            }

            th,td{
                padding:14px;
                text-align:center;
                border:1px solid #ddd;
            }

            tbody tr:hover{
                background:#f5fbff;
            }

            .action-area{
                display:flex;
                justify-content:space-between;
                align-items:center;
                flex-wrap:wrap;
                gap:15px;
            }

            select{
                padding:10px;
                border-radius:8px;
                border:1px solid #ccc;
            }

            .btn{
                background:#4facfe;
                color:white;
                border:none;
                padding:12px 22px;
                border-radius:8px;
                cursor:pointer;
                font-size:15px;
                font-weight:bold;
                transition:.3s;
            }

            .btn:hover{
                background:#1889ff;
            }

            .cart-btn{
                display:inline-block;
                margin-top:25px;
                background:#28a745;
                color:white;
                text-decoration:none;
                padding:12px 22px;
                border-radius:8px;
                transition:.3s;
            }

            .cart-btn:hover{
                background:#1e7e34;
            }

            .pagination{
                margin-top:30px;
                text-align:center;
                font-weight:bold;
            }

            .pagination a{
                color:#1877f2;
                text-decoration:none;
                margin:0 15px;
            }

            .pagination a:hover{
                text-decoration:underline;
            }

            .message{
                margin-top:25px;
                text-align:center;
                color:green;
                font-weight:bold;
                font-size:16px;
            }

        </style>

    </head>

    <body>

        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <div class="container">

            <h1>🥤 Drink Menu</h1>

            <p class="subtitle">
                Choose your favorite beverage and add it to your shopping cart.
            </p>

            <form action="MainController" method="POST">

                <table>

                    <thead>

                        <tr>
                            <th>Product ID</th>
                            <th>Beverage</th>
                            <th>Price (VNĐ)</th>
                            <th>Available</th>
                            <th>Select</th>
                        </tr>

                    </thead>

                    <tbody>

                        <c:if test="${empty requestScope.LIST_PRODUCT}">
                            <tr>
                                <td colspan="5">
                                    No products available.
                                </td>
                            </tr>
                        </c:if>

                        <c:forEach var="p" items="${requestScope.LIST_PRODUCT}">

                            <tr>

                                <td>${p.id}</td>

                                <td>${p.name}</td>

                                <td>
                                    <fmt:formatNumber value="${p.price}" pattern="#,###"/> ₫
                                </td>

                                <td>${p.quantity}</td>

                                <td>

                                    <input
                                        type="radio"
                                        name="product"
                                        value="${p.id}-${p.name}"
                                        required/>

                                </td>

                            </tr>

                        </c:forEach>

                    </tbody>

                </table>

                <div class="action-area">

                    <div>

                        <label><b>Quantity:</b></label>

                        <select name="quantity" required>

                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="10">10</option>

                        </select>

                    </div>

                    <input
                        class="btn"
                        type="submit"
                        name="action"
                        value="Add"/>

                </div>

            </form>

            <a class="cart-btn"
               href="MainController?action=View">

                🛒 View Shopping Cart

            </a>

            <div class="pagination">

                <c:if test="${requestScope.CURRENT_PAGE > 1}">

                    <a href="MainController?action=ViewProduct&page=${requestScope.CURRENT_PAGE-1}">

                        ← Previous

                    </a>

                </c:if>

                Page

                ${empty requestScope.CURRENT_PAGE ? 1 : requestScope.CURRENT_PAGE}

                of

                ${empty requestScope.TOTAL_PAGES ? 1 : requestScope.TOTAL_PAGES}

                <c:if test="${requestScope.CURRENT_PAGE < requestScope.TOTAL_PAGES}">

                    <a href="MainController?action=ViewProduct&page=${requestScope.CURRENT_PAGE+1}">

                        Next →

                    </a>

                </c:if>

            </div>

            <div class="message">

                ${requestScope.MESSAGE}

            </div>

        </div>

    </body>
</html>