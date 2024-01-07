$(document).ready(function () {

    let dateInput = $('#form-reviewClaimDetail__date');
    calculateDuration();
    updateDay();


    function updateDay() {
        let dayInput = $('#form-reviewClaimDetail__day');

        let selectedDate = new Date(dateInput.val());
        let options = {weekday: 'long'};
        let day = selectedDate.toLocaleDateString('en-US', options);

        dayInput.val(day);
    }

    dateInput.on('change', function () {
        updateDay();
    });

    // Logic sử lí tính Duration Project
    function calculateDuration() {
        let startDateInput = $('#form-reviewClaimDetail__startDate');
        let endDateInput = $('#form-reviewClaimDetail__endDate');
        let durationInput = $('#form-reviewClaim__project-duration');

        let startDate = new Date(startDateInput.val());
        let endDate = new Date(endDateInput.val());

        let duration = Math.abs(endDate - startDate);
        let days = Math.ceil(duration / (1000 * 60 * 60 * 24));

        durationInput.val(days + ' days');
    }

    // Logic tính total hours
    function calculateTotalHours() {
        let fromTime = $('#form-reviewClaimDetail__fromTime').val();
        let toTime = $('#form-reviewClaimDetail__toTime').val();
        let fromTimeObj = new Date("1970-01-01T" + fromTime + "Z");
        let toTimeObj = new Date("1970-01-01T" + toTime + "Z");


        let totalHours = (toTimeObj - fromTimeObj) / (1000 * 60 * 60);
        totalHours = Math.round(totalHours);
        $('#form-reviewClaimDetail__totalHours').val(totalHours);

    }

    $("#form-reviewClaimDetail__fromTime").change(function () {
        calculateTotalHours();
    });

    $("#form-reviewClaimDetail__toTime").change(function () {
        calculateTotalHours();
    });

    $('#form-reviewClaimDetail__fromTime, #form-reviewClaimDetail__toTime').on('change', function () {
        calculateTotalHours();
    });

});

$("#approveBtn").click(function () {
    $("#form-reviewClaim").attr("action", "/claim/approval/approve");
    $("#modal-content").html(
        "<p>This action will approve Claim.</p>" +
        "<p>Please click 'OK' to approve the claim or 'Cancel' to close the dialog</p>");
});

$("#returnBtn").click(function () {
    $("#form-reviewClaim").attr("action", "/claim/approval/return");
    $("#modal-content").html(
        "<p>This action will return Claim.</p>" +
        "<p>Please click 'OK' to return the claim or 'Cancel' to close the dialog.</p>");
});

$("#rejectBtn").click(function () {
    $("#form-reviewClaim").attr("action", "/claim/approval/reject");
    $("#modal-content").html(
        "<p>This action will reject Claim.</p>" +
        "<p>Please click 'OK' to reject the claim or 'Cancel' to close the dialog.</p>");
});

$("#form-reviewClaim").validate({
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

        remarks: {
            required: true,
        }

    },
    messages: {
        remarks: {
            required: "Please enter remarks",
        }

    },
    submitHandler: function (form) {
        form.submit();
    },
});
