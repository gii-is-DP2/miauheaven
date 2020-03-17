<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="products">
   <jsp:body>
        <h2>Create Product</h2>
        <form:form modelAttribute="product" class="form-horizontal" action="/admin/product/save/">
            	<input type="hidden" name="id" value="${product.id}"/>
           		<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Description" name="description"/>
                <petclinic:inputField label="Price" name="price"/>
                <petclinic:selectField label="Stock" name="stock" size="3" names="${stock}"></petclinic:selectField>
                <petclinic:inputField label="Image URL" name="image"/>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                			<c:if test="${not empty product.name}">
                            <button class="btn btn-default" type="submit">Update product</button>
                            </c:if>
                            <c:if test="${empty product.name}">
                            <button class="btn btn-default" type="submit">Add product</button>
                            </c:if>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
