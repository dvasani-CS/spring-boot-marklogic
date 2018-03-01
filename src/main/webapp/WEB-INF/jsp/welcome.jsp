<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
<script src="/js/main.js"></script>

</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="starter-template">
			<h1>Spring Boot Web</h1>
			<h2>Message: ${message}</h2>
			<h2>host: ${host}</h2>
			<a href="/products.json">show all</a>
		</div>
	</div>
	<div class="container">

		<div>
			<form class="form-inline" style="margin: 20px 20px 20px 20px"
				id="customerForm">
				<div class="form-group">
					<label for="sku" style="margin-right: 5px">SKU:</label> <input
						type="text" class="form-control" id="sku" placeholder="Enter SKU" />
				</div>
				<div class="form-group">
					<label for="name" style="margin-left: 20px; margin-right: 5px">Name:</label>
					<input type="text" class="form-control" id="name"
						placeholder="Enter Name" />
				</div><br><br>
				<div class="form-group">
					<label for="description" style="margin-right: 5px">Description:</label>
					<input type="text" class="form-control" id="description"
						placeholder="Enter Description" />
				</div>
				<div class="form-group">
					<label for="price" style="margin-left: 20px; margin-right: 5px">Price:</label>
					<input type="text" class="form-control" id="price"
						placeholder="Enter Price" />
				</div><br><br>
				<div class="form-group">
					<label for="year" style="margin-left: 20px; margin-right: 5px">Year:</label>
					<input type="text" class="form-control" id="year"
						placeholder="Enter Year" />
				</div><br><br>
				<button type="button" class="btn btn-default" id="save"
					style="margin-left: 20px; margin-right: 5px">Save</button>
			</form>
		</div>
		<div>
			<div class="form-group">
				<label for="sku_get" style="margin-right: 5px">SKU:</label> <input
					type="text" class="form-control" id="sku_get" placeholder="Enter SKU" />
			</div>
			<button type="button" class="btn btn-default" id="get"
				style="margin-left: 20px; margin-right: 5px">Get</button>
		</div>
		<div>
			<div class="form-group">
				<label for="sku_del" style="margin-right: 5px">SKU:</label> <input
					type="text" class="form-control" id="sku_del" placeholder="Enter SKU" />
			</div>
			<button type="button" class="btn btn-default" id="delete"
				style="margin-left: 20px; margin-right: 5px">Delete</button>
		</div>
	</div>
	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>