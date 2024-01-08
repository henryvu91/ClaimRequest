$(document).ready(function () {
    $(document).off('click').on('click', '.detail', function () {
        let recordId = $(this).data("id");
        console.log(recordId)
        $.ajax({
            url: "/claim/detail",
            type: "GET",
            data: {id: recordId},
            success: function (data) {
                $('#modal-body-content').html(data)
            },
            error: function (xhr, status, error) {
                alert("Error: " + error.message);
            },
        })
    });

    $(document).on("click", ".btn-submitClaim", function () {
        let recordId = $(this).data("id");
        let $modal = $('#modalBootstrap');
        let contentPopup = $('<div>This action will Submit Claim.</div>' +
            '<div>Please click ‘OK’ to submit the claim or ‘Cancel’ to close the dialog.</div>');

        $modal.find('.modal-header').find('h1').text('SubMit Claim');
        $modal.find('.modal-body').html(contentPopup);
        $('#btn-okPopup').attr("data-id", recordId);
    })

    $(document).on("click", ".btn-cancelPopups", function () {
        $('#btn-okPopup').removeAttr("data-id");
    })


    $(document).on("click", ".btn-okPopups", function () {
        let recordId = $(this).data("id");
        let url = "/api/claim/myDraft/submitClaim";
        let title = "Submit Claim";
        submitClaim(recordId, url, title);
    });

    function submitClaim(recordId, url, title) {
        $.ajax({
            url: url,
            type: "POST",
            data: {id: recordId},
            success: function (data) {
                sessionStorage.setItem('toastMessage', JSON.stringify({message: data.message, title: title}));
                window.location.reload();
            },
            error: function (xhr, status, error) {
                sessionStorage.setItem('toastMessage', JSON.stringify({message: error.message, title: title}));
                window.location.reload();
            },
        });
    }
});