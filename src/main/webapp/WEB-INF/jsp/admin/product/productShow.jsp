<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="products">

    <h2>Information about the product</h2>
	<br>
	<br>
	<br>
	<b>Image of the product:</b>
	<div class="col-md-12">
            <spring:url value="${product.image}" htmlEscape="true" var="image"/>
            <img class="img-responsive" src="${image}" height="200" width="200"/>
        </div>
    <table class="table table-striped">
       <tr>
            <th>Product name</th>
            <td><c:out value="${product.name}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${product.description}"/></td> 
        </tr>
        <tr>
            <th>price</th>
            <td><c:out value="${product.price}"/></td>
        </tr>
        <tr>
            <th>stock</th>
            <c:if test="${product.stock}">
					<td><c:out value="Yes" /></td>
					</c:if>
					<c:if test="${not product.stock}">
					<td><c:out value="No" /></td>
					</c:if>
        </tr>
    </table>
    <a class="btn btn-default" href='<spring:url value="/owners/product/save/${product.id}" ></spring:url>'>Update</a>
</petclinic:layout>
