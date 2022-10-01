window.UserManager = {
    userSetting: {
        host: "/admin/users",
        mod: "release",
        debugAgent: "web",
        root: $("#wrapper"),
        cookie: 1,
        pathPlugin: "/asset",
        requestTimeout: 5000,
        contentType: "application/json;charset=utf-8",
        dataTable: null,
        mode: "system"
    },
    init: function () {
        let instance = this;
        if (typeof jQuery === 'undefined') {
            console.error("Can not load jQuery environment")
        }
    },
    renderDataTable: function () {
        let instance = this;
        $.get(instance.userSetting.host + "?action=list", function (data) {
            console.log(data)
            if (data.error_code == 'WCD-00000000') {
                $("#usersBody").empty()
                $("#userRowTmpl").tmpl(data.data).appendTo("#usersBody")
            }
        })
    },
    createUser: function (e) {
        let instance = this;
        e.preventDefault()
        let username = $("#username").val()
        let password = $("#password").val()
        let name = $("#name").val()
        let mobile = $("#mobile").val()
        let email = $("#email").val()
        if (!instance.validate(username, password, name, mobile, email)) {
            return;
        }
        let formData = new FormData();
        let req = {
            username: username,
            password: password,
            name: name,
            mobile: mobile,
            email: email
        }
        console.log(formData)
        $.ajax({
            type: "POST",
            url: instance.userSetting.host + "?action=create",
            contentType: instance.userSetting.contentType,
            data: JSON.stringify(req),
            processData: false,
            cache: false,
            success: function (response) {
                console.log(response)
                if (response.error_code === 'WCD-00000000') {
                    $('#createUserModal').modal('hide');
                    instance.clear()
                    $.notify("Thêm mới người dùng thành công!", "success")
                    instance.renderDataTable()
                } else {
                    $.notify("Thêm mới người dùng thất bại: " + response.error_message)
                }
            },
            error: function (error) {
                console.log(error)
                $.notify("Lỗi khi thêm mới người dùng: " + error.message, "err")
            }
        })
    },
    editUser: function (e, userid) {
        let instance = this;
        e.preventDefault()
        let name = $("#editName").val()
        let mobile = $("#editMobile").val()
        let email = $("#editEmail").val()
        let status = $("#editStatus").prop("checked") === true ? 0 : 1
        if (!instance.validateBase(name, mobile, email)) {
            return;
        }
        let req = {
            id: userid,
            name: name,
            mobile: mobile,
            email: email,
            status: status
        }
        $.ajax({
            type: "POST",
            url: instance.userSetting.host + "?action=update",
            contentType: instance.userSetting.contentType,
            data: JSON.stringify(req),
            cache: false,
            success: function (response) {
                console.log(response)
                if (response.error_code === 'WCD-00000000') {
                    $('#editUserModal').modal('hide');
                    $.notify("Cập nhật người dùng thành công!", "success")
                    instance.clear()
                    instance.renderDataTable()
                } else {
                    $.notify("Cập nhật người dùng thất bại: " + response.error_message, "error")
                }
            },
            error: function (error) {
                console.log(error)
                $.notify("Lỗi khi cập nhật người dùng: " + error.message, "error")
            }
        })
    },
    showModalEditUser(userId) {
        let instance = this;
        $.get(instance.userSetting.host + "?action=detail&userId=" + userId, function (data) {
            if (data.error_code == 'WCD-00000000') {
                let user = data.data
                $("#editUser").empty()
                $("#editUserTmp").tmpl(user).appendTo("#editUser")
                $("#editUserModal").modal({
                    backdrop: 'static',
                    keyboard: false
                })
            } else if (data.error_code == 'WCD-00000404') {
                $.notify("Không tồn tại người dùng với id " + userId)
            }
        })
    },
    clear() {
        // Clear error
        $("#errorUsername").text("")
        $("#errorMobile").text("")
        $("#errorEmail").text("")
        $("#errorPassword").text("")
        // Clear form input
        $("#username").val("")
        $("#password").val("")
        $("#name").val("")
        $("#email").val("")
        $("#mobile").val("")
    },
    validate: function (username, password, name, mobile, email) {
        let instance = this;
        let result = true;
        result = instance.validateBase(name, mobile, email)
        if (!instance.validateUsername(username)) {
            $("#errorUsername").text("Tên đăng nhập không đúng định, tên đăng nhập không được chứa kí tự đặc biệt")
            result = false;
        } else {
            $("#errorUsername").text("")
        }
        if (!this.validatePassword(password)) {
            $("#errorPassword").text("Mật khẩu không đúng định dạng, Mật khẩu gồm chữa hoa, thường số, ký tự đặc biệt 6-15 ký tự")
            result = false;
        } else {
            $("#errorPassword").text("")
        }
        return result;
    },
    validateBase: function (name, mobile, email) {
        let instance = this;
        let result = true
        if (!name) {
            $("#errorName").text("Họ và tên không được trống")
            result = false
        }
        if (!instance.validateEmail(email)) {
            $("#errorEmail").text("Email không đúng định dạng")
            result = false;
        } else {
            $("#errorEmail").text("")
        }

        if (!instance.validateMobile(mobile)) {
            $("#errorMobile").text("Số điện thoại không đúng định dạng")
            result = false;
        } else {
            $("#errorMobile").text("")
        }
        return result;
    },
    validatePassword: function (password) {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,15}/
        return regex.test(password)
    },
    validateUsername: function (value) {
        const regex = /^[a-zA-Z0-9_.-]*$/
        return regex.test(value)
    },
    validateEmail: function (value) {
        const regex = /^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})$/
        return regex.test(value)
    },
    validateMobile: function (value) {
        const regex = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/
        return regex.test(value)
    },
    deleteUser: function (userIds) {
        let instance = this;
        let req = {
            userIds: userIds
        }
        if (confirm("Bạn có chắc chắn muốn xóa không ?")) {
            $.ajax({
                type: "POST",
                data: JSON.stringify(req),
                contentType: "application/json;charset=utf-8",
                url: instance.userSetting.host + "?action=delete",
                cache: false,
                success: function (response, textStatus, xhr) {
                    if (response.error_code === 'WCD-00000000') {
                        $.notify("Xóa người dùng thành công!", "success")
                        instance.renderDataTable()
                    } else {
                        $.notify("Xóa người dùng thất bại: " + response.error_message, "error")
                    }
                },
                error: function (xhr, textStatus) {
                    console.log(xhr.status)
                }
            })
        }
    }
}
$(document).on("click", "#btnDeleteUsers", function (e) {
    e.preventDefault();
    let userIds = []
    $("input:checkbox[name='userId']:checked").each(function () {
        userIds.push($(this).val())
    })
    if (userIds.length <= 0) {
        $.notify("Chưa chọn tài khoản muốn xóa", "error")
        return;
    }
    UserManager.deleteUser(userIds)

})

$(document).on("change", "#selectAllUsers", function (e) {
    e.preventDefault()
    if (this.checked) {
        $("input[name='userId']").prop("checked", true)
    } else {
        $("input[name='userId']").prop("checked", false)
    }

})

$(document).on("click", "#btnCreateUser", function (e) {
    UserManager.createUser(e);
})

$(document).on("click", ".btnEditUser", function (e) {
    e.preventDefault();
    let userId = $(this).attr("data-id")
    UserManager.showModalEditUser(userId)
})

$(document).on("click", "#btnSaveEditUser", function (e) {
    e.preventDefault();
    let userId = $(this).attr("data-id")
    UserManager.editUser(e, userId);
})