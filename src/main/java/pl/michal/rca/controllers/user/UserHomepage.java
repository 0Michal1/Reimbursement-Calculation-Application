package pl.michal.rca.controllers.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserHomepage", value = "/user/homepage")
public class UserHomepage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userIdAttribute = (int) request.getSession().getAttribute("userId");

        request.setAttribute("id", userIdAttribute);
        getServletContext().getRequestDispatcher("/WEB-INF/views/user/homepage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
