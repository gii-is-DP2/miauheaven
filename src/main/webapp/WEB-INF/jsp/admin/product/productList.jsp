<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Products list</h2>


	<table id="questionnaireTable" class="table table-striped">
		<thead>
			<tr>
				<th >Name</th>
				<th >Price</th>
				<th >Stock</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="product">
				<tr>
					<td><c:out value="${product.name}" /></td>
					<td><c:out value="${product.price}" /></td>
					<c:if test="${product.stock}">
					<td><c:out value="Yes" /></td>
					</c:if>
					<c:if test="${not product.stock}">
					<td><c:out value="No" /></td>
					</c:if>
					<td><a href='<spring:url value="/admin/product/${product.id}"></spring:url>'>See more</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
