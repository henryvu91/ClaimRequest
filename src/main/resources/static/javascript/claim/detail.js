$(document).ready(function () {
    $('#detail').click(function () {
        let staffName = $(this).closest('tr').find('td:eq(1)').text();
        let projectName = $(this).closest('tr').find('td:eq(2)').text();

        $.ajax({
            url : "http://localhost:8080/claim/detail",
            method : "GET",
            success : function (data) {
                $('#modal-body-content').html(data)
            }
        })
    });
});