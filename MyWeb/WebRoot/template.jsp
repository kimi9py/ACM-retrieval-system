<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" href="css/bootstrap3.0/bootstrap.css">
		<title><%=title%></title>
		<script src="js/respond.js"></script>
		<script src="js/html5shiv.js"></script>
	</head>

	<body>
		<jsp:include page="top.jsp" />
		<jsp:include page="<%=content%>" />
		<jsp:include page="footer" />
	</body>
</html>

