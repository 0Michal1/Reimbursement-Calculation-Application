package pl.michal.rca.controllers;

import pl.michal.rca.models.User;
import pl.michal.rca.services.interfaces.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    private UserService userService = new pl.michal.rca.services.database.UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(username, password);
        if(userService.verifyLogin(user)){
            User userFromDB = userService.read(user);
            HttpSession sess = request.getSession();
            sess.setAttribute("role", userFromDB.getRole());
            sess.setAttribute("userId", userFromDB.getId());
            sess.setAttribute("username", userFromDB.getUsername());
           if("ADMIN".equals(userFromDB.getRole())){
               response.sendRedirect("/admin/homepage");
           }else{
               response.sendRedirect("/user/homepage");
           }
        }
        else{response.sendRedirect("/login");}

    }
    private boolean validUser(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) return false;
        if (user.getPassword() == null || user.getPassword().isBlank()) return false;
        return true;
    }
}
