package pl.michal.rca.controllers.admin;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.services.database.AdminVariableService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminReceiptsState", value = "/admin/receipts/state")
public class AdminReceiptsState extends HttpServlet {
   private AdminVariableService adminVariableService = new AdminVariableService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         int id = Integer.parseInt(request.getParameter("id"));
         String state = request.getParameter("state");
        adminVariableService.updateState(id, state);
        List<AdminVariable> receipts = adminVariableService.findAllByType("receiptType");
        request.setAttribute("receipts", receipts);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/receipts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
