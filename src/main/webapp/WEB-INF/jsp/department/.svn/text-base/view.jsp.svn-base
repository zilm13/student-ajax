<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="department.view.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>" />
</head>
<body>
    <div id="content">
        <h1><fmt:message key="department.view.heading"/></h1>
        <div id="add">
            <fmt:message key="department.view.add"/>
            <input type="button" value="<fmt:message key='department.view.addbutton'/>" onclick="location.href='<c:url value='/department/add'/>';" />
        </div>
        <c:if test="${not empty cookah.greenMessage}">
            <div id ="greenmessage"><c:out value="${cookah.greenMessage}" /></div>
            <c:set target="${cookah}" property="greenMessage" value=""/>
        </c:if>
        <c:if test="${not empty cookah.redMessage}">
            <div id ="redmessage"><c:out value="${cookah.redMessage}" /></div>
            <c:set target="${cookah}" property="redMessage" value=""/>
        </c:if>
        <table id="gradient">
            <thead>
                <tr>
                    <th scope="col"><fmt:message key="department.view.table.title.number"/></th>
                    <th scope="col"><fmt:message key="department.view.table.title.departmentname"/></th>
                    <th scope="col"><fmt:message key="department.view.table.title.deanfirstname"/></th>
                    <th scope="col"><fmt:message key="department.view.table.title.deanlastname"/></th>
                    <th scope="col"><fmt:message key="department.view.table.title.change"/></th>
                    <th scope="col"><fmt:message key="department.view.table.title.delete"/></th>
                </tr>
            </thead>
            <tbody>
              <c:forEach items="${departments}" var="department">
                <tr>
                  <td><c:out value="${department.id}" /></td>
                  <td><c:out value="${department.departmentName}" /></td>
                  <td><c:out value="${department.deanFirstName}" /></td>
                  <td><c:out value="${department.deanLastName}" /></td>
                  <td><a href="<c:url value='/department/edit/${department.id}'/>"><fmt:message key="department.view.table.change"/></a></td>
                  <td><a href="<c:url value='/department/delete/${department.id}'/>"><fmt:message key="department.view.table.delete"/></a></td>
                </tr>
              </c:forEach>
              </tbody>
        </table>
        <c:if test="${not empty departmentsError}">
        <div class="error"><c:out value="${departmentsError}" /></div>
        </c:if>
    </div>
</body>
</html>