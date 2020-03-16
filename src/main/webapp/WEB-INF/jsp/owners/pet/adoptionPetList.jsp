<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="adoptionPetList">
	<h2>Adoption Pet List</h2>

	<table id="adoptionPetListTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 25%">Name</th>
				<th style="width: 25%">Type</th>
				<th style="width: 25%">Genre</th>
				<th style="width: 25%">birthDate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pets}" var="pet">
				<tr>
					<td width><c:out value="${pet.name}" /></td>
					<td><c:out value="${pet.type}" /></td>
					<td><c:out value="${pet.genre}" /></td>
					<td><c:out value="${pet.birthDate}" /></td>
					  <spring:url value="questionnaire/new/{petId}" var="questUrl">
        				<spring:param name="petId" value="${pet.id}"/>
    					</spring:url>
					<td> <a href="${fn:escapeXml(questUrl)}" class="btn btn-default">Adopt it</a>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
