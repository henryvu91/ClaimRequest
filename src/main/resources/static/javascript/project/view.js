$(document).ready(function () {
  let contentMain = $("#content-main");
  let totalPagesAttr = $("#view-project__totalPages").attr("data-totalPages");
  let pageNumberAttr = $("#view-project__pageNo").attr("data-pageNo");

  let getTotalPages = totalPagesAttr ? parseInt(totalPagesAttr, 10) : 1;
  let getPageNumber = pageNumberAttr ? parseInt(pageNumberAttr, 10) + 1 : 1;

  $(document)
    .off("click")
    .on("click", ".btn-delete-projectById", function () {
      let recordId = $(this).data("id");
      if (confirm("Are you sure you want to delete this record?")) {
        $.ajax({
          url: "/project/delete",
          type: "POST",
          data: { id: recordId },
          success: function (data) {
            window.location.reload();
          },
          error: function (xhr, status, error) {
            alert("Error: " + error.message);
          },
        });
      }
    });
});
