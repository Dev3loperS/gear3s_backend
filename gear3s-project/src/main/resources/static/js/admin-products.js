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
})