$(function () {
    let userTable = $('#dataTable').DataTable({
        scrollY: 400,
        scrollX: true,
        ajax: {
            url: '/api/admin/product/list', //http://localhost:8088/api/admin/product/list
            dataSrc: function(data) {
                return data.data
            },
        },
        columns: [
            {data: 'id'},
            {data: 'name'},
            {data: 'price_origin'},
            {
                data: 'inventory',
                render: function (data, type, row) {
                    return row.sold_qty + '/' + (data + row.sold_qty)
                }
            },
            {data: 'createDate'},
            {data: 'categoryId'},
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

    function loadCategories() {
        let returnData = []
        $.ajax({
            url: `/api/admin/category`,
            type: "GET",
            success: function (data) {
                returnData = data.data
            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                console.log("ERROR get all Products" + errorMessage)
            },
            async: false
        });
        return returnData
    }
    $('#addModal').on('show.bs.modal', function (e) {
        let cates = loadCategories()
        $("#addCategory").html('')
        for (let i in cates) {
            $("#addCategory").append(`<option value="${cates[i].id}">- ID: ${cates[i].id}: ${cates[i].name}</option>`);
        }
    })

    let addInfo = {
        "categoryId": 0,
        "description": "",
        "inventory": 0,
        "name": "",
        "price_discount": 0,
        "price_origin": 0
    }
    // ADD SUBMIT
    $( "#addForm" ).submit(function( event ) {
        event.preventDefault()
        addInfo = {
            "categoryId": $('select#addCategory').find(':selected').val(),
            "description": $(this).find('textarea[name="description"]').val(),
            "inventory": $(this).find('input[name="inventory"]').val(),
            "name": $(this).find('input[name="name"]').val(),
            "price_discount": $(this).find('input[name="price_discount"]').val(),
            "price_origin": $(this).find('input[name="price_origin"]').val()
        }
        console.log(addInfo)
        $.ajax({
            url: `/api/admin/product/add`,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(addInfo),
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
        })
    })

    function compareObject(object1, object2) {
        const keys1 = Object.keys(object1);
        const keys2 = Object.keys(object2);

        if (keys1.length !== keys2.length) {
            return false;
        }

        for (let key of keys1) {
            if (object1[key] !== object2[key]) {
                return false;
            }
        }

        return true;
    }

    // SHOW EDIT FORM
    let editInfo = {
        "id": 0,
        "name": "",
        "price_origin": 0,
        "price_discount": 0,
        "inventory": 0,
        "sold_qty": 0,
        "view_qty": 0,
        "createDate": "28-02-2023",
        "categoryId": 0,
        "description": ""
    }
    let oldEditInfo = {
        "id": 0,
        "name": "",
        "price_origin": 0,
        "price_discount": 0,
        "inventory": 0,
        "sold_qty": 0,
        "view_qty": 0,
        "createDate": "28-02-2023",
        "categoryId": 0,
        "description": ""
    }
    $(document).on('click', '.editRowNe', function(){
        let id = $(this).parent().find('#id').val()
        $.ajax({
            url: `/api/admin/product/detail/${id}`,
            type: "GET",
            success: function (data) {
                let product = data.data
                let cates = loadCategories()
                editInfo = {...product}
                oldEditInfo = {...product}
                onOffEditBtn()
                $('#editModal input[name="id"]').val(product.id)
                $('#editModal input[name="name"]').val(product.name)
                $('#editModal input[name="price_origin"]').val(product.price_origin)
                $('#editModal input[name="price_discount"]').val(product.price_discount)
                $('#editModal input[name="inventory"]').val(product.inventory)

                $('#editModal input[name="sold_qty"]').val(product.sold_qty)
                $('#editModal input[name="view_qty"]').val(product.view_qty)
                $('#editModal input[name="createDate"]').val(product.createDate)
                // $('#editModal input[name="categoryId"]').val(product.categoryId)
                $('#editModal textarea[name="description"]').val(product.description)

                for (let i in cates) {
                    $("#category").append(`<option value="${cates[i].id}">- ID: ${cates[i].id}: ${cates[i].name}</option>`);
                }

                $("#category option[value="+ product.categoryId +"]").attr("selected","selected")

            },
            error: function (jqXHR) {
                let errorMessage = $.parseJSON(jqXHR.responseText).message
                showAlert(errorMessage)
                editInfo = {
                    "id": 0,
                    "name": "",
                    "price_origin": 0,
                    "price_discount": 0,
                    "inventory": 0,
                    "sold_qty": 0,
                    "view_qty": 0,
                    "createDate": "28-02-2023",
                    "categoryId": 0,
                    "description": ""
                }
                oldEditInfo = {
                    "id": 0,
                    "name": "",
                    "price_origin": 0,
                    "price_discount": 0,
                    "inventory": 0,
                    "sold_qty": 0,
                    "view_qty": 0,
                    "createDate": "28-02-2023",
                    "categoryId": 0,
                    "description": ""
                }
            }
        })
    })

    function onOffEditBtn() {
        if (!compareObject(editInfo, oldEditInfo)) {
            $('#editForm button[type="submit"]').removeAttr('disabled')
        } else {
            $('#editForm button[type="submit"]').attr('disabled','disabled')
        }

    }

    $("#editForm input").change(function () {
        let key = $(this).attr('name')
        let type = $(this).attr('type')
        editInfo[key] = (type !== 'number') ? $(this).val().trim() : parseInt($(this).val())
        onOffEditBtn()
    })
    $("#editForm textarea").keyup(function () {
        let key = $(this).attr('name')
        editInfo[key] = $(this).val().trim()
        onOffEditBtn()
    })
    $("#editForm select").on('change', function () {
        let key = $(this).attr('name')
        editInfo[key] = parseInt($(this).val())
        onOffEditBtn()
    })

    // EDIT SUBMIT
    $('#editForm').submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/product/edit`,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(editInfo),
            type: "PUT",
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

    let deleteId
    $(document).on('click', '.deleteRowNe', function(){
        deleteId = $(this).parent().find('#id').val()
        $("#deleteForm #content").html(`DO YOU WANT TO DELETE THIS PRODUCT WITH ID: <b>${deleteId}</b>?`)
    })

    $("#deleteForm").submit(function (e) {
        e.preventDefault()
        $.ajax({
            url: `/api/admin/product/delete/${deleteId}`,
            type: "DELETE",
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