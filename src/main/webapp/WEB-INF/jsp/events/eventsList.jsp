<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="events">
    <h2>Events</h2>

    <table id="eventsTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Name</th>
            <th>Date</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${events}" var="event">
            <tr>
            	<td>
            		<c:out value="${event.name}"/>
                </td>
                <td>
                    <c:out value="${event.date}"/>
                </td>
                <td>
                    <c:out value="${event.animalshelter.name}"/>
                </td>
                <td> 
					<a href="/events/${event.id}">See more</a>
               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/events/new" class="btn btn-default">Create new Event</a>
</petclinic:layout>