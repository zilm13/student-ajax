<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="group.add.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="group.add.heading"/></h1>
        <form:form id="registration" method="post" commandName="groupForm">
        <ul>
            <li>
                <label for="groupName"><fmt:message key="group.add.form.groupname"/></label>
                <form:input path="groupName"/>
                <p>&nbsp;<form:errors path="groupName"/></p>
            </li>
            <li>
                <label for="department"><fmt:message key="group.add.form.department"/></label>
                <form:select path="department" >
                    <option value=""><fmt:message key="group.add.form.department.choose"/></option>
                  <c:forEach items="${departments}" var="department">
                      <option value="${department.id}" <c:if test="${groupForm.department.id == department.id}">selected</c:if> ><c:out value="${department.departmentName}"/></option>
                  </c:forEach>
                </form:select>
                <p>&nbsp;<form:errors path="department"/></p>
            </li>
            <li class="center">
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='group.add.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='group.add.form.ok'/>" />
                <p>&nbsp;</p>
            </li>
        </ul>
        </form:form>
        <c:if test="${not empty groupError}">
        <div class="error"><c:out value="${groupError}" /></div>
        </c:if>
    </div>
</body>
</html>