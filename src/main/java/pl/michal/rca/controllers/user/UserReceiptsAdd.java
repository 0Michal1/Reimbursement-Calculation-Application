package pl.michal.rca.controllers.user;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.models.Receipt;
import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.services.interfaces.AdminVariableService;
import pl.michal.rca.services.interfaces.ReceiptService;
import pl.michal.rca.services.interfaces.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserAddReceipts", value = "/user/receipts/add")
public class UserReceiptsAdd extends HttpServlet {
    private AdminVariableService adminVariableService = new pl.michal.rca.services.database.AdminVariableService();
    private ReceiptService receiptService =new pl.michal.rca.services.database.ReceiptService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReimbursementService reimbursementService = new pl.michal.rca.services.database.ReimbursementService();
        String username = request.getParameter("username");
        int userId = Integer.parseInt(request.getParameter("userId"));

        double value1 = Double.parseDouble(request.getParameter("value1"));
        double value2 = Double.parseDouble(request.getParameter("value2"));
        double value3 = Double.parseDouble(request.getParameter("value3"));
        double value4 = Double.parseDouble(request.getParameter("value4"));
        double value5 = Double.parseDouble(request.getParameter("value5"));
        List<Double> values = List.of(value1, value2, value3, value4, value5);
        int typeId1 = Integer.parseInt(request.getParameter("receiptType1"));
        int typeId2 = Integer.parseInt(request.getParameter("receiptType2"));
        int typeId3 = Integer.parseInt(request.getParameter("receiptType3"));
        int typeId4 = Integer.parseInt(request.getParameter("receiptType4"));
        int typeId5 = Integer.parseInt(request.getParameter("receiptType5"));
        List<Integer> typeId = List.of(typeId1, typeId2,typeId3,typeId4,typeId5);
        List<String> errorMsg = checkMaxValue(values, typeId);
        Double receiptsValue =0.0;

        if(errorMsg.size() ==0) {
            Reimbursement reimbursement = reimbursementService.createStartingReimbursement(username, userId, "In progress");
            for (int i = 0; i < 5; i++) {
                AdminVariable adminVariable = adminVariableService.read(typeId.get(i));
                Receipt receipt = new Receipt();
                receipt.setTypeId(typeId.get(i));
                receipt.setReimbursementId(reimbursement.getId());
                receipt.setValue(values.get(i));
                receiptsValue = receiptsValue+values.get(i);
                receiptService.create(receipt);
            }
            reimbursement.setReceiptsValue(receiptsValue);
            request.setAttribute("reimbursement", reimbursement);
            getServletContext().getRequestDispatcher("/WEB-INF/views/user/add-reimbursement.jsp").forward(request, response);
        }else {
            request.setAttribute("errorMsgs", errorMsg);
            List<AdminVariable> receiptTypes = adminVariableService.findAllByTypeActive("receiptType");
            request.setAttribute("receiptTypes", receiptTypes);
            getServletContext().getRequestDispatcher("/WEB-INF/views/user/add-receipts.jsp").forward(request, response);
        }



    }

    private List<String> checkMaxValue(List<Double> values, List<Integer> typeId) {
        List<String> errorMsg = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AdminVariable adminVariable = adminVariableService.read(typeId.get(i));
            if(values.get(i)>adminVariable.getValue()){
                String message = "Value of receipt "+adminVariable.getName()+" exceeds the maximum limit.";
            errorMsg.add(message);
            }
        }
        return errorMsg;
    }

}
