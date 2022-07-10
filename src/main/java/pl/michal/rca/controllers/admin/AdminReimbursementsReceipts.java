package pl.michal.rca.controllers.admin;

import pl.michal.rca.models.Receipt;
import pl.michal.rca.services.interfaces.AdminVariableService;
import pl.michal.rca.services.interfaces.ReceiptService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminReimbursementsReceipts", value = "/admin/reimbursements/receipts")
public class AdminReimbursementsReceipts extends HttpServlet {
    private ReceiptService receiptService = new pl.michal.rca.services.database.ReceiptService();
    private AdminVariableService adminVariableService = new pl.michal.rca.services.database.AdminVariableService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reimbursementsID = Integer.parseInt(request.getParameter("id"));
        List<Receipt> receipts = receiptService.findAllByReimbursementsID(reimbursementsID);
        for (Receipt receipt:receipts) {
            String receiptName = adminVariableService.readNameById(receipt.getTypeId());
            receipt.setName(receiptName);
        }
        request.setAttribute("receipts", receipts);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/reimbursements-receipts.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
