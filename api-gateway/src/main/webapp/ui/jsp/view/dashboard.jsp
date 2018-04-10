<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Welcome ${user.details.principal.username}</h2>
<img src="<c:url value="/resources/img/dms.jpg" />" class="img-fluid center-block" style='margin-top: -50px'>
<form method='post' action='/loging/logout'>
    <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type='submit' value='ajdeee'>
</form>