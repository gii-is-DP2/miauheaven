<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="createNotification">
   <jsp:body>
        <h2>Create Notification</h2>
        <form:form modelAttribute="notification" class="form-horizontal">
            	<input type="hidden" name="id" value="${notification.id}"/>
           		<petclinic:inputField label="Title" name="title"/>
                <petclinic:inputField label="Message" name="message"/>
                <petclinic:inputField label="Destination URL" name="url"/>
                <petclinic:selectField label="Target" name="target" size="2" names="${targets}"></petclinic:selectField>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-default" type="submit">Add notification</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
