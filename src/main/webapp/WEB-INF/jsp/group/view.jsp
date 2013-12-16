<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title><fmt:message key="group.view.title"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/students.css'/>">
</head>
<body>
    <div id="content">
        <h1>
        <c:choose>
        <c:when test="${not empty titleplus}">
        <fmt:message key="group.view.headingplus">
            <fmt:param value="${titleplus}"/>
        </fmt:message>
        </c:when>
        <c:otherwise>
        <fmt:message key="group.view.heading"/>
        </c:otherwise>
        </c:choose>
        </h1>
        <div id="add">
            <fmt:message key="group.view.add"/>
            <input type="button" value="<fmt:message key='group.view.addbutton'/>" onclick="location.href='<c:url value='/group/add/${departmentId}'/>';" />
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
                    <th scope="col"><fmt:message key="group.view.table.title.number"/></th>
                    <th scope="col"><fmt:message key="group.view.table.title.groupname"/></th>
                    <th scope="col"><fmt:message key="group.view.table.title.department"/></th>
                    <th scope="col"><fmt:message key="group.view.table.title.change"/></th>
                    <th scope="col"><fmt:message key="group.view.table.title.delete"/></th>
                </tr>
            </thead>
            <tbody>
              <c:forEach items="${groups}" var="group">
                <tr>
                  <td><c:out value="${group.id}" /></td>
                  <td><c:out value="${group.groupName}" /></td>
                  <td><c:out value="${group.department.departmentName}" /></td>
                  <td><a href="<c:url value='/group/edit/${group.id}'/>"><fmt:message key="group.view.table.change"/></a></td>
                  <td><a href="<c:url value='/group/delete/${group.id}'/>"><fmt:message key="group.view.table.delete"/></a></td>
                </tr>
              </c:forEach>
              </tbody>
        </table>
        <c:if test="${not empty groupsError}">
        <div class="error"><c:out value="${groupsError}" /></div>
        </c:if>
    </div>
</body>
</html>