$(document).ready(function () {
  let $contentMain = $(this).find("#content-main");
  let $jobRanksData = null;

  function addOptionsToSelectElement($selectElement, data) {
    $selectElement.children().remove();

    $.each(data, function (index, item) {
      $selectElement.append(
        $("<option></option>").val(item.id).text(item.name),
      );
    });
  }

  function loadAndPopulateSelectElements() {
    if ($jobRanksData) {
      populateAllSelectElements();
    } else {
      $.ajax({
        url: "/api/get-jobRank",
        type: "GET",
        dataType: "json",
        success: function (data) {
          $jobRanksData = data.filter(function (item) {
            return item.name !== "PM" && item.name !== "QA";
          });
          populateAllSelectElements();
        },
        error: function (jqXHR, textStatus, errorThrown) {
          console.error("Error fetching job ranks:", textStatus, errorThrown);
        },
      });
    }
  }

  function populateAllSelectElements() {
    $(".jobRankSelect").each(function () {
      addOptionsToSelectElement($(this), $jobRanksData);
    });
  }

  $(function () {
    loadAndPopulateSelectElements();
  });

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
    let newIndex = $tableBody.find("tr").length;
    let $newRow = $(`
    <tr>
      <th scope="row">${newIndex + 1}</th>
      <td>
        <div class="autocomplete-dropdown position-relative">
          <input class="autocomplete-staff-id" type="hidden" name="projectWorkingsById[${newIndex}].workingStaffId">
          <input class="form-control shadow-none reset autocomplete-input"
                 id="form-addProject__workingStaffId-${newIndex + 1}" 
                 placeholder="Enter The Staff Name"
                 type="text"
                 autocomplete="off">
          <div class="dropdown-menu overflow-auto w-100 autocomplete-results position-absolute mt-2"></div>
        </div>
      </td>
      <td>
        <div>
          <select aria-label="Select Job Rank" class="form-select jobRankSelect" 
                  name="projectWorkingsById[${newIndex}].workingJobRankId">
            <!-- Populate options here -->
          </select>
        </div>
      </td>
      <td>
        <div>
          <input class="form-control shadow-none reset startDateRecord" 
                 id="form-addWorking__startDate-${newIndex}" 
                 name="projectWorkingsById[${newIndex}].workingStartDate" 
                 type="date">
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
    $newRow.find("jobRankSelect").each(function () {
      addOptionsToSelectElement($(this), $jobRanksData);
    });
    toggleDeleteButton();
  }

  $(document).on("click", ".new-record-btn", function () {
    $(this).closest("td").removeClass();
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
      setTimeout(function () {
        $input.val($item.text()).prop("disabled", true);
      }, 200);

      $hiddenInput.val($item.data("staff-id"));

      setTimeout(function () {
        isProgrammaticChange = false;
      }, 1000);

      $(".autocomplete-results").removeClass("show").children().remove();
    },
  );

  $(document).on(
    "input",
    ".autocomplete-input",
    debounce(function () {
      if (!isProgrammaticChange) {
        let $input = $(this);
        let $parent = $input.closest(".autocomplete-dropdown");
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
    }, 400),
  );

  $(document).on("click", "td", function () {
    let $input = $(this).find(".autocomplete-input:disabled");
    if ($input) {
      $input.prop("disabled", false).focus();
      $input
        .closest(".autocomplete-dropdown")
        .find(".autocomplete-staff-id")
        .val("");
    }
  });

  $(document).on("click", function (e) {
    let $target = $(e.target);
    let $currentAutocompleteResults = $target
      .closest(".autocomplete-dropdown")
      .find(".autocomplete-results");

    if (
      !$currentAutocompleteResults.length &&
      !$target.is(".autocomplete-input")
    ) {
      $(".autocomplete-results").removeClass("show").children().remove();
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
    submitHandler: function (form) {
      $.ajax({
        type: "POST",
        url: "/project/create",
        data: $(form).serialize(),
        success: function (data) {
          $contentMain.html(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
          console.error("Error loading page:", textStatus, errorThrown);
        },
      });
    },
  });
});
