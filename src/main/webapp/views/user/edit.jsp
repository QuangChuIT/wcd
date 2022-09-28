<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="at" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<at:templateAdmin>
    <jsp:attribute name="content">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Cập nhật thông tin người dùng</h6>
            </div>
            <div class="card-body">
                <c:if test="${requestScope.errorMessage != null}">
                    <p class="text-danger">${requestScope.errorMessage}</p>
                </c:if>
                <form action="/admin/user/index?action=update" method="post" onsubmit="return validate()">
                    <input type="hidden" value="${requestScope.user.id}" name="id"/>
                    <div class="form-group">
                        <label for="username">Tài khoản <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" readonly value="${requestScope.user.username}"
                               name="username"
                               id="username"
                               placeholder="Nhập tài khoản">
                        <span class="text-danger mt-1" id="errorUsername"></span>
                    </div>
                    <div class="form-group">
                        <label for="name">Họ và tên</label>
                        <input type="text" class="form-control" required value="${requestScope.user.name}"
                               id="name"
                               name="name"
                               placeholder="Nhập họ và tên">
                    </div>
                    <div class="form-group">
                        <label for="mobile">Số điện thoại</label>
                        <input type="text" class="form-control" value="${requestScope.user.mobile}"
                               name="mobile"
                               id="mobile"
                               placeholder="Nhập số điện thoại">
                        <span class="text-danger mt-1" id="errorMobile"></span>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" value="${requestScope.user.email}"
                               name="email"
                               id="email"
                               placeholder="Nhập số email">
                        <span class="text-danger mt-1" id="errorEmail"></span>
                    </div>
                    <div class="form-group">
                        <c:choose>
                            <c:when test="${requestScope.user.status == 0}">
                                <input type="checkbox" name="status" value="${requestScope.user.status}" id="status" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="status" value="${requestScope.user.status}" id="status"/>
                            </c:otherwise>
                        </c:choose>

                        <label class="form-check-label" for="status">
                            Hoạt động
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                    <a href="/admin/user/index" class="btn btn-warning">Danh sách</a>
                </form>
            </div>
        </div>
    </jsp:attribute>
</at:templateAdmin>