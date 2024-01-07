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

    $(document).on("click", ".btn-submit-claimByIds", function () {
        let recordId =
            (this).data("id");
        let url = "/api/claim/myDraft/submitClaim";
        let title = "Submit Claim";
        if (confirm("Are you sure you want to submit this record?")) {
            submitClaim(recordId, url, title);
        }
    });

    function submitClaim(recordId, url, title) {
        $.ajax({
            url: url,
            type: "POST",
            data: {id: recordId},
            success: function (data) {
                window.location.reload();
                handleSuccessfulClaimSubmission(data, title)
            },
            error: function (xhr, status, error) {
                alert("Error: " + error.message);
            },
        });
    }

    function handleSuccessfulClaimSubmission(data, title) {
        var toast = createToast(title, data.message);
        $('.toast-container').append(toast);
        var toastElement = new bootstrap.Toast(toast[0]);
        toastElement.show();
        updateTimestampEverySecond(toast);
    }

    function createToast(title, message) {
        return $('<div class="toast" role="alert" aria-live="assertive" aria-atomic="true"/>')
            .append($('<div class="toast-header"/>')
                .append('<strong class="me-auto">' + title + '</strong>')
                .append('<small class="text-muted time-ago">Just now</small>')
                .append('<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>')
            )
            .append($('<div class="toast-body"/>').text(message));
    }

    function updateTimestampEverySecond(toast) {
        var startTime = Date.now();
        var intervalId = setInterval(function () {
            var elapsedTime = Date.now() - startTime;
            var seconds = Math.round(elapsedTime / 1000);
            var timeAgoText = seconds < 2 ? 'Just now' : seconds + ' seconds ago';
            toast.find('.time-ago').text(timeAgoText);
        }, 1000);

        toast.on('hidden.bs.toast', function () {
            clearInterval(intervalId);
        });
    }
});