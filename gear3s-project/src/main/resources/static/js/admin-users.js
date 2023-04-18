$(document).ready(function () {
    assignDataToTable()
    function assignDataToTable() {
        $('#dataTable').DataTable({
            scrollY: 300,
            scrollX: true,
            ajax: {
                url: '/api/admin/user/list', //http://localhost:8088/api/admin/user/list
                dataSrc: function(data) {
                    let arrayData = []
                    for (let i = 0; i < data.data.length; i++) {
                        let object = data.data[i]
                        object.stt = i + 1
                        arrayData.push(object)
                    }
                    return arrayData
                },
            },
            columns: [
                {data: 'stt'},
                {data: 'id'},
                {data: 'email'},
                {data: 'fullname'},
                {data: 'phone'},
                {data: 'birthday'},
                {
                    data: 'roleId',
                    render: function (data, type) {
                        let returnData = ''
                        switch (data) {
                            case 1:
                                returnData = '<span class="badge badge-pill badge-success">ADMIN</span>'
                                break
                            case 2:
                                returnData = '<span class="badge badge-pill badge-primary">USER</span>'
                                break
                            default:
                                returnData = '<span class="badge badge-pill badge-warning">NONE</span>'
                        }
                        return returnData
                    }
                },
                {
                    data: null,
                    render: function (data, type, row) {
                        let id = row.id
                        if (row.email === 'admin@gmail.com') {
                            return null
                        }
                        return '<a href="#deleteModal" data-toggle="modal" class="deleteRowNe btn btn-outline-warning"><i class="fas fa-trash"></i></a>' +
                            `<input type="hidden" id="id" value=${id}>`
                    }
                },
                {
                    data: null,
                    render: function (data, type, row) {
                        let id = row.id
                        if (row.email === 'admin@gmail.com') {
                            return null
                        }
                        return `<a href="#editModal" data-toggle="modal" class="editRowNe btn btn-outline-info"><i class="fas fa-edit"></i></a>` +
                            `<input type="hidden" id="id" value=${id}>`
                    }

                }
            ]
        });
    }

    let defAddInfo = {
        email: '',
        password: '',
        roleId: 1
    }
    let addInfo = defAddInfo

    $('#roleId').change(function() {
        let isAdmin = $(this).prop('checked')
        // console.log(isAdmin)
        addInfo.roleId = isAdmin ? 1 : 2
    })

    let $alertBox = $("#alertBox")
    $alertBox.hide()
    function showAlert(alertMessage) {
        $alertBox.text(alertMessage)
        $alertBox.fadeTo(2000, 500).slideUp(500, function() {
            $alertBox.slideUp(500);
        });
        addInfo = defAddInfo
    }

    $( "#addForm" ).submit(function( event ) {
        event.preventDefault();
        addInfo.email = $(this).find('input[name="email"]').val()
        addInfo.password = $(this).find('input[name="password"]').val()

        $.ajax({
            url: "/api/admin/user/add",
            type: "POST",
            data: JSON.stringify(addInfo),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                $("#addModal").modal("hide")
                console.log(addInfo)
                showAlert(data.message)
                location.href = "/admin/users";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $("#addModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    });
    $(document).on('click', '.editRowNe', function(){
        let id = $(this).parent().find('#id').val()
        $.ajax({
            url: '/api/admin/user/detail/' + id,
            type: "GET",
            success: function (data) {
                let userData = data.data
                $('#editModal input[name="id"]').val(userData.id)
                $('#editModal input[name="email"]').val(userData.email)
                $('#editModal input[name="FullName"]').val(userData.fullname)
                $('#editModal input[name="phone"]').val(userData.phone)
                $('#editModal input[name="birthday"]').val(userData.birthday)
                console.log(userData.roleId)
                if (userData.roleId === 1) {
                    $('#editModal input[name="roleId"]').bootstrapToggle('on')
                } else {
                    $('#editModal input[name="roleId"]').bootstrapToggle('off')
                }
            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    })
})
