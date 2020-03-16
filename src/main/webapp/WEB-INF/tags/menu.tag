<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find owners</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAuthority('veterinarian')">
				<petclinic:menuItem active="${name eq 'pets'}" url="/pets"
					title="find pets for Vets">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Pets for vets</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'pets'}" url="/admin/pets"
					title="find pets for Admin">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Pets for admin</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'notificationList'}" url="/admin/notification/"
					title="List of notifications">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>List of notifications</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'adoptionPetList'}" url="/owners/adoptList/"
					title="Notifications for owners">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Adopt a pet</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'notificationList'}" url="/owners/notification/"
					title="Notifications for owners">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Notifications for owners</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('veterinarian')">
				<petclinic:menuItem active="${name eq 'notificationList'}" url="/vets/notification/"
					title="Notifications for vets">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Notifications for vets</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('animalshelter')">
				<petclinic:menuItem active="${name eq 'notificationList'}" url="/animalshelter/notification/"
					title="Notifications for animal shelters">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Notifications for animal shelters</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('animalshelter')">
				<petclinic:menuItem active="${name eq 'myAnimalShelter'}" url="/owners/myAnimalShelter"
					title="Notifications for animal shelters">
					<span class="glyphicon glyphicon-user icon-size" aria-hidden="true"></span>
					<span>My animal shelter</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarians</span>
				</petclinic:menuItem>

		
				<petclinic:menuItem active="${name eq 'animalshelter'}" url="/animalshelter"
					title="animalshelter">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Animal Shelter</span>
				</petclinic:menuItem>

				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'appointments'}" url="/admin/appointments"
					title="See appointments">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>See appointments</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('veterinarian')">
				<petclinic:menuItem active="${name eq 'appointments'}" url="/vets/appointment"
					title="See appointments">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>See appointments</span>
				</petclinic:menuItem>
				</sec:authorize>

				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>
				
				
				
				

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
