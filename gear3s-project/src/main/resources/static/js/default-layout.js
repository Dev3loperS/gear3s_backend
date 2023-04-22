$(function () {
    let TOKEN_KEY = "jwt"

    function removeJwtToken(cname, cvalue) {
        let expires = "expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        document.cookie = cname + "=" + cvalue + ";" + expires;
    }

    $('#btn-logout').on("click", function () {
        removeJwtToken(TOKEN_KEY, null);
        removeJwtToken("info")
        location.href="/admin/login"
    })

    function getCookie(cname) {
        let name = cname + '=';
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(let i = 0; i <ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    function setProfile() {
        let profile = JSON.parse(getCookie('info'))
        $("#myAvatar").attr('src', profile.avatar)
        $("#userDropdown").find('span').text(profile.fullname)
    }
    setProfile()
})