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

@WebServlet(name = "AdminRates", value = "/admin/rates")
public class AdminRates extends HttpServlet {
    private AdminVariableService adminVariableService = new pl.michal.rca.services.database.AdminVariableService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AdminVariable> rates = adminVariableService.findAllByType("rate");
        List<AdminVariable> limits = adminVariableService.findAllByType("limit");
        request.setAttribute("rates", rates);
        request.setAttribute("limits", limits);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/rates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int variableId  = Integer.parseInt(request.getParameter("variableId"));
        double value = Double.parseDouble(request.getParameter("value"));
        adminVariableService.updateValue(variableId, value);
        List<AdminVariable> rates = adminVariableService.findAllByType("rate");
        List<AdminVariable> limits = adminVariableService.findAllByType("limit");
        request.setAttribute("rates", rates);
        request.setAttribute("limits", limits);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin/rates.jsp").forward(request, response);
    }
}
