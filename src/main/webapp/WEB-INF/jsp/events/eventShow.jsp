<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="event">

    <h2>Event Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${event.name}"/></b></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><c:out value="${event.date}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${event.description}"/></td>
        </tr>
    </table>
    <spring:url value="{eventId}/edit" var="editUrl">
		<spring:param name="eventId" value="${event.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Event</a>
</petclinic:layout>