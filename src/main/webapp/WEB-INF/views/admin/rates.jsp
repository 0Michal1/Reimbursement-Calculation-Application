<%--
  Created by IntelliJ IDEA.
  User: michal
  Date: 08.07.2022
  Time: 17:31
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

    <title>Rates and Limits list</title>

    <!-- Custom fonts for this template-->
    <link href="/static/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

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
                    <h1 class="h3 mb-0 text-gray-800">Rates and Limits</h1>

                </div>

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Rates and Limits List</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Type</th>
                                    <th>Name</th>
                                    <th>Value</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${rates}" var="rate">
                                <form action="/admin/rates" method="post">
                                    <input type="hidden" id="variableId" name="variableId" value="${rate.id}">
                                    <tr>
                                        <td>${rate.type}</td>
                                        <td>${rate.name}</td>
                                        <td><input
                                                class="form-control" id="value" name="value" type="number"
                                                min="0" pattern="0.00" step=".01"
                                                placeholder=${rate.value}></td>
                                        <td>
                                            <button class="btn btn-primary" type="submit">Change Value</button>
                                        </td>
                                    </tr>
                                </form>
                                </c:forEach>
                                <c:forEach items="${limits}" var="limit">
                                <form action="/admin/rates" method="post">
                                    <input type="hidden" id="variableId1" name="variableId" value="${limit.id}">
                                    <tr>
                                        <td>${limit.type}</td>
                                        <td>${limit.name}</td>
                                        <td><input
                                                class="form-control" id="value1" name="value" type="number"
                                                min="0" pattern="0.00" step=".01"
                                                placeholder=${limit.value}></td>
                                        <td>
                                            <button class="btn btn-primary" type="submit">Change Value</button>
                                        </td>
                                    </tr>
                                </form>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
