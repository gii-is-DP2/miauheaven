<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="appointments">
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
			<c:if test="${appointment['new']}">New </c:if>Appointment</h2>
         
     
        <form:form modelAttribute="appointment" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Date" name="date" />
                <petclinic:inputField label="Cause" name="cause" />
                <label for="urgent">¿Its urgent?:</label>
				<select name="urgent">
	 				<option value="1">Yes</option>
	 				<option value="0">No</option>
	 						
				</select>
                <br>
                <label for="vet_id">Choose a vet:</label>
				<select name="vet_id">
				<c:forEach var="vet" items="${vets.vetList}">
	 						<option value="${vet.id}">${vet.firstName} ${vet.lastName} </option>
						</c:forEach>
				</select>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${appointment.pet.id}" />
                    <button class="btn btn-default" type="submit">Add Appointment</button>
                </div>
            </div>
        </form:form>

        <br />
    </jsp:body>

</petclinic:layout>
