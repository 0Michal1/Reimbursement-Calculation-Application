package pl.michal.rca.controllers.admin;

import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.services.interfaces.ReimbursementService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminReimbursementList", value = "/admin/reimbursements/list")
public class AdminReimbursementList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReimbursementService reimbursementService = new pl.michal.rca.services.database.ReimbursementService();
        List<Reimbursement> reimbursements = reimbursementService.findAll();
        request.setAttribute("reimbursements", reimbursements);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/reimbursements.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
