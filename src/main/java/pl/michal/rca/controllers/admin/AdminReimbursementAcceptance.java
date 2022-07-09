package pl.michal.rca.controllers.admin;

import pl.michal.rca.services.interfaces.ReimbursementService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminReimbursementAccept", value = "/admin/reimbursements/acceptance")
public class AdminReimbursementAcceptance extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reimbursementId = Integer.parseInt(request.getParameter("id"));
        String acceptance = request.getParameter("acceptance");
        ReimbursementService reimbursementService = new pl.michal.rca.services.database.ReimbursementService();
        reimbursementService.updateAcceptance(reimbursementId, acceptance);
        response.sendRedirect("/admin/reimbursements/list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
