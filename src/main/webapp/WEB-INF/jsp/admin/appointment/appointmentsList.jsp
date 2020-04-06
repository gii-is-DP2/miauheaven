<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="appointments">
    <h2>Appointments</h2>

    <table id="appointmentsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Owner</th>
            <th>Veterinarian</th>
            <th>Date</th>
            <th>Urgent</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
                <td>
                    <c:out value="${appointment.owner.firstName} ${appointment.owner.lastName}"/>
                </td>
                <td>
                    <c:out value="${appointment.vet.firstName} ${appointment.vet.lastName}"/>
                </td>
                 <td>
                    <c:out value="${appointment.date}"/>
                </td>
                <td>
                    <c:out value="${appointment.urgent}"/>
                </td>
                <td>
                <a href='<spring:url value="/admin/appointments/${appointment.id}"/>'>See more</a>
               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
