<%@page import="su26.t4s1.shopping.CartItemDTO"%>
<%@page import="java.util.List"%>
<%@page import="su26.t4s1.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shopping Cart</title>

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

        .container{
            max-width:1000px;
            margin:auto;
            background:white;
            padding:35px;
            border-radius:18px;
            box-shadow:0 15px 35px rgba(0,0,0,.2);
        }

        h1{
            text-align:center;
            color:#333;
            margin-bottom:25px;
        }

        table{
            width:100%;
            border-collapse:collapse;
            margin-top:20px;
        }

        th{
            background:#4facfe;
            color:white;
            padding:14px;
        }

        td{
            padding:12px;
            text-align:center;
            border:1px solid #ddd;
        }

        tbody tr:hover{
            background:#f5fbff;
        }

        input[type=number]{
            width:70px;
            padding:8px;
            text-align:center;
            border-radius:6px;
            border:1px solid #ccc;
        }

        .btn{
            border:none;
            padding:10px 18px;
            border-radius:8px;
            cursor:pointer;
            color:white;
            font-weight:bold;
            transition:.3s;
        }

        .edit{
            background:#17a2b8;
        }

        .edit:hover{
            background:#117a8b;
        }

        .remove{
            background:#dc3545;
        }

        .remove:hover{
            background:#b52b38;
        }

        .checkout{
            background:#28a745;
            margin-top:25px;
        }

        .checkout:hover{
            background:#218838;
        }

        .back{
            display:inline-block;
            margin-top:30px;
            text-decoration:none;
            background:#4facfe;
            color:white;
            padding:12px 20px;
            border-radius:8px;
            transition:.3s;
        }

        .back:hover{
            background:#1889ff;
        }

        .success{
            text-align:center;
            color:green;
            font-weight:bold;
            margin-bottom:20px;
        }

        .error{
            text-align:center;
            color:red;
            font-weight:bold;
            margin-bottom:20px;
            white-space:pre-line;
        }

        .total{
            text-align:right;
            font-size:24px;
            margin-top:25px;
            color:#333;
            font-weight:bold;
        }

    </style>

</head>

<body>

<div class="container">

<h1>🛒 Your Shopping Cart</h1>

<%
    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
    if (loginUser == null || !"US".equals(loginUser.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<CartItemDTO> cart =
            (List<CartItemDTO>) request.getAttribute("CART");

    String message =
            (String) request.getAttribute("MESSAGE");

    String errors =
            (String) request.getAttribute("ERROR");
%>

<% if(message != null && !message.isEmpty()){ %>

<div class="success">
    <%=message%>
</div>

<% } %>

<% if(errors != null && !errors.isEmpty()){ %>

<div class="error">
    <%=errors%>
</div>

<% } %>

<%

double total = 0;

if(cart == null || cart.isEmpty()){

%>

<h2 style="text-align:center;color:#666;">
    Your shopping cart is empty.
</h2>

<%

}else{

%>

<table>

<thead>

<tr>

<th>No</th>
<th>Product ID</th>
<th>Beverage</th>
<th>Price (VNĐ)</th>
<th>Quantity</th>
<th>Total (VNĐ)</th>
<th>Update</th>
<th>Remove</th>

</tr>

</thead>

<tbody>

<%

int count = 1;

for(CartItemDTO item : cart){

total += item.getPrice()*item.getQuantity();

%>

<form action="MainController" method="POST">

<tr>

<td><%=count++%></td>

<td>

<%=item.getpID()%>

<input
type="hidden"
name="id"
value="<%=item.getpID()%>"/>

</td>

<td><%=item.getPName()%></td>

<td>

<%=String.format("%,.0f",item.getPrice())%> ₫

</td>

<td>

<input
type="number"
name="quantity"
value="<%=item.getQuantity()%>"
min="1"/>

</td>

<td>

<%=String.format("%,.0f",
item.getPrice()*item.getQuantity())%> ₫

</td>

<td>

<button
class="btn edit"
type="submit"
name="action"
value="Edit">

Update

</button>

</td>

<td>

<button
class="btn remove"
type="submit"
name="action"
value="Remove">

Remove

</button>

</td>

</tr>

</form>

<%

}

%>

</tbody>

</table>
<%

}   // end else (cart not empty)

%>

<% if(cart != null && !cart.isEmpty()){ %>

<div class="total">

    Total:
    <%=String.format("%,.0f", total)%> ₫

</div>

<form action="MainController" method="POST">

    <input
        type="hidden"
        name="total"
        value="<%=String.format("%.0f", total)%>"/>

    <button
        class="btn checkout"
        type="submit"
        name="action"
        value="Checkout">

        💳 Checkout

    </button>

</form>

<% } %>

<a
    class="back"
    href="MainController?action=ViewProduct">

    ← Continue Shopping

</a>

</div>

</body>

</html>