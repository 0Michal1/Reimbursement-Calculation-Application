package pl.michal.rca.controllers.user;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.models.User;
import pl.michal.rca.services.interfaces.AdminVariableService;
import pl.michal.rca.services.interfaces.ReimbursementService;
import pl.michal.rca.services.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "UserAddReimbursement", value = "/user/reimbursements/add")
public class UserReimbursementAdd extends HttpServlet {
   AdminVariableService adminVariableService = new pl.michal.rca.services.database.AdminVariableService();
   ReimbursementService reimbursementService = new pl.michal.rca.services.database.ReimbursementService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int)request.getSession().getAttribute("userId");

        try {

            UserService userService = new pl.michal.rca.services.database.UserService();
            User user = userService.readById(userId);
            request.setAttribute("username", user.getUsername());
            request.setAttribute("userId", user.getId());

            List<AdminVariable> receiptTypes = adminVariableService.findAllByTypeActive("receiptType");
            request.setAttribute("receiptTypes", receiptTypes);
            getServletContext().getRequestDispatcher("/WEB-INF/views/user/add-receipts.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
//            logger.info("Invalid user id");
            response.sendRedirect("/user/homepage");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int reimbursementId = Integer.parseInt(request.getParameter("id"));
        double receiptsValue = Double.parseDouble(request.getParameter("receiptsValue"));
        int days = Integer.parseInt(request.getParameter("days"));
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        AdminVariable allowance = adminVariableService.readByName("allowance");
        double allowanceValue = allowance.getValue();
        AdminVariable forKm = adminVariableService.readByName("for km");
        double forKMValue = forKm.getValue();
        AdminVariable totalLimit = adminVariableService.readByName("limit total");
        double totalLimitValue = totalLimit.getValue();
        AdminVariable limitKm = adminVariableService.readByName("limit km");
        double limitKMValue = limitKm.getValue();
        double total = receiptsValue + days*allowanceValue +mileage*forKMValue;
        Reimbursement reimbursement =new Reimbursement();
        reimbursement.setReceiptsValue(receiptsValue);
        reimbursement.setUserId(userId);
        reimbursement.setUsername(username);
        reimbursement.setId(reimbursementId);
        if(mileage>limitKMValue||total>totalLimitValue){
            List<String> errorMsg = new ArrayList<>();
            if(mileage>limitKMValue){
                errorMsg.add("Value of mileage exceeds the maximum limit of "+limitKMValue+".");
            }
            if(total>totalLimitValue){
                errorMsg.add("Value of total Reimbursement exceeds the maximum limit of "+totalLimitValue+".");
            }
            request.setAttribute("errorMsgs", errorMsg);
            request.setAttribute("reimbursement", reimbursement);
            getServletContext().getRequestDispatcher("/WEB-INF/views/user/add-reimbursement.jsp").forward(request, response);
        }

        reimbursement.setMileage(mileage);
        reimbursement.setDays(days);
        reimbursement.setTotal(total);
        reimbursementService.update(reimbursement);
        response.sendRedirect("/user/reimbursements");
    }
}
