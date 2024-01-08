$(document).ready(function () {
    let contentMain = $("#content-main");
    let totalPagesAttr = $("#view-project__totalPages").attr("data-totalPages");
    let pageNumberAttr = $("#view-project__pageNo").attr("data-pageNo");

    let getTotalPages = totalPagesAttr ? parseInt(totalPagesAttr, 10) : 1;
    let getPageNumber = pageNumberAttr ? parseInt(pageNumberAttr, 10) + 1 : 1;

    $(document).off("click").on("click", ".btn-delete-projectById", function () {
        let recordId = $(this).data("id");
        let $modal = $('#modalBootstrap');
        let contentPopup = $('<div>This action will Delete Project.</div>' +
            '<div>Please click ‘OK’ to delete the project or ‘Cancel’ to close the dialog.</div>');

        $modal.find('.modal-header').find('h1').text('Delete Project');
        $modal.find('.modal-body').html(contentPopup);
        $('#btn-okPopup').attr("data-id", recordId);
    })

    $(document).on("click", ".btn-cancelPopups", function () {
        clearModal();
    })

    $(document)
        .on("click", ".btn-okPopups", function () {
            let recordId = $(this).data("id");
            let title = 'Delete Project';
            $.ajax({
                url: "/api/project/delete",
                type: "POST",
                data: {id: recordId},
                success: function (data) {
                    sessionStorage.setItem('toastMessage', JSON.stringify({message: data.message, title: title}));
                    window.location.reload();
                },
                error: function (xhr, status, error) {
                    sessionStorage.setItem('toastMessage', JSON.stringify({message: error.message, title: title}));
                    window.location.reload();
                    clearModal();
                },
            });
        });


});
