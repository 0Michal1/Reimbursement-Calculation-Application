package pl.michal.rca.controllers.user;

import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.services.interfaces.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserReimbursements", value = "/user/reimbursements")
public class UserReimbursements extends HttpServlet {
    ReimbursementService reimbursementService = new pl.michal.rca.services.database.ReimbursementService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int)request.getSession().getAttribute("userId");
        List<Reimbursement> reimbursements = reimbursementService.findAllForUser(userId);
        request.setAttribute("reimbursements", reimbursements);
        getServletContext().getRequestDispatcher("/WEB-INF/views/user/reimbursements.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
