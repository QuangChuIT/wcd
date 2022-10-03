<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="at" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<at:templateAdmin>
    <jsp:attribute name="content">
        <h1 class="h3 mb-2 text-gray-800">Quản lý sản phẩm</h1>
        <p class="mb-4">
            Quản lý danh mục sản phẩm, thông tin sản phẩm
        </p>

        <!-- DataTales Example -->
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary d-inline-block align-middle">Danh sách sản phẩm</h6>
                <button class="btn btn-primary ml-3" data-toggle="modal" data-target="#createUserModal"><i
                        class="fas fa-plus"></i> Tạo mới
                </button>
                <button class="btn btn-danger ml-3" id="btnDeleteUsers"><i class="fas fa-trash"></i> Xóa</button>
            </div>

            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="selectAllUsers"/></th>
                            <th>STT</th>
                            <th>Ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${requestScope.products}" varStatus="loop">
                                <tr>
                                    <td class="text-center"><input type="checkbox" name="userIds" value="${product.id}"></td>
                                    <td class="text-center">${loop.index + 1}</td>
                                    <td>
                                        <div class="img-pro">
                                            <img src="<c:url value="/assets/Photos/Product/${product.photo}"/>" width="100" height="100">
                                        </div>
                                    </td>
                                    <td>${product.title}</td>
                                    <td class="text-center">${product.quantity}</td>
                                    <td><fmt:formatNumber  pattern="#,###.##;-#,###.##" value="${product.price}" type="currency" currencySymbol="VND"/></td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${product.status == false}">
                                                <span>Còn bán</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>Ngừng bán</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><fmt:formatDate value="${product.createdDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                    <td><a href="<c:url value="/admin/carts?action=addToCart&productId=${product.id}"/>" class="btn btn-primary btn-sm">Đặt hàng</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:attribute>
</at:templateAdmin>
