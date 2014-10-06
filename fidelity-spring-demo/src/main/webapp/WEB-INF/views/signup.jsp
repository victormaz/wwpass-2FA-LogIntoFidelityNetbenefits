
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Registration example</title>
    
    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/signin/signin.css" rel="stylesheet">

</head>
<body>

<div class="container">
    
    <h1>User Registration</h1>

    <form:form action="./new" method="post" modelAttribute="signupForm" id="signupform" cssClass="form-horizontal">

        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

        <fieldset>
            <div class="col-sm-4">

                <div class="form-group">
                    <form:input path="username" id="username"  placeholder="Username" cssClass="col-sm-10"/>
                </div>

                <div class="form-group">
                    <form:input path="nickname" id="nickname" placeholder="Your Name" cssClass="col-sm-10"/>
                </div>

                <div class="form-group">
                    <form:password path="password" id="password" placeholder="Password" cssClass="col-sm-10"/>
                </div>

                <div class="form-group">
                    <div class="col-sm-10">
                        <input class="btn" type="submit"
                               value="Create Account" />
                    </div>
                </div>

            </div>
        </fieldset>
        
    </form:form>

</div>
</body>
</html>