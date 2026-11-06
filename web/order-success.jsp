<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Successful</title>
    </head>
    <body>
        <h1>Checkout Successful!</h1>
        <p>Order ID: <strong><%= session.getAttribute("OID") %></strong></p>
        <p>Total: <strong><%= session.getAttribute("TOTAL") %>$</strong></p>
        <% session.removeAttribute("OID"); session.removeAttribute("TOTAL"); %>
        <p>Thank you for your purchase!</p>
        <a href="shopping.jsp">Continue Shopping</a>
    </body>
</html>
