$(document).ready(function () {
    $('#detail').click(function () {
        var rowId = $(this).closest('tr').data('rowid');
        var staffName = $(this).closest('tr').find('td:eq(1)').text();
        var projectName = $(this).closest('tr').find('td:eq(2)').text();
        var modalContent =
            `<div class="table-striped">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                                <tr>
                                    <th>Staff name</th>
                                    <td>${staffName}</td>
                                </tr>
                                <tr>
                                    <th>Project Name</th>
                                    <td>${projectName}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>`;
        $('#modal-body-content').html(modalContent);
    });
});