$(document).ready(function () {
  const $tableBody = $(".table tbody");
  let $projectStartDate = $("#form-addProject__startDate");
  let $today = new Date();
  let $formattedDate = $today.toISOString().substring(0, 10);

  function toggleDeleteButton() {
    $tableBody
      .find(".delete-record-btn")
      .toggle($tableBody.find("tr").length > 3);
  }

  function addNewRow() {
    let newIndex = $tableBody.find("tr").length + 1;
    let $newRow = $(`
      <tr>
        <th scope="row">${newIndex}</th>
        <td>
          <select aria-label="Select Name" class="form-select" name="workingName">
            <option selected>Open this select menu</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
        </td>
        <td>
          <select aria-label="Select Job Rank" class="form-select" name="workingJobRank">
            <option selected>Open this select menu</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
        </td>
        <td>
        <div>
          <label class="form-label" for="form-addWorking__startDate-0${newIndex}" hidden="hidden"></label>
          <input class="form-control shadow-none reset startDateRecord" id="form-addWorking__startDate-0${newIndex}" name="workingStartDate" type="date">
        </div>
        </td>
        <td>
        </td>
      </tr>
    `);

    $tableBody.append($newRow);
    $newRow
      .find('input[type="date"]')
      .val($formattedDate)
      .attr("min", $projectStartDate.val());
    toggleDeleteButton();
  }

  $(document).on("click", ".new-record-btn", function () {
    $(this).parent().removeClass();
    let $currentRow = $(this).closest("tr");
    let $buttons = $currentRow.find("td:last").children().detach();

    addNewRow();
    $tableBody.find("tr:last td:last").append($buttons);
    toggleDeleteButton();
  });
  $(document).on("click", ".delete-record-btn", function () {
    const $currentRow = $(this).closest("tr");
    const $prevRow = $currentRow.prev("tr");
    let $buttons = $currentRow.find("td:last").children().detach();
    $currentRow.remove();
    if ($prevRow.length) {
      $prevRow
        .find("td:last")
        .append($buttons)
        .addClass("d-flex justify-content-evenly");
    }
    toggleDeleteButton();
  });
  toggleDeleteButton();

  $("#form-addProject").validate({
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
      $(element).addClass(validClass).removeClass(errorClass);
    },
    rules: {
      projectCode: {
        minlength: 3,
        maxLength: 20,
        required: true,
      },
      projectName: {
        required: true,
      },
      projectStartDate: {
        required: true,
      },
      projectEndDate: {
        greaterThan: "#form-addProject__startDate",
        required: true,
      },
      workingName: {
        required: true,
      },
      workingJobRank: {
        required: true,
      },
      workingStartDate: {
        required: true,
        greaterThan: "#form-addProject__startDate",
      },
    },
    messages: {
      projectCode: {
        minlength: "Project Code must be at least 3 characters",
        maxLength: "Project Code must be less than 20 characters",
        required: "Please enter Project Code",
      },
      projectName: {
        required: "Please enter Project Name",
      },
      projectStartDate: {
        required: "Please enter Project Start Date",
      },
      projectEndDate: {
        greaterThanEqual:
          "Project End Date must be greater than Project Start Date",
        required: "Please enter Project End Date",
      },
      workingName: {
        required: "Please enter Working Name",
      },
      workingJobRank: {
        required: "Please enter Working Job Rank",
      },
      workingStartDate: {
        required: "Please enter Working Start Date",
        greaterThanEqual:
          "Working Start Date must be greater than Project Start Date",
      },
    },
    submitHandler: function (form) {
      form.submit();
    },
  });
});
