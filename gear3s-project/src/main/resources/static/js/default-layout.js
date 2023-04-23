$(function () {
    let TOKEN_KEY = "jwt"

    function removeJwtToken(cname, cvalue) {
        let expires = "expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        document.cookie = cname + "=" + cvalue + ";" + expires;
    }

    $('#btn-logout').on("click", function () {
        removeJwtToken(TOKEN_KEY, null);
        localStorage.removeItem("info")
        location.href="/admin/login"
    })


    function setProfile() {
        let profile = JSON.parse(localStorage.getItem('info'))
        $("#myAvatar").attr('src', profile.avatar)
        $("#userDropdown").find('span').text(profile.fullname)
    }
    setProfile()
})