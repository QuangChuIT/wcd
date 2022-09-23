<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="at" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<at:templateAdmin>
    <jsp:attribute name="content">
        <h1 class="h3 mb-2 text-gray-800">Quản lý tài khoản</h1>
        <p class="mb-4">
            Quản lý tài khoản, mật khẩu người dùng
        </p>

        <!-- DataTales Example -->
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Danh sách tài khoản</h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tài khoản</th>
                            <th>Số điện thoại</th>
                            <th>Email</th>
                            <th>Địa chỉ</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.username}</td>
                                <td>${user.mobile}</td>
                                <td>${user.email}</td>
                                <td>${user.address}</td>
                                <td>
                                    <a href="/admin/user/index?action=edit&id=${user.id}"
                                       class="btn btn-sm btn-info">
                                        Sửa
                                    </a>
                                    <a href="/admin/user/index?action=delete&id=${user.id}"
                                       class="btn btn-sm btn-danger">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:attribute>
</at:templateAdmin>
