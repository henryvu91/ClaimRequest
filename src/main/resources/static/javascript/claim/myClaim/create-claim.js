$(document).ready(function () {
    // project name dom
    const selectedProject = $("#form-addClaim__project-name");

    // load the detail in firstTime
    loadDetail(selectedProject.val(), calculateDuration);

    //     add event change in name
    selectedProject.change(function () {
        let workingId = this.value;
        loadDetail(workingId, calculateDuration);
    });

    // Load information of working
    function loadDetail(workingId, callback) {
        $.ajax({
                url: "/claim/myClaim/workingDetail?workingId=" + workingId,
                success: function (result) {
                    $("#workingDetail").html(result);
                    callback();
                }
            }
        );
        calculateDuration();
    }

    let dateInput = $('#form-addClaimDetail__date');

    function updateDay() {
        let dayInput = $('#form-addClaimDetail__day');

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
        let startDateInput = $('#form-addClaimDetail__startDate');
        let endDateInput = $('#form-addClaimDetail__endDate');
        let durationInput = $('#form-addClaim__project-duration');

        let startDate = new Date(startDateInput.val());
        let endDate = new Date(endDateInput.val());

        let duration = Math.abs(endDate - startDate);
        let days = Math.ceil(duration / (1000 * 60 * 60 * 24));

        durationInput.val(days + ' days');
    }

    // $('#form-addClaimDetail__startDate, #form-addClaimDetail__endDate').on('change', function () {
    //     calculateDuration();
    // });

    // Logic tính total hours
    function calculateTotalHours() {
        let fromTime = $('#form-addClaimDetail__fromTime').val();
        let toTime = $('#form-addClaimDetail__toTime').val();
        let fromTimeObj = new Date("1970-01-01T" + fromTime + "Z");
        let toTimeObj = new Date("1970-01-01T" + toTime + "Z");

        // if (toTimeObj < fromTimeObj) {
        //     $('#form-addClaimDetail__toTime').addClass('is-invalid');
        //     $('#toTimeValidationMessage').text('Vui lòng nhập lại giờ.');
        //     return;
        // }

        let totalHours = (toTimeObj - fromTimeObj) / (1000 * 60 * 60);
        // totalHours = Math.round(totalHours * 100) / 100;
        totalHours = Math.round(totalHours);
        $('#form-addClaimDetail__totalHours').val(totalHours);
        // $('#form-addClaimDetail__toTime').removeClass('is-invalid');
        // $('#toTimeValidationMessage').text('');
    }

    $("#form-addClaimDetail__fromTime").change(function () {
        calculateTotalHours();
    });

    $("#form-addClaimDetail__toTime").change(function () {
        calculateTotalHours();
    });

    $('#form-addClaimDetail__fromTime, #form-addClaimDetail__toTime').on('change', function () {
        calculateTotalHours();
    });

    // // Hiển thị audit trail
    // $('#saveBtn').on('click', function () {
    //     let currentTime = new Date();
    //     let formattedTime = currentTime.toLocaleString();
    //     $('#form-addClaimDetail__audit-trail').val("Created by " + creatorName + " on " + formattedTime);
    // });
});
// lưu thông tin nhập
// $(document).ready(function () {
//     $('#saveBtn').on('click', function (e) {
//         e.preventDefault();
//
//         // Lấy giá trị của các trường nhập liệu
//         var status = $('#form-addClaim__status').val();
//         var staffId = $('#form-addClaim__staff-id').val();
//         var staffName = $('#form-addClaim__staff-name').val();
//         var staffDepartment = $('#form-addClaim__staff-department').val();
//         var projectName = $('#form-addClaim__project-name').val();
//         var startDate = $('#form-addClaimDetail__startDate').val();
//         var endDate = $('#form-addClaimDetail__endDate').val();
//         var projectDuration = $('#form-addClaim__project-duration').val();
//         var claimDetailDate = $('#form-addClaimDetail__date').val();
//         var claimDetailDay = $('#form-addClaimDetail__day').val();
//         var claimDetailFromTime = $('#form-addClaimDetail__fromTime').val();
//         var claimDetailToTime = $('#form-addClaimDetail__toTime').val();
//         var claimDetailTotalHours = $('#form-addClaimDetail__totalHours').val();
//         var remarks = $('#form-addClaimDetail__remarks').val();
//         var auditTrail = $('#form-addClaimDetail__audit-trail').val();
//
//         // Lưu các giá trị vào localStorage hoặc thực hiện các thao tác lưu dữ liệu khác
//         localStorage.setItem('status', status);
//         localStorage.setItem('staffId', staffId);
//         localStorage.setItem('staffName', staffName);
//         localStorage.setItem('staffDepartment', staffDepartment);
//         localStorage.setItem('projectName', projectName);
//         localStorage.setItem('startDate', startDate);
//         localStorage.setItem('endDate', endDate);
//         localStorage.setItem('projectDuration', projectDuration);
//         localStorage.setItem('claimDetailDate', claimDetailDate);
//         localStorage.setItem('claimDetailDay', claimDetailDay);
//         localStorage.setItem('claimDetailFromTime', claimDetailFromTime);
//         localStorage.setItem('claimDetailToTime', claimDetailToTime);
//         localStorage.setItem('claimDetailTotalHours', claimDetailTotalHours);
//         localStorage.setItem('remarks', remarks);
//         localStorage.setItem('auditTrail', auditTrail);
//
//         // Hiển thị thông báo thành công
//         alert('Data saved successfully!');
//     });
// });

//  bắt validate chọn Date trong khoảng Start DATE VÀ end date
// function validateDate() {
//     var startDate = $("#form-addClaimDetail__startDate").val();
//     var endDate = $("#form-addClaimDetail__endDate").val();
//     var dateInput = $("#form-addClaimDetail__date").val();
//     var dateValidationMessage = $("#dateValidationMessage");
//
//     if (dateInput < startDate || dateInput > endDate) {
//         dateValidationMessage.text("Date must be within the range of Start Date and End Date");
//     } else {
//         dateValidationMessage.text("");
//     }
// }

$.validator.methods.isClaimDateInDuration = function (value, element) {
    let startDate = $("#form-addClaimDetail__startDate").val();
    let endDate = $("#form-addClaimDetail__endDate").val();
    return value >= startDate && value <= endDate;
}

$.validator.methods.isClaimDateAfterJoinBeforeLeftProjectDate = function (value, element) {
    let startDate = $("#form-addClaim__joinedDate").val();
    let endDate = $("#form-addClaim__leftDate").val();
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
    let fromTime = $("#form-addClaimDetail__fromTime").val();

    return value >= fromTime;
}

$("#form-addClaim").validate({
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
// h của to phải lớn hơn from trong ngày

// $(document).ready(function() {
//   var fromTimeInput = $("#form-addClaimDetail__fromTime");
//   var toTimeInput = $("#form-addClaimDetail__toTime");
//   var createButton = $("#createButton");

//   toTimeInput.on("change", function() {
//     var fromTime = fromTimeInput.val();
//     var toTime = toTimeInput.val();
//     var fromDateTime = new Date("1970-01-01T" + fromTime + "Z");
//     var toDateTime = new Date("1970-01-01T" + toTime + "Z");

//     if (toDateTime <= fromDateTime) {

//       toTimeInput.val("");
//       toTimeInput.attr("placeholder", "Please enter a valid time");
//     }
//   });
// });