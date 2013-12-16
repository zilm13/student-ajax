<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="subdepartment.add.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="subdepartment.add.heading"/></h1>
        <form:form id="registration" method="post" commandName="subDepartmentForm">
        <ul>
            <li>
                <label for="subDepartmentName"><fmt:message key="subdepartment.add.form.subdepartmentname"/></label>
                <form:input path="subDepartmentName"/>
                <p>&nbsp;<form:errors path="subDepartmentName"/></p>
            </li>
            <li>
                <label for="department"><fmt:message key="subdepartment.add.form.department"/></label>
                <form:select path="department" >
                    <option value=""><fmt:message key="subdepartment.add.form.department.choose"/></option>
                  <c:forEach items="${departments}" var="department">
                      <option value="${department.id}" <c:if test="${subDepartmentForm.department.id == department.id}">selected</c:if> ><c:out value="${department.departmentName}"/></option>
                  </c:forEach>
                </form:select>
                <p>&nbsp;<form:errors path="department"/></p>
            </li>
            <li class="center">
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='subdepartment.add.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='subdepartment.add.form.ok'/>" />
                <p>&nbsp;</p>
            </li>
        </ul>
        </form:form>
        <c:if test="${not empty subDepartmentError}">
        <div class="error"><c:out value="${subDepartmentError}" /></div>
        </c:if>
    </div>
</body>
</html>