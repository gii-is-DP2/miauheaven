<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="appointments">

    <h2>Appointment Information</h2>
	
	
    <table class="table table-striped">
        <tr>
            <th>Owner</th>
            <td><b><c:out value="${appointment.owner.firstName} ${appointment.owner.lastName}"/></b></td> 
        </tr>
        <tr>
            <th>Pet name</th>
            <td><c:out value="${appointment.pet.name}"/></td>
        </tr>
        <tr>
        <tr>
            <th>Pet type</th>
            <td><c:out value="${appointment.pet.type}"/></td>
        </tr>
        <tr>
            <th>Veterinarian</th>
            <td><c:out value="${appointment.vet.firstName} ${appointment.vet.lastName}"/></td>
        </tr>
        <tr>
            <th>Urgent</th>
            <td><c:out value="${appointment.urgent}"/></td>
        </tr>
        <tr>
            <th>Cause</th>
            <td><c:out value="${appointment.cause}"/></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><c:out value="${appointment.date}"/></td>
        </tr>
    </table>
</petclinic:layout>
