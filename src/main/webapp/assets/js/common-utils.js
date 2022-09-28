function formatDate(dateStr) {
    let date = new Date(dateStr)
    let day = date.getUTCDate()
    let month = date.getUTCMonth()
    let year = date.getFullYear()
    return '' + (day <= 9 ? '0' + day : day) + '/' + (month <= 9 ? '0' + month : month) + '/' + year
}

function formatDateTime(dateStr) {
    let date = new Date(dateStr)
    return (date.getUTCDate() <= 9 ? '0' + date.getUTCDate() : date.getUTCDate()) + "/"
        + (date.getUTCMonth() <= 9 ? '0' + date.getUTCMonth() : date.getUTCMonth()) + "/"
        + date.getFullYear()
        + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()

}