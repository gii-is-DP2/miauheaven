<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="questionnaire">
    
    <jsp:body>
    
        <h2>
            <c:if test="${questionnaire['new']}">New </c:if> Questionnaire for <c:out value="${questionnaire.pet.name}"/>
        </h2>
        <form:form modelAttribute="questionnaire"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${questionnaire.id}"/>
           <div class="form-group has-feedback"> 
                <div class="control-group">
                <petclinic:selectField label="¿Dónde vives?" name="vivienda" size="3" names="${viviendas}"></petclinic:selectField>
                <petclinic:selectField label="¿Cómo considerarías tus ingresos mensuales?" name="ingresos" size="3" names="${ingresos}"></petclinic:selectField>
                <petclinic:selectField label="¿Tiempo libre a lo largo del día?" name="horasLibres" size="3" names="${horasLibres}"></petclinic:selectField>
                <petclinic:selectField label="¿Tienes otros animales con problemas de convivencia?" name="convivencia" size="2" names="${convivencia}"></petclinic:selectField>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${questionnaire['new']}">
                            <button class="btn btn-default" type="submit">Create adoption Questionnaire</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Pet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!questionnaire['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
