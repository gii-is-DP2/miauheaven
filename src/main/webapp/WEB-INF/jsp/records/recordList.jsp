<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="records">
	<h2>Records</h2>
	<a class="btn btn-default" href='<spring:url value="/owners/myAnimalShelter/records/new" htmlEscape="true"/>'>Add Record</a>
<br>
<br>
	<table id="recordsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Pets</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${records}" var="record">
				<tr>
					<td><c:out value="${record.owner.firstName} ${record.owner.lastName}" /></td>
					<c:forEach items="${quests}" var="quest">
						<c:if test="${record.owner.id == quest.owner.id}"></c:if>
						<td><c:out value="${quest.pet.name}" /></td>
						<br>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
