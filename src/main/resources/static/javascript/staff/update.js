$(document).ready(function () {

    $("#updateStaffForm").validate({
        errorPlacement: function (error, element) {
            error.insertBefore(element);
        },

        rules: {
            name: {
                required: true,
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

    $.validator.methods.isNumber = function (value,element){
        return !isNaN(value);
    }

    $.validator.methods.isPositive = function (value,element){
        return value > 0;
    }

});