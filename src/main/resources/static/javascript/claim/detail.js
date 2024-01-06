$(document).ready(function () {
    $(document).off('click').on('click', '.detail',function () {
        let recordId = $(this).data("id");
        console.log(recordId)
        $.ajax({
            url : "/claim/detail",
            type : "GET",
            data: { id: recordId},
            success : function (data) {
                $('#modal-body-content').html(data)
            },
            error: function (xhr, status, error) {
                alert("Error: " + error.message);
            },
        })
    });
});