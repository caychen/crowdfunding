<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul style="padding-left:0px;" class="list-group">
	<c:forEach items="${rootPermission.children}" var="permission">
		<c:if test="${empty permission.children}">
			<li class="list-group-item tree-closed" >
				<a href="${ctx}${permission.url}"><i class="${permission.icon}"></i> ${permission.name}</a>
			</li>
		</c:if>
		<c:if test="${not empty permission.children}">
			<li class="list-group-item tree-expanded">
				<span><i class="${permission.icon}"></i> ${permission.name} <span class="badge" style="float:right">${permission.children.size()}</span></span>
				<c:forEach items="${permission.children}" var="p">
					<ul style="margin-top:10px;">
						<li style="height:30px;">
							<a href="${ctx}${p.url}"><i class="${p.icon}"></i> ${p.name}</a>
						</li>
					</ul>
				</c:forEach>
			</li>
		</c:if>
	</c:forEach>
</ul>