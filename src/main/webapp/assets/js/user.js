function validate() {
    let result = true;
    let mobile = $("#mobile").val()
    let email = $("#email").val()
    if (!validateEmail(email)) {
        $("#errorEmail").text("Email không đúng định dạng")
        result = false;
    }

    if (!validateMobile(mobile)) {
        $("#errorMobile").text("Số điện thoại không đúng định dạng")
        result = false;
    }
    return result;

}

function validatePassword(password) {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,15}/
    return regex.test(password)
}

function validateUsername(value) {
    const regex = /^[a-zA-Z0-9_.-]*$/
    return regex.test(value)
}

function validateEmail(value) {
    const regex = /^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})$/
    return regex.test(value)
}

function validateMobile(value) {
    const regex = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/
    return regex.test(value)
}
