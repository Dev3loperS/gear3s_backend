$(function () {
    let userTable = $('#dataTable').DataTable({
        scrollY: 300,
        scrollX: true,
        ajax: {
            url: '/api/admin/category', //http://localhost:8088/
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
        addInfo = {
            email: '',
            password: '',
            roleId: 1
        }
    }


})