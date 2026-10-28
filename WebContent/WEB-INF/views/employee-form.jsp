
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${formTitle}</title>
</head>
<body>

    <h2>${formTitle}</h2>

    <form action="<c:url value='/employee/${formAction}' />" method="post">

        <label>Employee ID:</label>
        <input type="number"
               name="empId"
               value="${employee.empId}"
               required
               <c:if test="${formAction == 'update'}">readonly</c:if>>
        <br><br>

        <label>Name:</label>
        <input type="text"
               name="name"
               value="<c:out value='${employee.name}' />"
               required>
        <br><br>

        <label>Designation:</label>
        <input type="text"
               name="designation"
               value="<c:out value='${employee.designation}' />"
               required>
        <br><br>

        <button type="submit">Save</button>
        <a href="<c:url value='/employees' />">Cancel</a>

    </form>

</body>
</html>