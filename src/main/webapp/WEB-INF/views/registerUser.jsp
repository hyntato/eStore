<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Register User</h2>
		<p class="lead">Fill the below information to register:</p>

		<sf:form method="post" action="${pageContext.request.contextPath}/register" modelAttribute="user">

			<h4>기본 정보</h4>
			
			<div class="form-group">
				<label>Username</label>
				<span style="color: #ff0000">${usernameMsg}</span>
				<sf:input class="form-control" type="text" placeholder="Enter username" path="username" />
				<span style="color: #ff0000"><sf:errors path="username" /></span>
			</div>

			<div class="form-group">
				<label>Password</label>
				<sf:input class="form-control" type="password" placeholder="Enter password" path="password" />
				<span style="color: #ff0000;"><sf:errors path="password" /></span>
			</div>

			<div class="form-group">
				<label>Email</label>
				<span style="color: #ff0000">${emailMsg}</span>
				<sf:input class="form-control" type="text" placeholder="Enter email" path="email" />
				<span style="color: #ff0000;"><sf:errors path="email" /></span>
			</div>
			
			<br/>
			
			<h4>배송 정보</h4>
			
			<div class="form-group">
				<label>Address</label>
				<sf:input class="form-control" type="text" placeholder="Enter address" path="shippingAddress.address" />
			</div>

			<div class="form-group">
				<label>Country</label>
				<sf:input class="form-control" type="text" placeholder="Enter country" path="shippingAddress.country" />
			</div>

			<div class="form-group">
				<label>ZIP Code</label>
				<sf:input class="form-control" type="text" placeholder="Enter zip code" path="shippingAddress.zipCode" />
			</div>

			<br/>
			
			<button type="submit" class="btn btn-primary">Submit</button>
			<a href="<c:url value="/"/>" class="btn btn-dark">Cancel</a>
		</sf:form>

	</div>
</div>