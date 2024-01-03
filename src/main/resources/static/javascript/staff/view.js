$(document).ready(function () {
    const nameStaffField = $("#form-viewStaff__name");

    // load the detail in firstTime
    loadDetail(nameStaffField.val());

    //     add event change in name
    nameStaffField.change(function () {
        let idStaff = this.value;
        loadDetail(idStaff);
    });

    function loadDetail(staffId) {
        $.ajax({
                url:"/staff/viewDetail?id="+staffId,
                success:function (result) {
                    $("#viewDetail").html(result)
                }
            }

        )
    }
});