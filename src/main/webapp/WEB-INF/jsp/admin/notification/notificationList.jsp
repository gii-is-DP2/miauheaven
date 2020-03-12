<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="notificationList">
      <h2>Notifications created</h2>

	 <a href='<spring:url value="/admin/notification/new"/>'>Create new notification</a>
    <table id="notificationsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Title</th>
            <th>Target</th>
            <th>Date</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${notifications}" var="notification">
            <tr>
                <td>
                    <c:out value="${notification.title}"/>
                </td>
                <td>
                    <c:out value="${notification.target}"/>
                </td>
                 <td>
                    <c:out value="${notification.date}"/>
                </td>
                <td>
                <a href='<spring:url value="/admin/notification/${notification.id}"/>'>See more</a>
               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
