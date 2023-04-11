// $.ajaxSetup({
//     headers: {
//         'x-access-token': localStorage.getItem("jwtToken")
//     }
// });

$(function () {
    // VARIABLES =============================================================
    let TOKEN_KEY = "jwt"

    // FUNCTIONS =============================================================

    function getCookie(cname) {
        let name = cname + "=";
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

    function getJwtToken() {
        return getCookie(TOKEN_KEY);
        // return $.cookie(TOKEN_KEY)
    }

    function setCookie(cname, cvalue, hours) {
        const d = new Date();
        d.setTime(d.getTime() + (hours*60*60*1000));
        let expires = "expires="+ d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }

    function setJwtToken(token) {
        setCookie(TOKEN_KEY, token, 0.5)
        // $.cookie(TOKEN_KEY, token)
        // localStorage.setItem(TOKEN_KEY, token);
    }

    function displayError(errorMessage) {
        let $errorModal = $('#loginErrorModal')
        if (errorMessage !== '') {
            $errorModal.children().first().text(errorMessage)
        }
        if ($errorModal.css("display") === 'none') {
            $errorModal.show()
        }
    }

    // function createAuthorizationTokenHeader() {
    //     let token = getJwtToken();
    //     if (token) {
    //         return {"Authorization": "Bearer " + token};
    //     } else {
    //         return {};
    //     }
    // }

    function doLogin(loginData) {
        $.ajax({
            url: "/api/admin/login/signin",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                // console.log(jqXHR)
                setJwtToken(data['data'].newToken);
                console.log("TOKEN: " + getJwtToken())
                // showTokenInformation();
                location.href = "/admin/home";

                // $.ajax({
                //     url: "/admin/users",
                //     type: "GET",
                //     dataType: "html",
                //     headers: createAuthorizationTokenHeader(),
                //     success: function (response) {
                //         $("html").html(response);
                //     }
                // })
            },
            error: function (jqXHR, textStatus, errorThrown) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                displayError(errorMessage)
                console.log("ERROR: " + jqXHR.status + "-" + jqXHR.responseText)
            }
        });
    }

    // REGISTER EVENT LISTENERS =============================================================
    $("#loginForm").submit(function (event) {
        event.preventDefault();

        let $form = $(this);
        let formData = {
            email: $form.find('input[name="email"]').val(),
            password: $form.find('input[name="password"]').val()
        };

        //alert("email: " + formData.email + ", pass: " + formData.password)
        //
        doLogin(formData);
    });

    $('#loginForm').keypress(function (e) {
        let key = e.which;
        if(key === 13)  // the enter key code
        {
            $("#loginForm").submit();
            return false;
        }
    });
});