<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
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
                <h6 class="m-0 font-weight-bold text-primary d-inline-block align-middle">Danh sách tài khoản</h6>
                <button class="btn btn-primary ml-3" data-toggle="modal" data-target="#createUserModal"><i
                        class="fas fa-plus"></i> Tạo mới
                </button>
                <button class="btn btn-danger ml-3"><i class="fas fa-trash"></i> Xóa</button>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="selectAllUsers"/></th>
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
                        <tbody id="usersBody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:attribute>
</at:templateAdmin>
<div class="modal fade" id="createUserModal" tabindex="-1" aria-labelledby="createUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Tạo mới người dùng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="username">Tài khoản <span class="text-danger">*</span></label>
                    <input type="text" class="form-control"
                           name="username"
                           id="username"
                           placeholder="Nhập tài khoản">
                    <span class="text-danger mt-1" id="errorUsername"></span>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu <span class="text-danger">*</span></label>
                    <input type="password" class="form-control"
                           name="password"
                           id="password"
                           placeholder="Nhập tài khoản">
                    <span class="text-danger mt-1" id="errorPassword"></span>
                </div>
                <div class="form-group">
                    <label for="name">Họ và tên <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" required
                           id="name"
                           name="name"
                           placeholder="Nhập họ và tên">
                    <span class="text-danger mt-1" id="errorName"></span>
                </div>
                <div class="form-group">
                    <label for="mobile">Số điện thoại <span class="text-danger">*</span></label>
                    <input type="text" class="form-control"
                           name="mobile"
                           id="mobile"
                           placeholder="Nhập số điện thoại">
                    <span class="text-danger mt-1" id="errorMobile"></span>
                </div>
                <div class="form-group">
                    <label for="email">Email <span class="text-danger">*</span></label>
                    <input type="email" class="form-control"
                           name="email"
                           id="email"
                           placeholder="Nhập số email">
                    <span class="text-danger mt-1" id="errorEmail"></span>
                </div>
                <div class="form-group">
                    <label for="email">Ảnh đại diện</label>
                    <input type="file" id="userPhoto">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary cancel-user-form" data-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-primary" id="btnCreateUser">Lưu</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="editUserModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Cập nhật người dùng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body edit-user" id="editUser">
            </div>
        </div>
    </div>
</div>
<script id="editUserTmp" type="text/x-jQuery-tmpl">
     <div class="form-group">
        <label for="editUsername">Tài khoản <span class="text-danger">*</span></label>
        <input type="text" class="form-control"
               name="editUsername"
               id="editUsername"
               readonly
               value="${username}"
               placeholder="Tài khoản">
        <span class="text-danger mt-1" id="errorUsername"></span>
    </div>
    <div class="form-group">
        <label for="editName">Họ và tên <span class="text-danger">*</span></label>
        <input type="text" class="form-control" required
               id="editName"
               name="editName"
               value="${name}"
               placeholder="Họ và tên">
        <span class="text-danger mt-1" id="errorName"></span>
    </div>
    <div class="form-group">
        <label for="editMobile">Số điện thoại <span class="text-danger">*</span></label>
        <input type="text" class="form-control"
               name="editMobile"
               id="editMobile"
               value="${mobile}"
               placeholder="Số điện thoại">
        <span class="text-danger mt-1" id="errorMobile"></span>
    </div>
    <div class="form-group">
        <label for="editEmail">Email <span class="text-danger">*</span></label>
        <input type="email" class="form-control"
               name="editEmail"
               id="editEmail"
               value="${email}"
               placeholder="Nhập số email">
        <span class="text-danger mt-1" id="errorEmail"></span>
    </div>
     <div class="form-group">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="${status}}" id="editStatus" {{if status == 0}}checked{{/if}}>
            <label class="form-check-label" for="editStatus">Hoạt động</label>
     </div>
     <div class="modal-footer">
        <button type="button" class="btn btn-secondary cancel-user-form"  data-dismiss="modal">Hủy</button>
        <button type="button" class="btn btn-primary" data-id="${id}" id="btnSaveEditUser">Lưu</button>
     </div>
</script>
<script id="userRowTmpl" type="text/x-jQuery-tmpl">
    <tr>
        <td><input type="checkbox" name="userId" value="${id}"/></td>
        <td>${username}</td>
        <td>${name}</td>
        <td>${mobile}</td>
        <td>${email}</td>
        <td>${formatDateTime(createdDate)}</td>
        <td>{{if lastLoginDate }}${formatDateTime(lastLoginDate)}{{else}}{{/if}}</td>
        <td>{{if status == 0 }}Hoạt động{{else}}Khóa{{/if}}</td>
        <td><button class="btn btn-sm btn-info btnEditUser" data-id="${id}"><i class="fas fa-pencil"></i> Sửa</button></td>
    </tr>
</script>
<script>
    $(document).ready(function () {
        UserManager.renderDataTable()
    })
</script>