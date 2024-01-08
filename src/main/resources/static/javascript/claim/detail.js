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
        $('#btn-ok-claimById').attr("data-id", recordId);
    })

    $(document).on("click", ".btn-cancel-claimByIds", function () {
        $('#btn-ok-claimById').removeAttr("data-id");
    })


    $(document).on("click", ".btn-ok-claimByIds", function () {
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
                sessionStorage.setItem('submittedClaim', JSON.stringify({message: data.message, title: title}));
                window.location.reload();
            },
            error: function (xhr, status, error) {
                sessionStorage.setItem('submittedClaim', JSON.stringify({message: error.message, title: title}));
                window.location.reload();
            },
        });
    }

    $(function () {
        var submittedClaimData = sessionStorage.getItem('submittedClaim');
        if (submittedClaimData) {
            submittedClaimData = JSON.parse(submittedClaimData);
            handleSuccessfulClaimSubmission(submittedClaimData.message, submittedClaimData.title);
            // Clear the session storage flag
            sessionStorage.removeItem('submittedClaim');
        }
    });

    function handleSuccessfulClaimSubmission(message, title) {
        var toast = createToast(title, message);
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