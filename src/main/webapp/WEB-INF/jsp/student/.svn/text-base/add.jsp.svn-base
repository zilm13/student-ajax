<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="student.add.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="student.add.heading"/></h1>
        <c:if test="${empty studentError}">
        <form:form id="registration" method="post" commandName="studentForm">
        <ul>
            <li>
                <label for="firstName"><fmt:message key="student.add.form.firstname"/></label>
                <form:input path="firstName"/>
                <p>&nbsp;<form:errors path="firstName"/></p>
            </li>
            <li>
                <label for="lastName"><fmt:message key="student.add.form.lastname"/></label>
                <form:input path="lastName"/>
                <p>&nbsp;<form:errors path="lastName"/></p>
            </li>
            <li>
                <label for="group"><fmt:message key="student.add.form.group"/></label>
                <form:select path="group" >
                    <option value=""><fmt:message key="student.add.form.group.choose"/></option>
                  <c:forEach items="${groups}" var="group">
                      <option value="${group.id}" <c:if test="${studentForm.group.id == group.id}">selected</c:if> ><c:out value="${group.groupName}"/></option>
                  </c:forEach>
                </form:select>
                <p>&nbsp;<form:errors path="group"/></p>
            </li>
            <li>
                <label for="subDepartment"><fmt:message key="student.add.form.subdepartment"/></label>
                <form:select path="subDepartment" >
                    <option value=""><fmt:message key="student.add.form.subdepartment.choose"/></option>
                  <c:forEach items="${subdepartments}" var="subdepartment">
                      <option value="${subdepartment.id}" <c:if test="${studentForm.subDepartment.id == subdepartment.id}">selected</c:if> ><c:out value="${subdepartment.subDepartmentName}"/></option>
                  </c:forEach>
                </form:select>
                <p>&nbsp;<form:errors path="subDepartment"/></p>
            </li>
            <li>
                <label for="isHead"><fmt:message key="student.add.form.ishead"/></label>
                <div id="head">
                <c:choose>
                <c:when test="${not empty headFound}">
                <form:radiobutton path="forceHead" value="false" />
                <fmt:message key="student.add.form.headfound.leave" >
                    <fmt:param value="${headFound.firstName}"/>
                    <fmt:param value="${headFound.lastName}"/>
                </fmt:message>
                <br/>
                <form:radiobutton path="forceHead" value="true" /><fmt:message key="student.add.form.headfound.force" />
                </c:when>
                <c:otherwise>
                <form:checkbox path="isHead" value="true" />
                </c:otherwise>
                </c:choose>
                </div>
                <p>&nbsp;<form:errors path="isHead"/></p>
            </li>
            <li class="center">
                <input id="cancelForm" type="button" name="cancel" value="<fmt:message key='student.add.form.cancel'/>" onclick="location.href='${cookah.returnPath}';"  />
                <input id="saveForm" type="submit" name="submit" value="<fmt:message key='student.add.form.ok'/>" />
                <p>&nbsp;</p>
            </li>
        </ul>
        </form:form>
        </c:if>
        <c:if test="${not empty studentError}">
            <div class="error"><c:out value="${studentError}" /></div>
        </c:if>
    </div>
</body>
</html>