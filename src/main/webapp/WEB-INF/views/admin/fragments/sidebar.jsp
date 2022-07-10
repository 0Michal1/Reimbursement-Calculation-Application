<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/admin/homepage">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Reimbursement</div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="/admin/homepage">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Homepage</span></a>
    </li>
<%--    <li class="nav-item active">--%>
<%--        <a class="nav-link" href="/admin/user/list">--%>
<%--            <i class="fas fa-fw fa-tachometer-alt"></i>--%>
<%--            <span>User List</span></a>--%>
<%--    </li>--%>
    <li class="nav-item active">
        <a class="nav-link" href="/admin/reimbursements/list">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Reimbursements List</span></a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/admin/rates">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Change rates and limits</span></a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/admin/receipts">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Edit receipts list and their limits</span></a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/logout">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Logout</span></a>
    </li>
</ul>
