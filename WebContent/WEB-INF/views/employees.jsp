
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #f4f7fb;
            color: #243047;
        }

        .page {
            max-width: 1050px;
            margin: 45px auto;
            padding: 0 22px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 20px;
            margin-bottom: 25px;
        }

        .header h1 {
            margin: 0 0 8px;
            font-size: 28px;
        }

        .subtitle {
            margin: 0;
            color: #718096;
            font-size: 14px;
        }

        button {
            border: 0;
            border-radius: 7px;
            padding: 11px 16px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s, transform 0.1s;
        }

        button:active {
            transform: scale(0.98);
        }

        .primary-btn {
            background: #315ee7;
            color: white;
        }

        .primary-btn:hover {
            background: #244bc2;
        }

        .card {
            background: white;
            border: 1px solid #e5eaf2;
            border-radius: 12px;
            box-shadow: 0 5px 18px rgba(30, 50, 90, 0.05);
            overflow: hidden;
        }

        .card-heading {
            padding: 20px 24px;
            border-bottom: 1px solid #edf0f5;
            font-weight: 700;
        }

        .table-wrap {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 16px 24px;
            text-align: left;
            border-bottom: 1px solid #edf0f5;
            font-size: 14px;
        }

        th {
            color: #718096;
            background: #fafbfe;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        tbody tr:hover {
            background: #f8faff;
        }

        tbody tr:last-child td {
            border-bottom: 0;
        }

        .employee-id {
            color: #64748b;
            font-weight: 600;
        }

        .designation {
            display: inline-block;
            padding: 6px 10px;
            border-radius: 20px;
            background: #eef2ff;
            color: #4256a6;
            font-size: 12px;
        }

        .delete-btn {
            background: #fff1f1;
            color: #c53030;
            padding: 8px 12px;
        }

        .delete-btn:hover {
            background: #ffe0e0;
        }

        .empty-state {
            padding: 38px 20px;
            text-align: center;
            color: #718096;
        }

        /* Notification */
        .toast {
            display: none;
            margin-bottom: 18px;
            padding: 13px 16px;
            border-radius: 8px;
            font-size: 14px;
        }

        .toast.success {
            display: block;
            color: #176b3a;
            background: #e9f8ef;
            border: 1px solid #bce8ca;
        }

        .toast.error {
            display: block;
            color: #a52828;
            background: #fff0f0;
            border: 1px solid #f3c1c1;
        }

        /* Modal backdrop and dialog */
        .modal-backdrop {
            display: none;
            position: fixed;
            inset: 0;
            z-index: 10;
            background: rgba(19, 29, 48, 0.5);
            padding: 20px;
            align-items: center;
            justify-content: center;
        }

        .modal-backdrop.open {
            display: flex;
        }

        .modal {
            width: 100%;
            max-width: 440px;
            background: white;
            border-radius: 13px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            animation: appear 0.16s ease-out;
        }

        @keyframes appear {
            from {
                opacity: 0;
                transform: translateY(8px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 24px;
            border-bottom: 1px solid #edf0f5;
        }

        .modal-header h2 {
            margin: 0;
            font-size: 20px;
        }

        .close-btn {
            background: transparent;
            color: #718096;
            font-size: 23px;
            padding: 2px 8px;
        }

        .modal-body {
            padding: 22px 24px;
        }

        .field {
            margin-bottom: 17px;
        }

        .field label {
            display: block;
            margin-bottom: 7px;
            font-size: 13px;
            font-weight: 600;
            color: #46536a;
        }

        .field input {
            width: 100%;
            padding: 11px 12px;
            border: 1px solid #cfd7e4;
            border-radius: 7px;
            outline: none;
            font-size: 14px;
        }

        .field input:focus {
            border-color: #315ee7;
            box-shadow: 0 0 0 3px rgba(49, 94, 231, 0.12);
        }

        .modal-actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            padding-top: 8px;
        }

        .secondary-btn {
            background: #edf0f5;
            color: #46536a;
        }

        .secondary-btn:hover {
            background: #dfe5ee;
        }

        .delete-modal-body {
            padding: 24px;
        }

        .delete-modal-body p {
            line-height: 1.5;
            color: #64748b;
        }

        .delete-modal-body strong {
            color: #243047;
        }

        @media (max-width: 600px) {
            .page {
                margin: 25px auto;
                padding: 0 12px;
            }

            .header {
                align-items: flex-start;
                flex-direction: column;
            }

            th, td {
                padding: 13px 14px;
            }
        }
    </style>
</head>

<body>

<div class="page">

    <div class="header">
        <div>
            <h1>Employee Management</h1>
            <p class="subtitle">View and manage your employee records.</p>
        </div>

        <button class="primary-btn" type="button" onclick="openAddModal()">
            + Add Employee
        </button>
    </div>

    <div id="toast" class="toast" role="status"></div>

    <div class="card">
        <div class="card-heading">Employee List</div>

        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>Name</th>
                        <th>Designation</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>
                    <c:choose>
                        <c:when test="${not empty employees}">
                            <c:forEach var="employee" items="${employees}">
                                <tr>
                                    <td class="employee-id">
                                        <c:out value="${employee.empId}" />
                                    </td>
                                    <td>
                                        <c:out value="${employee.name}" />
                                    </td>
                                    <td>
                                        <span class="designation">
                                            <c:out value="${employee.designation}" />
                                        </span>
                                    </td>
                                    <td>
                                        <button
                                            class="delete-btn"
                                            type="button"
                                            onclick="openDeleteModal(
                                                '${employee.empId}',
                                                this
                                            )">
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="empty-state">
                                    No employees have been added yet.
                                    Click <strong>Add Employee</strong> to get started.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

