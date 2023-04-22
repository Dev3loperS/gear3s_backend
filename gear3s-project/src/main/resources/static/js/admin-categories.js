$(function () {
    let userTable = $('#dataTable').DataTable({
        scrollY: 300,
        scrollX: true,
        ajax: {
            url: '/api/admin/category', //http://localhost:8088/
            dataSrc: function (data) {
                return data.data
            }
        },
        columns: [
            {data: 'id'},
            {data: 'name'},
            {
                data: null,
                render: function (data, type, row) {
                    let id = row.id
                    return '<a href="#deleteModal" data-toggle="modal" class="deleteRowNe btn btn-outline-warning"><i class="fas fa-trash"></i></a>' +
                        `<input type="hidden" id="id" value=${id}>`
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    let id = row.id
                    return `<a href="#editModal" data-toggle="modal" class="editRowNe btn btn-outline-info"><i class="fas fa-edit"></i></a>` +
                        `<input type="hidden" id="id" value=${id}>`
                }

            }
        ]
    });

    let $alertBox = $("#alertBox")
    $alertBox.hide()
    function showAlert(alertMessage) {
        $alertBox.text(alertMessage)
        $alertBox.fadeTo(2000, 500).slideUp(500, function() {
            $alertBox.slideUp(500);
        });
    }

    // ADD SUBMIT
    $( "#addForm" ).submit(function( event ) {
        event.preventDefault()
        let name = $(this).find('input[name="name"]').val()

        $.ajax({
            url: `/api/admin/category/add?name=${name}`,
            type: "POST",
            success: function (data) {
                $("#addModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload()
                $("#addForm")[0].reset()
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
        id: '',
        name: ''
    }
    let oldEditInfo = {
        id: '',
        name: ''
    }
    $(document).on('click', '.editRowNe', function(){
        let id = $(this).parent().find('#id').val()
        $.ajax({
            url: '/api/admin/category/findById?id=' + id,
            type: "POST",
            success: function (data) {
                let cateData = data.data
                $('#editModal input[name="id"]').val(cateData.id)
                $('#editModal input[name="name"]').val(cateData.name)

                oldEditInfo.id = cateData.id
                oldEditInfo.name = cateData.name
                editInfo.id = cateData.id
                editInfo.name = cateData.name
            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
                editInfo = {
                    id: '',
                    name: ''
                }
                oldEditInfo = {
                    id: '',
                    name: ''
                }
            }
        })
    })

    $('#editForm input[name="name"]').keyup(function() {
        editInfo.name = $(this).val().trim()
        if (editInfo.name !== oldEditInfo.name) {
            $('#editForm button[type="submit"]').removeAttr('disabled')
        } else {
            $('#editForm button[type="submit"]').attr('disabled','disabled')
        }
    })

    // EDIT SUBMIT
    $('#editForm').submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/category/update/${editInfo.id}?name=${editInfo.name}`,
            type: "POST",
            success: function (data) {
                $("#editModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload();
            },
            error: function (jqXHR) {
                $("#editModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        })
    })

    let deleteCateId
    $(document).on('click', '.deleteRowNe', function(){
        deleteCateId = $(this).parent().find('#id').val()
        $.ajax({
            url: '/api/admin/category/findById?id=' + deleteCateId,
            type: "POST",
            success: function (data) {
                let cateData = data.data
                $("#deleteForm #content").html(`DO YOU WANT TO DELETE THIS CATEGORY WITH NAME: <b>${cateData.name}</b>`)
            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        })
    })

    $("#deleteForm").submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/category/deleteById?id=${deleteCateId}`,
            type: "POST",
            success: function (data) {
                $("#deleteModal").modal("hide")
                showAlert(data.message)
                userTable.ajax.reload()
            },
            error: function (jqXHR) {
                $("#deleteModal").modal("hide")
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
            }
        });
    })
})