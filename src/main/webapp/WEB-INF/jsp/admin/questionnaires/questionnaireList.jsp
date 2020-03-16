<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="questionnaire">
	<h2>Applications</h2>


	<table id="questionnaireTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Score</th>
				<th style="width: 200px;">Name</th>
				<th style="width: 120px">Home</th>
				<th style="width: 120px">Income</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questionnaires}" var="quest">
				<tr>
					<td><c:out value="${quest.puntuacion}" /></td>
					<td><c:out value="${quest.name}" /></td>
					<td><c:out value="${quest.vivienda}" /></td>
					<td><c:out value="${quest.ingresos}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
