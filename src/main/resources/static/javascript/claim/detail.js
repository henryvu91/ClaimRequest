$(document).ready(function () {
    $('#detail').click(function () {
        $.ajax({
            url : "http://localhost:8080/claim/detail",
            method : "GET",
            success : function (data) {
                $('#modal-body-content').html(data)
            }
        })
    });
});