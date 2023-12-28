$(document).ready(function () {
  const $tableBody = $(".table tbody");

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
          <input class="form-control shadow-none reset startDate" name="workingStartDate" type="date">
        </td>
        <td class="d-flex justify-content-evenly">
        </td>
      </tr>
    `);

    $tableBody.append($newRow);
    toggleDeleteButton();
  }

  $(document).on("click", ".new-record-btn", function () {
    $(this).parent().removeClass();
    let $currentRow = $(this).closest("tr");
    let $buttons = $currentRow.find("td:last").children().detach();

    addNewRow();
    let $startDate = $(".startDate");
    let today = new Date();
    let formattedDate = today.toISOString().substring(0, 10);
    $startDate.val(formattedDate);
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
});
