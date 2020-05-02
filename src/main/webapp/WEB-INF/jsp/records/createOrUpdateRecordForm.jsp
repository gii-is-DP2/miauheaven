<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<petclinic:layout pageName="records">
	<jsp:body>
    <c:choose>
        <c:when test="${not empty owners}">
        <h2>
            <c:if test="${record['new']}">New </c:if> record
        </h2>
      
        
        <form:form modelAttribute="record" class="form-horizontal">
           <div class="form-group has-feedback"> 
                <div class="control-group">
                  <label for="owner_id">Choose a owner:</label>
				<select name="owner_id">
    			<option value= "null">-</option>
				<c:forEach var="owner" items="${owners}">
						<option value="${owner.id}">${owner.firstName} ${owner.lastName} </option>
				</c:forEach>
				</select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${record['new']}">
                            <button class="btn btn-default" type="submit">Create record</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update record</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
          </c:when>
           <c:otherwise>
             <h2>
           No data
        </h2>
              </c:otherwise>
           </c:choose>
          
        <spring:url value="/owners/myAnimalShelter/records" var="recordListUrl">
	</spring:url>
	<a href="${fn:escapeXml(recordListUrl)}" class="btn btn-default">Go Back</a>
        
    </jsp:body>
</petclinic:layout>