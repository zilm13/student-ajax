<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
    <div id="navigation">
        <ul>
            <c:set var="splitURI" value="${fn:split(pageContext.request.requestURI, '/')}" />
	        <li <c:if test="${splitURI[3] == 'department'}">class="active"</c:if>>
                <a href="<c:url value='/department/view'/>">Факультеты
                </a>
	        </li>
	        <li <c:if test="${splitURI[3] == 'group'}">class="active"</c:if>>
                <a href="<c:url value='/group/view'/>">Группы
                </a>
	        </li>
	        <li <c:if test="${splitURI[3] == 'subdepartment'}">class="active"</c:if>>
                <a href="<c:url value='/subdepartment/view'/>">Кафедры
                </a>
	        </li>
	        <li <c:if test="${splitURI[3] == 'student'}">class="active"</c:if>>
                <a href="<c:url value='/student/view'/>">Студенты
                </a>
	        </li>
        </ul>
    </div>