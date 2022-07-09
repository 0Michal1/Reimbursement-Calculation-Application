package pl.michal.rca.controllers.admin;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.services.interfaces.AdminVariableService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminReceiptsTypeAdd", value = "/admin/receipts/add")
public class AdminReceiptsTypeAdd extends HttpServlet {
    private AdminVariableService adminVariableService = new pl.michal.rca.services.database.AdminVariableService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/add-receipts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        Double value = Double.valueOf(request.getParameter("value"));
        AdminVariable receiptType = new AdminVariable();
        receiptType.setType("receiptType");
        receiptType.setState("active");
        receiptType.setName(name);
        receiptType.setValue(value);
        receiptType = adminVariableService.create(receiptType);
        List<AdminVariable> receipts = adminVariableService.findAllByType("receiptType");
        request.setAttribute("receipts", receipts);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/receipts.jsp").forward(request, response);

    }
}