</div>

<!-- Add Employee modal -->
<div id="addModal" class="modal-backdrop">
    <div class="modal" role="dialog" aria-modal="true" aria-labelledby="addTitle">

        <div class="modal-header">
            <h2 id="addTitle">Add Employee</h2>
            <button class="close-btn" type="button"
                    aria-label="Close" onclick="closeAddModal()">&times;</button>
        </div>

        <div class="modal-body">
            <form id="employeeForm">

                <div class="field">
                    <label for="empId">Employee ID</label>
                    <input id="empId" type="number" name="empId"
                           min="1" placeholder="Enter employee ID" required>
                </div>

                <div class="field">
                    <label for="empName">Full Name</label>
                    <input id="empName" type="text" name="name"
                           maxlength="100" placeholder="Enter employee name" required>
                </div>

                <div class="field">
                    <label for="empDesignation">Designation</label>
                    <input id="empDesignation" type="text" name="designation"
                           maxlength="100" placeholder="e.g. Java Developer" required>
                </div>

                <div class="modal-actions">
                    <button class="secondary-btn" type="button"
                            onclick="closeAddModal()">Cancel</button>

                    <button id="saveBtn" class="primary-btn" type="submit">
                        Save Employee
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>

<!-- Delete confirmation modal -->
<div id="deleteModal" class="modal-backdrop">
    <div class="modal" role="dialog" aria-modal="true"
         aria-labelledby="deleteTitle">

        <div class="modal-header">
            <h2 id="deleteTitle">Delete Employee?</h2>
            <button class="close-btn" type="button"
                    aria-label="Close" onclick="closeDeleteModal()">&times;</button>
        </div>

        <div class="delete-modal-body">
            <p>
                Are you sure you want to delete employee
                <strong id="deleteEmployeeId"></strong>?
                This action cannot be undone.
            </p>

            <div class="modal-actions">
                <button class="secondary-btn" type="button"
                        onclick="closeDeleteModal()">Cancel</button>

                <button id="confirmDeleteBtn" class="delete-btn" type="button"
                        onclick="confirmDelete()">Delete Employee</button>
            </div>
        </div>
    </div>
</div>

<script>
    const saveUrl = '<c:url value="/employee/ajax-save" />';
    const deleteUrlBase = '<c:url value="/employee/ajax-delete" />';

    let employeeIdToDelete = null;
    let deleteButtonToRestore = null;

    function showToast(message, type) {
        const toast = document.getElementById("toast");
        toast.textContent = message;
        toast.className = "toast " + type;

        // Hide the notification after a few seconds
        window.setTimeout(function() {
            toast.className = "toast";
            toast.textContent = "";
        }, 4500);
    }

    function openAddModal() {
        document.getElementById("employeeForm").reset();
        document.getElementById("addModal").classList.add("open");
        document.getElementById("empId").focus();
    }

    function closeAddModal() {
        document.getElementById("addModal").classList.remove("open");
    }

    document.getElementById("employeeForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const saveBtn = document.getElementById("saveBtn");
        saveBtn.disabled = true;
        saveBtn.textContent = "Saving...";

        const formData = new URLSearchParams(new FormData(this));

        fetch(saveUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Server returned an error.");
            }
            return response.text();
        })
        .then(result => {
            if (result.trim() === "OK") {
                closeAddModal();
                showToast("Employee added successfully.", "success");

                // Fetch the latest employee list from the database
                window.location.reload();
            } else {
                showToast(result.replace("ERROR: ", ""), "error");
            }
        })
        .catch(() => {
            showToast("Could not save the employee. Please try again.", "error");
        })
        .finally(() => {
            saveBtn.disabled = false;
            saveBtn.textContent = "Save Employee";
        });
    });

    function openDeleteModal(empId, button) {
        employeeIdToDelete = empId;
        deleteButtonToRestore = button;

        document.getElementById("deleteEmployeeId").textContent = "ID " + empId;
        document.getElementById("deleteModal").classList.add("open");
    }

    function closeDeleteModal() {
        document.getElementById("deleteModal").classList.remove("open");
        employeeIdToDelete = null;
        deleteButtonToRestore = null;
    }

    function confirmDelete() {
        if (employeeIdToDelete === null) {
            return;
        }

        const deleteBtn = document.getElementById("confirmDeleteBtn");
        deleteBtn.disabled = true;
        deleteBtn.textContent = "Deleting...";

        fetch(deleteUrlBase + "/" + encodeURIComponent(employeeIdToDelete), {
            method: "POST"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Server returned an error.");
            }
            return response.text();
        })
        .then(result => {
            if (result.trim() === "OK") {
                closeDeleteModal();
                showToast("Employee deleted successfully.", "success");

                // Reloads the list with the updated database records
                window.location.reload();
            } else {
                closeDeleteModal();
                showToast(result.replace("ERROR: ", ""), "error");
            }
        })
        .catch(() => {
            closeDeleteModal();
            showToast("Could not delete the employee. Please try again.", "error");
        })
        .finally(() => {
            deleteBtn.disabled = false;
            deleteBtn.textContent = "Delete Employee";
        });
    }

    // Close a popup if the user clicks the dark area outside it
    document.querySelectorAll(".modal-backdrop").forEach(backdrop => {
        backdrop.addEventListener("click", function(event) {
            if (event.target === backdrop) {
                backdrop.classList.remove("open");
            }
        });
    });

    // Close popups with the Escape key
    document.addEventListener("keydown", function(event) {
        if (event.key === "Escape") {
            closeAddModal();
            closeDeleteModal();
        }
    });
</script>

</body>
</html>
