window.UserManager = {
    userSetting: {
        host: "/admin/users",
        mod: "release",
        debugAgent: "web",
        root: $("#wrapper"),
        cookie: 1,
        pathPlugin: "/asset",
        requestTimeout: 5000,
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
            $("#userRowTmpl").tmpl(data).appendTo("#usersBody")
        })
    },
    validate: function () {
        let instance = this;
        let result = true;
        let mobile = $("#mobile").val()
        let email = $("#email").val()
        if (!instance.validateEmail(email)) {
            $("#errorEmail").text("Email không đúng định dạng")
            result = false;
        }

        if (!instance.validateMobile(mobile)) {
            $("#errorMobile").text("Số điện thoại không đúng định dạng")
            result = false;
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
        $.ajax({
            type: "POST",
            data: JSON.stringify(req),
            contentType: "application/json;charset=utf-8",
            url: instance.userSetting.host + "?action=delete",
            cache: false,
            success: function (response, textStatus, xhr) {
                console.log(response)
                console.log(xhr.status)
            },
            error: function (xhr, textStatus) {
                console.log(xhr.status)
            }
        })
    }
}
$(document).on("click", "#btnDeleteUser", function (e) {
    e.preventDefault();
    let userIds = []
    $("input:checkbox[name='userId']:checked").each(function () {
        userIds.push($(this).val())
    })
    if (userIds.length <= 0) {
        $.notify("Chưa chọn tài khoản muốn xóa", "warning")
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