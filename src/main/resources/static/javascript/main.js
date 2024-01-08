$(document).ready(function () {
    let $contentMain = $("#content-main");

    let $startDate = $(".startDate");
    let $endDate = $(".endDate");
    let $duration = $(".duration");
    let $startDateRecord = $(".startDateRecord");

    let $projectStartDate = $(".start-date");
    let $projectEndDate = $(".end-date");

    let today = new Date();
    let formattedDate = today.toISOString().substring(0, 10);

    $startDate.val(formattedDate);
    $startDateRecord.val(formattedDate);
    $endDate.attr("min", formattedDate);
    $startDateRecord.attr("min", $startDate.val());

    projectDuration();


    function calculateDuration() {
        let startDateVal = new Date($startDate.val());
        let endDateVal = new Date($endDate.val());

        if (!isNaN(startDateVal.getTime()) && !isNaN(endDateVal.getTime())) {
            let timeDiff = endDateVal - startDateVal;
            let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            $duration.val(`${diffDays + 1} Days`);
        } else {
            $duration.val("");
        }
    }

    function projectDuration() {
        let startDateVal = new Date($projectStartDate.val());
        let endDateVal = new Date($projectEndDate.val());

        if (!isNaN(startDateVal.getTime()) && !isNaN(endDateVal.getTime())) {
            let timeDiff = endDateVal - startDateVal;
            let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            $duration.text(diffDays + 1);
        } else {
            $duration.text("");
        }
    }

    $startDate.on("change", function () {
        let $newMinDate = $startDate.val();
        $endDate.attr("min", $newMinDate);
        $(".startDateRecord").attr("min", $newMinDate);
        calculateDuration();
    });
    $endDate.on("change", function () {
        let $newMaxDate = $endDate.val();
        // $startDateRecord.attr("max", $newMaxDate);
        $(".startDateRecord").attr("max", $newMaxDate);
        calculateDuration();
    });

    $(function () {
        clearModal();
        var submittedClaimData = sessionStorage.getItem('toastMessage');
        if (submittedClaimData) {
            submittedClaimData = JSON.parse(submittedClaimData);
            handleSuccessfulClaimSubmission(submittedClaimData.message, submittedClaimData.title);
            // Clear the session storage flag
            sessionStorage.removeItem('toastMessage');
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

    function clearModal() {
        let $modal = $('#modalBootstrap');
        $modal.find('.modal-header').find('h1').text('');
        $modal.find('.modal-body').children().remove();
        $('#btn-okPopup').removeAttr("data-id");
    }

    $("#form-login__username").change(function () {
        $("#form-login__message").children().remove();
    });
    $("#form-login__password").change(function () {
        $("#form-login__message").children().remove();
    });

    $("#form-register").validate({
        errorClass: "is-invalid",
        validClass: "is-valid",
        errorElement: "div",
        errorPlacement: function (error, element) {
            error.addClass("invalid-feedback");
            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.next("label"));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass(errorClass).removeClass(validClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass(errorClass).addClass(validClass);
        },
        rules: {
            username: {
                required: true,
                minLength: 5,
                maxlength: 50,
            },
            email: {
                email: true,
                required: true,
                maxlength: 50,
            },
            password: {
                required: true,
                minLength: 5,
                maxlength: 50,
            },
            confirmPassword: {
                required: true,
                maxlength: 50,
                equalTo: "#form-register__password",
            },
        },
        messages: {
            username: {
                required: "Please enter username",
                minlength: "Username must be at least 5 characters",
                maxlength: "Username must be less than 50 characters",
            },
            email: {
                required: "Please enter email",
                email: "Please enter valid email",
                minlength: "Email must be at least 5 characters",
                maxlength: "Email must be less than 50 characters",
            },
            password: {
                required: "Please enter password",
                minlength: "Password must be at least 5 characters",
                maxlength: "Password must be less than 50 characters",
            },
            confirmPassword: {
                required: "Please enter confirm password",
                maxlength: "Confirm password must be less than 50 characters",
                equalTo: "Password and confirm password does not match",
            },
        },
        submitHandler: function (form) {
            form.submit();
        },
    });

    $("#form-login").validate({
        errorClass: "is-invalid",
        validClass: "is-valid",
        errorElement: "div",
        errorPlacement: function (error, element) {
            error.addClass("invalid-feedback");
            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.next("label"));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass(errorClass).removeClass(validClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass(errorClass).addClass(validClass);
        },
        rules: {
            username: {
                required: true,
            },
            password: {
                required: true,
            },
        },
        messages: {
            username: {
                required: "Please enter username",
            },
            password: {
                required: "Please enter password",
            },
        },
        submitHandler: function (form) {
            form.submit();
        },
    });

    $(".resetDataForm").click(function () {
        let form = $(this).closest("form");

        form.find(".reset").val("");

        form.find(".is-invalid").removeClass("is-invalid");
        form.find(".is-valid").removeClass("is-valid");
        form.find(".invalid-feedback").remove();

        form.validate().resetForm();

        return false;
    });
});
