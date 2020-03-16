<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="questionnaire">
	<h2>Applications for <c:out value="${questionnaire[0].pet.name}"></c:out></h2>


	<table id="questionnaireTable" class="table table-striped">
		<thead>
			<tr>
				<th >Name</th>
				<th >Score</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questionnaire}" var="quest">
				<tr>
					<td><c:out value="${quest.owner.firstName} ${quest.owner.lastName}" /></td>
					<td><c:out value="${quest.puntuacion}" /></td>
					<td><a href='<spring:url value="/owners/adoptList/questionnaire/show/${quest.id}"></spring:url>'>See more</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
