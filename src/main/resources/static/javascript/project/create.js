$(document).ready(function () {
  $(".autocomplete-input").attr("autocomplete", "off");
  const $tableBody = $(".table tbody");
  let $projectStartDate = $("#form-addProject__startDate");
  let $projectEndDate = $("#form-addProject__endDate");

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
            <div class="autocomplete-dropdown position-relative">
                <input class="autocomplete-staff-id" name="workingStaffId" type="hidden">
                <input class="form-control shadow-none reset autocomplete-input"
                       id="form-addProject__workingStaffId-01" name="workingStaffName"
                       placeholder="Enter The Staff Name"
                       type="text"
                       autocomplete="off">
                <div class="dropdown-menu overflow-auto w-100 autocomplete-results position-absolute"></div>
            </div>
        </td>
        <td>
        <div>
            <select aria-label="Select Job Rank" class="form-select" name="workingJobRankId">
            <option selected>Open this select menu</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
        </div>
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
      .attr("min", $projectStartDate.val())
      .attr("max", $projectEndDate.val());
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
      $prevRow.find("td:last").append($buttons);
    }
    toggleDeleteButton();
  });
  toggleDeleteButton();

  let isProgrammaticChange = false;

  function debounce(func, wait, immediate) {
    let $timeout;
    return function () {
      let $context = this,
        args = arguments;
      let $later = function () {
        $timeout = null;
        if (!immediate) func.apply($context, args);
      };
      let $callNow = immediate && !$timeout;
      clearTimeout($timeout);
      $timeout = setTimeout($later, wait);
      if ($callNow) func.apply($context, args);
    };
  }

  $(document).on(
    "click",
    ".autocomplete-dropdown .dropdown-item",
    function (e) {
      e.preventDefault();
      let $item = $(this);
      let $dropdown = $item.closest(".autocomplete-dropdown");
      let $input = $dropdown.find(".autocomplete-input");
      let $hiddenInput = $dropdown.find(".autocomplete-staff-id");

      // Set the input value and the hidden input value
      isProgrammaticChange = true;
      $input.val($item.text());
      $hiddenInput.val($item.data("staff-id"));

      setTimeout(function () {
        isProgrammaticChange = false;
      }, 1000);

      $(".autocomplete-results").removeClass("show");
    },
  );

  $(document).on(
    "input",
    ".autocomplete-input",
    debounce(function () {
      if (!isProgrammaticChange) {
        let $input = $(this);
        let $parent = $input.parent(".autocomplete-dropdown");
        let $dropdown = $parent.find(".autocomplete-results");
        let $value = $input.val();

        if ($value.length > 0) {
          let existingValues = [];
          $(".autocomplete-input")
            .not($input)
            .each(function () {
              existingValues.push($(this).val());
            });

          $.ajax({
            url: "/api/search-staff",
            type: "GET",
            data: { query: $value },
            success: function (data) {
              $dropdown.children().remove();
              $.each(data, function (index, staff) {
                if ($.inArray(staff.name, existingValues) === -1) {
                  $dropdown.append(
                    `<a class="dropdown-item" href="#" data-staff-id="${staff.id}">${staff.name}</a>`,
                  );
                }
              });
              $dropdown.toggleClass("show", $dropdown.children().length > 0);
            },
            error: function (jqXHR, textStatus, errorThrown) {
              console.error("Error fetching staff:", textStatus, errorThrown);
              $dropdown.empty();
              $dropdown.removeClass("show");
            },
          });
        } else {
          $dropdown.removeClass("show");
        }
      }
    }, 250),
  );

  $(document).on("click", function (e) {
    let $target = $(e.target);
    let $currentAutocompleteResults = $target
      .closest(".autocomplete-dropdown")
      .find(".autocomplete-results");

    if (
      !$currentAutocompleteResults.length &&
      !$target.is(".autocomplete-input")
    ) {
      // Close all dropdowns using the cached selector
      $(".autocomplete-results").removeClass("show");
    }
  });

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
        maxlength: 20,
        required: true,
      },
      projectName: {
        minlength: 3,
        required: true,
      },
      projectStartDate: {
        required: true,
      },
      projectEndDate: {
        required: true,
      },
      workingStaffName: {
        required: true,
      },
      workingJobRank: {
        required: true,
      },
      workingStartDate: {
        required: true,
      },
    },
    messages: {
      projectCode: {
        minlength: "Project Code must be at least 3 characters",
        maxlength: "Project Code must be less than 20 characters",
        required: "Please enter Project Code",
      },
      projectName: {
        minlength: "Project Name must be at least 3 characters",
        required: "Please enter Project Name",
      },
      projectStartDate: {
        required: "Please enter Project Start Date",
      },
      projectEndDate: {
        required: "Please enter Project End Date",
      },
      workingStaffName: {
        required: "Please enter Staff Name",
      },
      workingJobRank: {
        required: "Please enter Working Job Rank",
      },
      workingStartDate: {
        required: "Please enter Working Start Date",
      },
    },
    submitHandler: function (form) {},
  });
});
