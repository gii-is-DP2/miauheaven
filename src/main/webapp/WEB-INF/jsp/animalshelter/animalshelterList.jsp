<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="animalshelters">
	<h2>Animal Shelter</h2>
	
	  
    	<spring:url value="animalshelter/new" var="animalshelternewUrl">
	</spring:url>
	<a href="${fn:escapeXml(animalshelternewUrl)}" class="btn btn-default">Become an Animal Shelter</a>
    
	

	<table id="animalsheltersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">CIF</th>
				<th style="width: 120px">Place</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${animalshelters}" var="animalshelter">
				<tr>
					<td><spring:url value="/animalshelter/{animalshelterId}" var="animalshelterUrl">
							<spring:param name="animalshelterId" value="${animalshelter.id}" />
						</spring:url> <c:out value="${animalshelter.name}" /></td>
					<td><c:out value="${animalshelter.cif}" /></td>
					<td><c:out value="${animalshelter.place}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
