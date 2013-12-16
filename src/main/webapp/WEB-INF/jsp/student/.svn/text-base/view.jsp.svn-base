<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="student.view.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
        <h1>
        <c:if test="${not empty titleplussub}">
            <fmt:message key="student.view.titleplussub">
                <fmt:param value="${titleplussub}"/>
            </fmt:message>
        </c:if>
        <c:if test="${not empty titleplusgroup}">
            <fmt:message key="student.view.titleplusgroup">
                <fmt:param value="${titleplusgroup}"/>
            </fmt:message>
        </c:if>
        <c:if test="${empty titleplusgroup && empty titleplussub}">
        <fmt:message key="student.view.heading"/>
        </c:if>
        </h1>
        <div id="add">
            <fmt:message key="student.view.add"/>
            <c:if test="${not empty groupId}">
                <input type="button" value="<fmt:message key='student.view.addbutton'/>" onclick="location.href='<c:url value='/student/add/group/${groupId}'/>';" />
            </c:if>
            <c:if test="${not empty subDepartmentId}">
                <input type="button" value="<fmt:message key='student.view.addbutton'/>" onclick="location.href='<c:url value='/student/add/subdep/${subDepartmentId}'/>';" />
            </c:if>
            <c:if test="${empty subDepartmentId && empty groupId}">
            <input type="button" value="<fmt:message key='student.view.addbutton'/>" onclick="location.href='<c:url value='/student/add/'/>';" />
            </c:if>
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
                    <th scope="col"><fmt:message key="student.view.table.title.number"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.group"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.firstname"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.lastname"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.subdepartment"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.ishead"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.change"/></th>
                    <th scope="col"><fmt:message key="student.view.table.title.delete"/></th>
                </tr>
            </thead>
            <tbody>
              <c:forEach items="${students}" var="student">
                <tr>
                  <td><c:out value="${student.id}" /></td>
                  <td><c:out value="${student.group.groupName}" /></td>
                  <td><c:out value="${student.firstName}" /></td>
                  <td><c:out value="${student.lastName}" /></td>
                  <td><c:out value="${student.subDepartment.subDepartmentName}" /></td>
                  <td><c:if test="${student.isHead}"><fmt:message key="student.view.table.ishead"/></c:if></td>
                  <td><a href="<c:url value='/student/edit/${student.id}'/>"><fmt:message key="student.view.table.change"/></a></td>
                  <td><a href="<c:url value='/student/delete/${student.id}'/>"><fmt:message key="student.view.table.delete"/></a></td>
                </tr>
              </c:forEach>
              </tbody>
        </table>
        <c:if test="${not empty studentsError}">
        <div class="error"><c:out value="${studentsError}" /></div>
        </c:if>
    </div>
</body>
</html>