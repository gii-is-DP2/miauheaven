<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pets">
    <h2>Pets</h2>

    <table id="petsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Owner</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
                    <c:out value="${pet.name}"/>
                </td>
                <td>
                    <c:out value="${pet.type}"/>
                </td>
                 <td>
                    <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
                </td>
                <td>
                <a href='<spring:url value="/pets/${pet.id}"/>'>See more</a>
               </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>            
        </tr>
    </table> -->
</petclinic:layout>
