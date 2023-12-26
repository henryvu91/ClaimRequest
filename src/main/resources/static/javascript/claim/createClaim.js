$(document).ready(function() {
    function updateDay() {
      let dateInput = $('#form-addClaimDetail__date');
      let dayInput = $('#form-addClaimDetail__day');

      let selectedDate = new Date(dateInput.val());
      let options = { weekday: 'long' };
      let day = selectedDate.toLocaleDateString('en-US', options);

      dayInput.val(day);
    }

    $('#form-addClaimDetail__date').on('change', function() {
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

    $('#form-addClaimDetail__startDate, #form-addClaimDetail__endDate').on('change', function() {
      calculateDuration();
    });

    // Logic tính total hours
    function calculateTotalHours() {
      var fromTime = $('#form-addClaimDetail__fromTime').val();
      var toTime = $('#form-addClaimDetail__toTime').val();

      // Chuyển đổi giá trị từ chuỗi "hh:mm" sang đối tượng Date
      var fromTimeObj = new Date("1970-01-01T" + fromTime + "Z");
      var toTimeObj = new Date("1970-01-01T" + toTime + "Z");

      // Trừ giá trị thời gian để tính tổng số giờ
      var totalHours = (toTimeObj - fromTimeObj) / (1000 * 60 * 60);

      // Làm tròn giá trị tổng số giờ
      totalHours = Math.round(totalHours * 100) / 100;

      // Cập nhật giá trị vào trường "Total Hours"
      $('#form-addClaimDetail__totalHours').val(totalHours);
    }

    $('#form-addClaimDetail__fromTime, #form-addClaimDetail__toTime').on('change', function() {
      calculateTotalHours();
    });

    // Hiển thị audit trail
    $('#saveBtn').on('click', function() {
      alert("Thành công");
      let currentTime = new Date();
      let formattedTime = currentTime.toLocaleString();
      $('#form-addClaimDetail__audit-trail').val("Created by " + creatorName + " on " + formattedTime);
    });
  });
// lưu thông tin nhập
  $(document).ready(function() {
  $('#saveBtn').on('click', function(e) {
    e.preventDefault();

    // Lấy giá trị của các trường nhập liệu
    var status = $('#form-addClaim__status').val();
    var staffId = $('#form-addClaim__staff-id').val();
    var staffName = $('#form-addClaim__staff-name').val();
    var staffDepartment = $('#form-addClaim__staff-department').val();
    var projectName = $('#form-addClaim__project-name').val();
    var startDate = $('#form-addClaimDetail__startDate').val();
    var endDate = $('#form-addClaimDetail__endDate').val();
    var projectDuration = $('#form-addClaim__project-duration').val();
    var claimDetailDate = $('#form-addClaimDetail__date').val();
    var claimDetailDay = $('#form-addClaimDetail__day').val();
    var claimDetailFromTime = $('#form-addClaimDetail__fromTime').val();
    var claimDetailToTime = $('#form-addClaimDetail__toTime').val();
    var claimDetailTotalHours = $('#form-addClaimDetail__totalHours').val();
    var remarks = $('#form-addClaimDetail__remarks').val();
    var auditTrail = $('#form-addClaimDetail__audit-trail').val();

    // Lưu các giá trị vào localStorage hoặc thực hiện các thao tác lưu dữ liệu khác
    localStorage.setItem('status', status);
    localStorage.setItem('staffId', staffId);
    localStorage.setItem('staffName', staffName);
    localStorage.setItem('staffDepartment', staffDepartment);
    localStorage.setItem('projectName', projectName);
    localStorage.setItem('startDate', startDate);
    localStorage.setItem('endDate', endDate);
    localStorage.setItem('projectDuration', projectDuration);
    localStorage.setItem('claimDetailDate', claimDetailDate);
    localStorage.setItem('claimDetailDay', claimDetailDay);
    localStorage.setItem('claimDetailFromTime', claimDetailFromTime);
    localStorage.setItem('claimDetailToTime', claimDetailToTime);
    localStorage.setItem('claimDetailTotalHours', claimDetailTotalHours);
    localStorage.setItem('remarks', remarks);
    localStorage.setItem('auditTrail', auditTrail);

    // Hiển thị thông báo thành công
    alert('Data saved successfully!');
  });
});
  //  bắt validate chọn Date trong khoảng Start DATE VÀ end date
function validateDate() {
  var startDate = $("#form-addClaimDetail__startDate").val();
  var endDate = $("#form-addClaimDetail__endDate").val();
  var dateInput = $("#form-addClaimDetail__date").val();
  var dateValidationMessage = $("#dateValidationMessage");

  if (dateInput < startDate || dateInput > endDate) {
    dateValidationMessage.text("Date must be within the range of Start Date and End Date");
  } else {
    dateValidationMessage.text("");
  }
}