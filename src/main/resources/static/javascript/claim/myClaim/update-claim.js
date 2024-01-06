$(document).ready(function () {
    // // project name dom
    // const selectedProject = $("#form-updateClaim__project-name");
    //
    // // load the detail in firstTime
    // loadDetail(selectedProject.val(), calculateDuration);
    //
    // //     add event change in name
    // selectedProject.change(function () {
    //     let workingId = this.value;
    //     loadDetail(workingId, calculateDuration);
    // });
    //
    // // Load information of working
    // function loadDetail(workingId, callback) {
    //     $.ajax({
    //             url: "/claim/myClaim/workingDetail?workingId=" + workingId,
    //             success: function (result) {
    //                 $("#workingDetail").html(result);
    //                 callback();
    //             }
    //         }
    //     );
    //     calculateDuration();
    // }
    let dateInput = $('#form-updateClaimDetail__date');
    calculateDuration();
    updateDay();


    function updateDay() {
        let dayInput = $('#form-updateClaimDetail__day');

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
        let startDateInput = $('#form-updateClaimDetail__startDate');
        let endDateInput = $('#form-updateClaimDetail__endDate');
        let durationInput = $('#form-updateClaim__project-duration');

        let startDate = new Date(startDateInput.val());
        let endDate = new Date(endDateInput.val());

        let duration = Math.abs(endDate - startDate);
        let days = Math.ceil(duration / (1000 * 60 * 60 * 24));

        durationInput.val(days + ' days');
    }

    // Logic tính total hours
    function calculateTotalHours() {
        let fromTime = $('#form-updateClaimDetail__fromTime').val();
        let toTime = $('#form-updateClaimDetail__toTime').val();
        let fromTimeObj = new Date("1970-01-01T" + fromTime + "Z");
        let toTimeObj = new Date("1970-01-01T" + toTime + "Z");


        let totalHours = (toTimeObj - fromTimeObj) / (1000 * 60 * 60);
        // totalHours = Math.round(totalHours * 100) / 100;
        totalHours = Math.round(totalHours);
        $('#form-updateClaimDetail__totalHours').val(totalHours);

    }

    $("#form-updateClaimDetail__fromTime").change(function () {
        calculateTotalHours();
    });

    $("#form-updateClaimDetail__toTime").change(function () {
        calculateTotalHours();
    });

    $('#form-updateClaimDetail__fromTime, #form-updateClaimDetail__toTime').on('change', function () {
        calculateTotalHours();
    });

});

$.validator.methods.isClaimDateInDuration = function (value, element) {
    let startDate = $("#form-updateClaimDetail__startDate").val();
    let endDate = $("#form-updateClaimDetail__endDate").val();
    return value >= startDate && value <= endDate;
}

$.validator.methods.isClaimDateAfterJoinBeforeLeftProjectDate = function (value, element) {
    let startDate = $("#form-updateClaim__joinedDate").val();
    let endDate = $("#form-updateClaim__leftDate").val();
    return value >= startDate && (endDate === "" || value <= endDate);
}

$.validator.methods.isClaimDateAfterNow = function (value, element) {
    let today = new Date();
    today.setHours(0,0,0,0);
    let claimDate = new Date(value);
    claimDate.setHours(0,0,0);
    return claimDate >= today;
}

$.validator.methods.isToTimeAfterFromTime = function (value, element) {
    let fromTime = $("#form-updateClaimDetail__fromTime").val();

    return value >= fromTime;
}

$("#form-updateClaim").validate({
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
        workingId: {
            required: true,
        },
        remarks: {
            required: true,
        },

        date: {
            required: true,
            isClaimDateInDuration: true,
            isClaimDateAfterJoinBeforeLeftProjectDate: true,
            isClaimDateAfterNow: true
        },
        fromTime: {
            required: true
        },
        toTime: {
            required: true,
            isToTimeAfterFromTime: true
        }

    },
    messages: {
        workingId: {
            required: "Please input the project",
        },
        remarks: {
            required: "Please enter remarks",
        },
        date: {
            required: "Please input the claim date",
            isClaimDateInDuration: "Claim date must be within duration of project",
            isClaimDateAfterJoinBeforeLeftProjectDate: "Claim date must happen when claimer is in project",
            isClaimDateAfterNow: "Claim date must be after today"
        },
        fromTime: {
            required: "Please input the start time of the claim"
        },
        toTime: {
            required: "Please input the end time of the claim",
            isToTimeAfterFromTime: "To time must be after From time"
        }

    },
    submitHandler: function (form) {
        form.submit();
    },
});
