<%--
  Created by IntelliJ IDEA.
  User: michal
  Date: 07.07.2022
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Add Reimbursement</title>

    <!-- Custom fonts for this template-->
    <link href="/static/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/static/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <jsp:include page="fragments/sidebar.jsp"/>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Topbar -->
                <jsp:include page="fragments/navbar.jsp"/>

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Reimbursement</h1>
                    <a href="/user/homepage"
                       class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                            class="fas fa-user fa-sm text-white-50"></i>Homepage</a>
                </div>

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Add Reimbursement</h6>
                    </div>
                    <c:if test="${not empty errorMsgs}">
                        <div class="alert alert-danger" role="alert">
                            <c:forEach items="${errorMsgs}" var="errorMsg" >
                                <c:out value="${errorMsg}"/> </br>
                            </c:forEach>
                        </div>
                    </c:if>
                    <div class="card-body">
                        <form action="/user/reimbursements/add" method="post">
                            <input type="hidden" id="userId" name="userId" value="${reimbursement.userId}">
                            <input type="hidden" id="username" name="username" value="${reimbursement.username}">
                            <input type="hidden" id="id" name="id" value="${reimbursement.id}">
                            <input type="hidden" id="receiptsValue" name="receiptsValue" value="${reimbursement.receiptsValue}">
                            <div class="mb-3"><label for="days">Number of Days</label><input
                                    class="form-control" id="days" name="days" type="number"
                                    placeholder="Number of Days"></div>
                            <div class="mb-3"><label for="mileage">Number of Kilometers</label><input
                                    class="form-control" id="mileage" name="mileage" type="number"
                                    placeholder="Number of Kilometers"></div>
                            <div class="mb-3">
                                <button class="btn btn-primary" type="submit">Add</button>
                            </div>
                            <input type="hidden" name="id" value="${user.id}"/>
                        </form>


                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <jsp:include page="fragments/footer.jsp"/>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Bootstrap core JavaScript-->
<script src="/static/vendor/jquery/jquery.min.js"></script>
<script src="/static/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="/static/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="/static/js/sb-admin-2.min.js"></script>

</body>

</html>
