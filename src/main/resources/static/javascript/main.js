$(document).ready(function () {
    $("#btn-userProfile").click(function (e) {
        e.preventDefault();
        //include profile.html file into index.html
        contentMain.children().remove();
        contentMain.load("profile/update");
    });

    $("#btn-logout").click(function (e) {
        e.preventDefault();
        window.location.href = "login";
    });

    $("#form-login__username").change(function () {
        $("#form-login__message").children().remove();
    });
    $("#form-login__password").change(function () {
        $("#form-login__message").children().remove();
    });


    $("#form-register").validate({
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
            $(element).removeClass(errorClass).addClass(validClass);
        },
        rules: {
            username: {
                required: true,
                minLength: 5,
                maxlength: 50,
            },
            email: {
                email: true,
                required: true,
                maxlength: 50,
            },
            password: {
                required: true,
                minLength: 5,
                maxlength: 50,
            },
            confirmPassword: {
                required: true,
                maxlength: 50,
                equalTo: "#form-register__password",
            },
        },
        messages: {
            username: {
                required: "Please enter username",
                minlength: "Username must be at least 5 characters",
                maxlength: "Username must be less than 50 characters",
            },
            email: {
                required: "Please enter email",
                email: "Please enter valid email",
                minlength: "Email must be at least 5 characters",
                maxlength: "Email must be less than 50 characters",
            },
            password: {
                required: "Please enter password",
                minlength: "Password must be at least 5 characters",
                maxlength: "Password must be less than 50 characters",
            },
            confirmPassword: {
                required: "Please enter confirm password",
                maxlength: "Confirm password must be less than 50 characters",
                equalTo: "Password and confirm password does not match",
            },
        },
        submitHandler: function (form) {
            form.submit();
        },
    });

    $("#form-login").validate({
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
            $(element).removeClass(errorClass).addClass(validClass);
        },
        rules: {
            username: {
                required: true,
            },
            password: {
                required: true,
            },
        },
        messages: {
            username: {
                required: "Please enter username",
            },
            password: {
                required: "Please enter password",
            },
        },
        submitHandler: function (form) {
            form.submit();
        },
    });

    $(".resetDataForm").click(function () {
        let form = $(this).closest("form");

        form.find(".reset").val("");

        form.find(".is-invalid").removeClass("is-invalid");
        form.find(".is-valid").removeClass("is-valid");
        form.find(".invalid-feedback").remove();

        form.validate().resetForm();

        return false;
    });
});
