# User Management & Online Shopping Web App

A Java web app for a small drink shop. Admins manage user accounts; users log in, add drinks to a cart and check out. Built as an individual course project (Java Web, Summer 2026) at FPT University.

## Features

**Login and security**
- Login with username and password, protected by Google reCAPTCHA
- Login with a Google account (OAuth 2.0); a new account is created on the first Google login
- Role-based pages: Admin (`AD`) and User (`US`)
- All database queries use `PreparedStatement` to protect against SQL injection

**Admin**
- Search users by name
- Update a user's name and role, delete users
- Lock / unlock accounts (an admin cannot lock their own account)

**User**
- View the drink menu with paging (5 drinks per page)
- Add drinks to a cart, change quantity, remove items
- Check out: the app checks stock, saves the order and order details, reduces stock and clears the cart
- Receive an order confirmation email

## Tech stack

Java 8 · Servlet / JSP · JDBC · SQL Server · Apache Tomcat · NetBeans (Ant) · Gson · JavaMail

## Project structure

```
src/java/su26/t4s1/
├── controllers/   # Servlets; MainController sends each request to the right controller (MVC)
├── user/          # UserDTO, UserDAO, UserError
├── shopping/      # Product, Cart, Order, OrderDetail and their DAOs
└── utils/         # DBUtils, EmailUtils, RecaptchaUtils, GoogleConfig
web/               # JSP pages (login, admin, user, shopping, viewCart, ...)
database.sql       # Script to create the database and demo data
```

## How to run

1. **Database:** open `database.sql` in SQL Server Management Studio and run it. It creates the `UserManagement` database with demo data.
2. **Configuration:** the secret keys were removed from this repository. Fill in your own values:

   | File | What to fill in |
   |---|---|
   | `src/java/su26/t4s1/utils/DBUtils.java` | SQL Server user and password |
   | `src/java/su26/t4s1/utils/GoogleConfig.java` | Google OAuth Client ID and Client Secret ([Google Cloud Console](https://console.cloud.google.com/apis/credentials)) |
   | `src/java/su26/t4s1/utils/RecaptchaUtils.java` | reCAPTCHA v2 secret key ([reCAPTCHA admin](https://www.google.com/recaptcha/admin)) |
   | `web/login.jsp` | reCAPTCHA v2 site key |
   | `src/java/su26/t4s1/utils/EmailUtils.java` | Gmail address and a Gmail App Password |

3. **Run:** open the project in NetBeans, set Apache Tomcat as the server, then Run. Open `http://localhost:8080/UserManagement_T4S1_JSP/`.

**Demo accounts:** `admin` / `1` (Admin), `Hoadnt` / `1` (User).

## Known limitations

- Passwords are stored as plain text (course scope). Hashing (e.g. BCrypt) would be the next improvement.
- Configuration is written in Java files; environment variables would be safer.
