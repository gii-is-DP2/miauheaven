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
				<th style="width: 150px">Name</th>
				<th style="width: 200px">birthDate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pets}" var="pet">
				<tr>
				<td>
					<td><c:out value="${pet.name}" /></td>
					<td><c:out value="${pet.birthDate}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
