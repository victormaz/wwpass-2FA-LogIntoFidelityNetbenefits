<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Dashboard example</title>

    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/signin/signin.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <h1>Welcome, <sec:authentication property="principal.username" />!</h1>
    <br/>
    <p><a href="/fidelity-spring-demo/logout">Logout</a></p>
    <br/>
    <c:if test="${message != null}">
        <div class="alert alert-success" id="message"><c:out value="${message}"/></div>
    </c:if>
    <br/>
    <h2>Attach your PassKey to your profile:</h2>
    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
            Failed to login.
            <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                Reason: <c:out
                    value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
            </c:if>
        </div>
    </c:if>

    <c:if test="${param.success != null}">
        <div class="alert alert-success">PassKey is successfully attached.</div>
    </c:if>

    <div class="col-sm-4">
        <form:form action="/fidelity-spring-demo/attach" method="post" modelAttribute="dashboardForm" id="dashboardForm" cssClass="form-horizontal">

            <form:errors path="*" element="div" cssClass="alert alert-danger"/>

            <c:if test="${param.nosoftware != null}">
                <div class="alert alert-danger">No WWPass software found!</div>
            </c:if>

            <div class="form-group">
                <div class="col-sm-10">
                    <form:password path="password" id="password" placeholder="Password" cssClass="col-sm-10"/>
                </div>
            </div>

            <form:hidden path="ticket" id="ticket" />

        </form:form>

        <div class="form-group">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary" onClick="javascript:OnAuth();">Attach</button>
            </div>
        </div>
    </div>
</div>

<!-- wwpass auth -->
<script src="//cdn.wwpass.com/packages/latest/wwpass.js"></script>
<script type="text/javascript" charset="utf-8">

    function OnAuth() {
        wwpass_auth('fidelity%2Dtest.wwpass.net:p', function (status, response) {
            if (status == WWPass_OK) { //If ticket request handled successfully
                document.getElementById("ticket").setAttribute("value", response);
                var f = document.getElementById('dashboardForm');
                f.submit();
            } else {
                document.location = "?nosoftware"; //If ticket request not handled, return error
            }
        });
        return false;
    }
</script>
</body>
</html>