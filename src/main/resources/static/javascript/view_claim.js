$(document).ready(function () {
    $('#detail').click(function () {
        let staffName = $(this).closest('tr').find('td:eq(1)').text();
        let projectName = $(this).closest('tr').find('td:eq(2)').text();
        let modalContent =
            `<div>
                <table class="table table-striped">
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