
<!DOCTYPE html>
<html>
<head>
    <title>Employee Validation</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: inline-block;
            width: 130px;
        }

        input {
            padding: 8px;
            width: 220px;
        }

        .error {
            color: red;
            font-size: 13px;
            display: block;
            margin-left: 134px;
            margin-top: 5px;
        }

        .success {
            color: green;
            margin-top: 15px;
        }

        button {
            padding: 8px 18px;
            cursor: pointer;
        }
    </style>
</head>

<body>

    <h2>Employee Validation Form</h2>

    <form id="employeeForm"
          action="${pageContext.request.contextPath}/employee/validation"
          method="post"
          novalidate>

        <div class="form-group">
            <label for="empName">Employee Name:</label>
            <input type="text" id="empName" name="empName">
            <span class="error" id="nameError"></span>
        </div>

        <div class="form-group">
            <label for="city">City:</label>
            <input type="text" id="city" name="city">
            <span class="error" id="cityError"></span>
        </div>

        <button type="submit">Validate</button>

        <p id="successMessage" class="success">
            ${successMessage}
        </p>

        <p class="error">${errorMessage}</p>

    </form>

    <script>
        const form = document.getElementById("employeeForm");

        form.addEventListener("submit", function(event) {

            event.preventDefault();

            const name =
                document.getElementById("empName").value.trim();

            const city =
                document.getElementById("city").value.trim();

            const pattern = /^[A-Za-z ]+$/;

            let valid = true;

            document.getElementById("nameError").textContent = "";
            document.getElementById("cityError").textContent = "";
            document.getElementById("successMessage").textContent = "";

            if (name === "") {
                document.getElementById("nameError").textContent =
                    "Employee name is required";
                valid = false;
            } else if (!pattern.test(name)) {
                document.getElementById("nameError").textContent =
                    "Enter letters only";
                valid = false;
            }

            if (city === "") {
                document.getElementById("cityError").textContent =
                    "City is required";
                valid = false;
            } else if (!pattern.test(city)) {
                document.getElementById("cityError").textContent =
                    "Enter letters only";
                valid = false;
            }

            if (valid) {
                form.submit();
            }
        });
    </script>

</body>
</html>
