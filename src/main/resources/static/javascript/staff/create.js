$(document).ready(function () {

    $("#createStaffForm").validate({
        errorPlacement: function (error, element) {
            error.insertBefore(element);
        },

        rules: {
            name: {
                required: true,
            },

            email: {
                required: true,
                email: true,
            },

            password:{
                required: true,
                password:true,
            },
            departmentId:{
                required:true
            },

            roleId:{
                required:true
            },

            salary:{
                required:true,
                isNumber:true,
                isPositive:true
            }
        },
        messages: {
            name: {
                required: "Name must be filled",

            },

            email: {
                required: "Email must be filled",
                email: "Email is invalid"
            },

            password: {
                required: "Password must be filled",
                password: "Password does not accept space",
            },

            departmentId:{
                required: "Department must be filled"
            },

            roleId:{
                required:"Role must be filled"
            },

            salary:{
                required:"Salary must be filled",
                isNumber:"Salary must be number",
                isPositive:"Salary must be positive"
            }
        },

        submitHandler: function (form) {
            form.submit();
        },
    });

    $.validator.methods.password = function (value, element) {
        let passwordReg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
        return passwordReg.test(value);
    };

    $.validator.methods.isNumber = function (value,element){
        return !isNaN(value);
    }

    $.validator.methods.isPositive = function (value,element){
        return value > 0;
    }
});