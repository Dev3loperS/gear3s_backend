$(function () {
    let TOKEN_KEY = "jwt"

    function removeJwtToken(cname, cvalue) {
        let expires = "expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        document.cookie = cname + "=" + cvalue + ";" + expires;
    }

    $('#btn-logout').on("click", function () {
        console.log("AAA")
        removeJwtToken(TOKEN_KEY, null);
        location.href="/admin/login"
    })
})