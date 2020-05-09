<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="event">
    	<jsp:attribute name="customScript">
        <script>
					$(function() {
						$("#date").datepicker({
							dateFormat : 'yy/mm/dd'
						});
					});
		</script>
    </jsp:attribute>
    <jsp:body>
    
        <h2>
            <c:if test="${event['new']}">New </c:if> Event
        </h2>
        <form:form modelAttribute="event"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${event.id}"/>
           <div class="form-group has-feedback"> 
                <div class="control-group">
                	<petclinic:inputField label="Name" name="name"></petclinic:inputField>
                	<petclinic:inputField label="Description:" name="description"></petclinic:inputField>
                	<petclinic:inputField label="Date of the event" name="date"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${event['new']}">
                            <button class="btn btn-default" type="submit">Create Event</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Event</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!event['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
