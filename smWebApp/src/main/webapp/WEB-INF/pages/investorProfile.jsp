<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="../../resources/include/head.jsp"%>

<script type="text/javascript">
	function load() {
	}
</script>
<div class="container">
	<hr class="space" />

	<%@include file="../../resources/include/header.jsp"%>
	<hr class="space" />

	<div class="span11 well">
		<div class="row">
			<div class="span11">
			<div class="pull-left">
				<img class="img-rounded" src="${investor.image}" width="150" height="150" />
			</div>
			<div class="span3">
				<h3>
					<c:out value="${investor.name}" />
				</h3>
				<span><c:out value="${investor.follower_count}" />
					Followers </span> <br> <span> <c:out value="${investor.company_count}" /> Companies Invested in
				</span>
				<hr class="space" />
				<c:forEach items="${investor.locations}" var="location">
					<span class="pull-left muted"> <small>${location.name}
					</small></span>
				</c:forEach>
				<br>
				<hr class="space" />
			</div>
			<div class="span3">
				<hr class="space" />
				<hr class="space" />
				<hr class="space" />
					<a id="profileLinkedInIcon" target="_blank"
						href="${investor.linkedin_url}"> <img class="img-rounded"
						src="resources/img/pro-linkedin-icon.png" width="30" height="30"></a>
					<a id="profileTwitterIcon" target="_blank"
						href="${investor.twitter_url}"> <img class="img-rounded"
						src="resources/img/pro-twitter-icon.png" width="30" height="30"></a>
					<a id="profileAnglelistInIcon" target="_blank"
						href="${investor.angellist_url}"> <img class="img-rounded"
						src="resources/img/pro-anglelist-icon.png" width="100"
						height="100"></a>
				</div>
			</div>
		</div>
	</div>
	<hr class="space" />

	<div class="span11 well">
		<div class="row">
			<div class="span11">
				<h4>About</h4>
				<span><c:out value="${investor.overview}" /></span>
			</div>
		</div>
	</div>

	<div class="span11 well">
		<div class="row">
			<div class="span11">
				<h4>Investments</h4>
				<hr class="space" />
					<span> 
						<c:forEach items="${investor.top_investments}" var="fund">
						<div class="pull-left span3">
						<c:forEach items="${fund.companies}" var="company">
							<a class="pull-left btn span3" href=companyProfile?permalink=${company.permalink}>
								<span class="pull-left muted">Series  ${fund.round_code}</span> 
								<span class="pull-right muted">${fund.funded_month}/${fund.funded_day}/${fund.funded_year} </span>
							<br>
								<span class="pull-left large-number">$${fund.raised_amount_in_million}M</span>
							<br>
							<hr class="space" />
								<span class="pull-left">${company.name}</span>
							<br>
								<img class="img-rounded pull-left" src="${company.logo_url}" width="40" height="40" />
							<br>
							</a>
							</c:forEach>
						</div>
					</c:forEach>
				</span>
			</div>
		</div>
	</div>
	
	<div class="span11 well">
		<div class="row">
			<div class="span11">
				<h4>Companies Invested In</h4>
				<span> <c:forEach items="${companies}" var="company">
						<div class="pull-left">
							<img class="img-rounded" src="${company.logo_url}" width="50"
								height="50" /> <span class="pull-left "> <a
								href=companyProfile?permalink=${company.permalink}><c:out
										value="${company.name}" /></a>
							</span>
							<hr class="space" />
						</div>

					</c:forEach>
				</span>
			</div>
		</div>
	</div>
</div>
</body>
</html>
