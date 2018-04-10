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
                    required: "Name is required"
                },
                surname: {
                    required: "Surname is required"
                },
                username: {
                    required: "Username is required"
                },
                password: {
                    required: "Password is required"
                },
                roles: {
                    required: "Role is required"
                },
                pib: {
                    required: "Pib is required"
                },
                identificationNumber: {
                    required: "Identification number is required"
                },
                headquarters: {
                    required: "Headquarters is required"
                },
                file: {
                    required: "Document is required"
                },
                company: {
                    required: "Company is required"
                },
                processId: {
                    required: "Process is required"
                },
                input_document_types: {
                    required: "Input document type is required"
                },
                output_document_types: {
                    required: "Output document types is required"
                }
            }
        });
    });
}();