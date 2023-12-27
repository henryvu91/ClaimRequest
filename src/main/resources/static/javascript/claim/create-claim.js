$(document).ready(function () {
    // Cached jQuery selectors
    var $startDateInput = $('#form-addClaimDetail__startDate');
    var $endDateInput = $('#form-addClaimDetail__endDate');
    var $dateInput = $('#form-addClaimDetail__date');
    var $dayInput = $('#form-addClaimDetail__day');
    var $durationInput = $('#form-addClaim__project-duration');
    var $fromTime = $('#form-addClaimDetail__fromTime');
    var $toTime = $('#form-addClaimDetail__toTime');
    var $totalHours = $('#form-addClaimDetail__totalHours');
    var $dateValidationMessage = $("#dateValidationMessage");
    var $auditTrail = $('#form-addClaimDetail__audit-trail');

    // Update day name when date changes
    function updateDay() {
        let selectedDate = new Date($dateInput.val());
        let day = selectedDate.toLocaleDateString('en-US', {weekday: 'long'});
        $dayInput.val(day);
    }

    // Calculate and update duration
    function calculateDuration() {
        let startDate = new Date($startDateInput.val());
        let endDate = new Date($endDateInput.val());
        let duration = Math.abs(endDate - startDate);
        let days = Math.ceil(duration / (1000 * 60 * 60 * 24));
        $durationInput.val(days + ' days');
    }

    // Calculate and update total hours
    function calculateTotalHours() {
        var fromTimeObj = new Date("1970-01-01T" + $fromTime.val() + "Z");
        var toTimeObj = new Date("1970-01-01T" + $toTime.val() + "Z");
        var totalHours = Math.round(((toTimeObj - fromTimeObj) / (1000 * 60 * 60)) * 100) / 100;
        $totalHours.val(totalHours);
    }

    // Validate selected date within the range of start and end dates
    function validateDate() {
        var startDate = $startDateInput.val();
        var endDate = $endDateInput.val();
        var dateInput = $dateInput.val();
        if (dateInput < startDate || dateInput > endDate) {
            $dateValidationMessage.text("Date must be within the range of Start Date and End Date");
        } else {
            $dateValidationMessage.text("");
        }
    }

    // Event handlers
    $dateInput.on('change', function () {
        updateDay();
        validateDate();
    });
    $startDateInput.on('change', calculateDuration);
    $endDateInput.on('change', function () {
        calculateDuration();
        validateDate();
    });
    $fromTime.on('change', calculateTotalHours);
    $toTime.on('change', calculateTotalHours);

    // Save form data and display audit trail on save
    $('#saveBtn').on('click', function (e) {
        e.preventDefault();
        // Save form data logic here...

        // Audit trail
        let currentTime = new Date();
        let formattedTime = currentTime.toLocaleString();
        let creatorName = 'YourName'; // Replace with actual variable or logic to get creator's name
        $auditTrail.val("Created by " + creatorName + " on " + formattedTime);

        alert('Data saved successfully!');
    });
});