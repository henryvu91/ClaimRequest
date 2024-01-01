$(document).ready(function () {
  let contentMain = $("#content-main");
  let totalPagesAttr = $("#view-project__totalPages").attr("data-totalPages");
  let pageNumberAttr = $("#view-project__pageNo").attr("data-pageNo");

  let getTotalPages = totalPagesAttr ? parseInt(totalPagesAttr, 10) : 1;
  let getPageNumber = pageNumberAttr ? parseInt(pageNumberAttr, 10) + 1 : 1;

  function loadData() {
    $("#set__pageNo").val(getPageNumber - 1);
    $.ajax({
      type: "GET",
      url: "/project/view",
      data: $("#form-viewProject").serialize(),
      success: function (data) {
        contentMain.html(data);
      },
      error: function (jqXHR, textStatus, errorThrown) {
        console.error("Error loading page:", textStatus, errorThrown);
      },
    });
  }

  $("#pagination__project").twbsPagination({
    totalPages: getTotalPages,
    startPage: getPageNumber,
    visiblePages: 5,
    first: false,
    last: false,
    onPageClick: function (event, page) {
      event.preventDefault();
      $("#set__pageNo").val(page - 1);
      if (getPageNumber != page) {
        $.ajax({
          type: "GET",
          url: "/project/view",
          data: $("#form-viewProject").serialize(),
          success: function (data) {
            contentMain.html(data);
          },
          error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error loading page:", textStatus, errorThrown);
          },
        });
      }
    },
  });

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
            loadData();
          },
          error: function (xhr, status, error) {
            alert("Error: " + error.message);
          },
        });
      }
    });
});
