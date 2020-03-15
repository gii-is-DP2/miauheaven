<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Questionnaire Show">

    <h2>Questionnaire for <c:out value="${questionnaire.pet.name}"></c:out></h2>
	
	
    <table class="table table-striped">
        
        <tr>
            <th>Pet name</th>
            <td><c:out value="${questionnaire.pet.name}"/></td>
        </tr>
        <tr>
            <th>Owner</th>
            <td><b><c:out value="${questionnaire.owner.firstName} ${questionnaire.owner.lastName}"/></b></td> 
        </tr>
        <tr>
            <th>Income</th>
            <td><c:out value="${questionnaire.ingresos}"/></td>
        </tr>
        <tr>
            <th>Free Time</th>
            <td><c:out value="${questionnaire.horasLibres}"/></td>
        </tr>
        <tr>
            <th>Coexistence</th>
            <td><c:out value="${questionnaire.convivencia}"/></td>
        </tr>
        <tr>
            <th>Score</th>
            <td><c:out value="${questionnaire.puntuacion}"/></td>
        </tr>
    </table>
    <a class="btn btn-default" href='<spring:url value="/owners/adoptList/questionnaire/accept/${questionnaire.id}" ></spring:url>'>Accept adoption</a>
</petclinic:layout>
