
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Sign in example</title>
    
    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/signin/signin.css" rel="stylesheet">

</head>
<body>

<div class="container">

    <form action="<c:url value="/fidelity-spring-demo/login"></c:url>" class="form-signin" method="post">
        
        <h2 class="form-signin-heading">Please sign in</h2>
        
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    Reason: <c:out
                        value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">You have been logged out.</div>
        </c:if>
        
        <input type="text" class="form-control" id="username" name="username" placeholder="Username" autofocus/>
        <input type="password" class="form-control" id="password" placeholder="Password" name="password" />
        <input id="submit" class="btn btn-lg btn-success btn-block" name="submit" type="submit" value="Sign in" />
    </form>
    
    <div class="form-signin">
        <c:if test="${param.nouser != null}">
            <div class="alert alert-danger">PassKey is not attached to any profile. Login and bind your profile with your PassKey</div>
        </c:if>
        <c:if test="${param.nosoftware != null}">
            <div class="alert alert-danger">No WWPass software found!</div>
        </c:if>
        <button class="btn btn-lg btn-primary btn-block" onClick="javascript:OnAuth();">Login with WWPass</button>
    </div>

    <p style="text-align:center;"><a href="/fidelity-spring-demo/signup/form">Register</a></p>
    
</div>

<!-- wwpass auth -->
<script src="//cdn.wwpass.com/packages/latest/wwpass.js"></script>
<script type="text/javascript" charset="utf-8">

/* ID provider 10/06/2014 */

    function OnAuth() {
        wwpass_auth('fidelity%2Dtest.wwpass.net:p', function (status, response) {
/*            if (status != 603) {
                post_to_url("/wwpass", {ticket: response});
            }*/
            if (status == WWPass_OK) { //If ticket request handled successfully
            	//alert("status OK = '" + status + "'");
                post_to_url("/fidelity-spring-demo/wwpass", {ticket: response}); //Pass ticket to the auth.java and call it
            } else {
            	//alert("status error = '" + status + "'");
                document.location = "?nosoftware"; //If ticket request not handled, return error
            }
        });
        return false;
    }

    function post_to_url(path, params, method) {
        method = method || "post"; 

        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);

        for(var key in params) {
            if(params.hasOwnProperty(key)) {
                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", key);
                hiddenField.setAttribute("value", params[key]);

                form.appendChild(hiddenField);
            }
        }

        document.body.appendChild(form);
        form.submit();
    }
    
    window.alert = function(message, fallback){
        if(fallback)
        {
            old_alert(message);
            return;
        }
        $(document.createElement('div'))
            .attr({title: 'Alert', 'class': 'alert'})
            .html(message)
            .dialog({
                buttons: {OK: function(){$(this).dialog('close');}},
                close: function(){$(this).remove();},
                draggable: true,
                modal: true,
                resizable: false,
                width: 'auto'
            });
    };
</script>
</body>
</html>