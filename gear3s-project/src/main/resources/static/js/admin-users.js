$(document).ready(function () {
    let userTable = $('#dataTable').DataTable({
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
                    if (row.roleId === 1) {
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

    let addInfo = {
        email: '',
        password: '',
        roleId: 1
    }

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
        addInfo = {
            email: '',
            password: '',
            roleId: 1
        }
    }

    // ADD SUBMIT
    $( "#addForm" ).submit(function( event ) {
        event.preventDefault()
        addInfo.email = $(this).find('input[name="email"]').val()
        addInfo.password = $(this).find('input[name="password"]').val()

        $.ajax({
            url: "/api/admin/user/add",
            type: "POST",
            data: JSON.stringify(addInfo),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                $("#addModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload()
                // setTimeout(() => {location.href = "/admin/users"}, 1500)
            },
            error: function (jqXHR) {
                $("#addModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    });

    // SHOW EDIT FORM
    let editInfo = {
        roleId: '',
        userId: ''
    }
    let oldEditInfo = {
        roleId: '',
        userId: ''
    }
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

                oldEditInfo.userId = userData.id
                oldEditInfo.roleId = userData.roleId
                editInfo.userId = userData.id
                editInfo.roleId = userData.roleId

                if (userData.roleId === 1) {
                    $('#editModal input[name="roleId"]').bootstrapToggle('on')
                } else {
                    $('#editModal input[name="roleId"]').bootstrapToggle('off')
                }
            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
                editInfo = {
                    roleId: '',
                    userId: ''
                }
                oldEditInfo = {
                    roleId: '',
                    userId: ''
                }
            }
        });
    })
    $('#editForm input[name="roleId"]').change(function() {
        let isAdmin = $(this).prop('checked')
        // console.log(isAdmin)
        editInfo.roleId = isAdmin ? 1 : 2
        if (editInfo.roleId !== oldEditInfo.roleId) {
            $('#editForm button[type="submit"]').removeAttr('disabled')
        } else {
            $('#editForm button[type="submit"]').attr('disabled','disabled')
        }
    })
    // EDIT SUBMIT
    $('#editForm').submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/user/role/${editInfo.userId}/${editInfo.roleId}`,
            type: "PUT",
            success: function (data) {
                $("#editModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload();
                // setTimeout(() => {location.href = "/admin/users"}, 1500)
            },
            error: function (jqXHR) {
                $("#editModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    })

    let deleteUserId
    $(document).on('click', '.deleteRowNe', function(){
        deleteUserId = $(this).parent().find('#id').val()
        $("#deleteForm #content").text("DO YOU WANT TO DELETE THIS ACCOUNT WITH ID: " + deleteUserId)
    })

    $("#deleteForm").submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/user/delete/${deleteUserId}`,
            type: "DELETE",
            success: function (data) {
                $("#deleteModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload()
                // setTimeout(() => {location.href = "/admin/users"}, 1500)
            },
            error: function (jqXHR) {
                $("#deleteModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    })
})

