<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>Error</h1>
        <p style="color:red;"><%= session.getAttribute("ERROR_MSG") != null ? session.getAttribute("ERROR_MSG") : "An error occurred." %></p>
        <% session.removeAttribute("ERROR_MSG"); %>
        <a href="view.jsp">Back to Cart</a>
        <a href="shopping.jsp">Continue Shopping</a>
    </body>
</html>
