// PREPARE FORM DATA
$( document ).ready(function() {
	$("#save").click(function(event){
		event.preventDefault();
		var formData = {
			"sku" : $("#sku").val(),
			"name" : $("#name").val(),
			"description" : $("#description").val(),
			"price" : $("#price").val(),
			"year" : $("#year").val(),
			"link" : "http://shop.oreilly.com/product/"+$("#sku").val()+".do",
			"categories" : [ "XML", "NoSQL", "book" ]
		}
		ajaxPost(formData);
	});
	$("#get").click(function(event){
		event.preventDefault();
		var formData = $("#sku_get").val();
		ajaxGet(formData);
	});
	$("#delete").click(function(event){
		event.preventDefault();
		var formData = $("#sku_del").val();
		ajaxDelete(formData);
	});
});
function ajaxPost(formData){
    	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "products",
		data : JSON.stringify(formData),
		dataType : 'text',
		success : function() {
			alert("Success!");
		},
		error : function(e) {
			alert("Error!");
			console.log("ERROR: ", e);
		}
	});
}
function ajaxDelete(id){
	$.ajax({
		type : "DELETE",
		contentType : "application/json",
		url : "/products/"+id+".json",
		success : function(result) {
			alert("Success!");
			console.log(result);
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
}
function ajaxGet(id){
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/products/"+id+".json",
		success : function() {
			alert("Success!");
			console.log();
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
}