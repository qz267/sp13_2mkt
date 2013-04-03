<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="../../resources/include/head.jsp"%>

<div class="container">
	<hr class="space" />

	<%@include file="../../resources/include/header.jsp"%>
	<hr class="space" />

	<div class="row">

		<div class="span3">
			<div class="side-block hidden-phone">
				<div>
					<strong>Followers</strong> <small class="pull-right"><a>Select
							All</a> | <a href="#">None</a></small>
				</div>
				<hr class="space" />
				<ul class="clear-ul">

					<li class="mts"><input type="checkbox" checked="checked">&nbsp;0-50</input>
						<a class="small-text pull-right" href="#">only</a></li>

					<li class="mts"><input type="checkbox" checked="checked">&nbsp;51-100</input>
						<a class="small-text pull-right" href="#">only</a></li>

					<li class="mts"><input type="checkbox" checked="checked">&nbsp;101-150</input>
						<a class="small-text pull-right" href="#">only</a></li>

					<li class="mts"><input type="checkbox" checked="checked">&nbsp;150+</input>
						<a class="small-text pull-right" href="#">only</a></li>
				</ul>

				<hr class="space" />
				<a href="#" class="btn">Update</a>

			</div>

			<hr class="space" />

		</div>


		<div class="span9">

			<h1>Companies</h1>
			<hr>
			<c:forEach items="${companies}" var="company">
				<div class="search-result">
					<div class="pull-left search-pic">
						<img src="${company.logo_url}" width="50" height="50" />
					</div>

					<div class="pull-left search-content">
						<div>
							<h4 class="pull-left">
								<a href="companyProfile"><c:out value="${company.name}" />
							</h4>
							</a>
						</div>
						<div>
							<a class="pull-left" href="#">${company.company_url}</a>
						</div>
					</div>


					<div class="search-stats pull-right">
						<div>
							<span class="large-number"><c:out
									value="${company.follower_count}" /></span> Followers
						</div>
						<div>
							<span class="large-number"><c:out
									value="${company.total_funding}" /></span> Total Funding
						</div>
					</div>

					<div class="clearfix"></div>
					<hr class="space" />
					
				</div>
				 <hr class="space"/>
			</c:forEach>
		</div>


	</div>






</div>


</body>
</html>
