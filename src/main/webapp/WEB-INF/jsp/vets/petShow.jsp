<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2>Pet Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${pet.name}"/></b></td>
        </tr>
        <tr>
            <th>Type</th>
            <td><c:out value="${pet.type}"/></td>
        </tr>
        <tr>
            <th>Owner</th>
            <td><c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/></td>
        </tr>
        <tr>
            <th>Birth date</th>
            <td><c:out value="${pet.birthDate}"/></td>
        </tr>
    </table>
</petclinic:layout>
