<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="staticResourceVersion" value="2018112600001" scope="request"/>

<script type="text/javascript">
    var _ctx_ = "${ctx}";
    var _companyName_ = "${companyName}";
    window.staticResourceVersion = "${staticResourceVersion}";
</script>