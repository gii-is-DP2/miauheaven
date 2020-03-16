<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="questionnaire">
	<h2>Questionnaire Information</h2>


	<table class="table table-striped">
		<tr>
            <th>Score</th>
            <td><b><c:out value="${quest.puntuacion}"/></b></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><c:out value="${quest.name}"/></td>
        </tr>
        <tr>
            <th>Home</th>
            <td><c:out value="${quest.vivienda}"/></td>
        </tr>
        <tr>
            <th>Income</th>
            <td><c:out value="${quest.ingresos}"/></td>
        </tr>
	</table>
</petclinic:layout>
