<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="at" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                            <th>STT</th>
                            <th>Tài khoản</th>
                            <th>Họ và tên</th>
                            <th>Số điện thoại</th>
                            <th>Email</th>
                            <th>Ngày tạo</th>
                            <th>Lần đăng nhập cuối</th>
                            <th>Trạng thái</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${requestScope.users}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${user.username}</td>
                                <td>${user.name}</td>
                                <td>${user.mobile}</td>
                                <td>${user.email}</td>
                                <td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss"  value="${user.createdDate}"/></td>
                                <td>
                                    <c:if test="${user.lastLoginDate != null}">
                                        <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss"  value="${user.lastLoginDate}"/>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.status == 0}">
                                            <span>Hoạt động</span>
                                        </c:when>
                                        <c:when test="${user.status == 1}">
                                            <span>Khóa</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="/admin/user/index?action=edit&id=${user.id}"
                                       class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="Sửa người dùng">
                                        <i class="fas fa-pen"></i>
                                    </a>
                                    <a href="/admin/user/index?action=delete&id=${user.id}" onclick="confirm('Bạn có chắc chắn muốn xóa người dùng không?')"
                                       class="btn btn-sm btn-danger"  title="Xóa người dùng">
                                        <i class="fas fa-trash"></i>
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
