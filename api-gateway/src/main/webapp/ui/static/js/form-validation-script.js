var Script = function () {
    $().ready(function () {
        $("#register_form").validate({
            rules: {
                name: {
                    required: true
                },
                surname: {
                    required: true
                },
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                pib: {
                    required: true
                },
                identificationNumber: {
                    required: true
                },
                headquarters: {
                    required: true
                },
                file: {
                    required: true
                },
                company: {
                    required: true
                },
                processId: {
                    required: true
                },
                roles: "required",
                input_document_types: "required",
                ouput_document_types: "required"
            },
            messages: {
                name: {
                    required: "Please enter a name."
                },
                surname: {
                    required: "Please enter a surname."
                },
                username: {
                    required: "Please enter a username."
                },
                password: {
                    required: "Please provide a password."
                },
                roles: {
                    required: "Please select a role."
                },
                pib: {
                    required: "Please provide a pib."
                },
                identificationNumber: {
                    required: "Please provide an identification number."
                },
                headquarters: {
                    required: "Please provide headquarters."
                },
                file: {
                    required: "Please provide document."
                },
                company: {
                    required: "Please provide a company."
                },
                processId: {
                    required: "Please provide a primitive process."
                },
                input_document_types: {
                    required: "Please provide document types for input."
                },
                output_document_types: {
                    required: "Please provide document types for output."
                }
            }
        });
    });
}();