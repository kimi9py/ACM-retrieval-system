<%@ page language="java" contentType="text/html; charset=utf-8" %>

<p><h1><strong><center><font color=#428bca style="font-style:italic">DHU ACM Online Resource System</font></center></strong></h1></p>
<ul class="nav nav-tabs nav-justified">
	<li><a href="index.jsp">Home</a></li>
	<li><a href="KnowledgeList.jsp">Knowledge</a></li>
	<li><a href="DifficultyList.jsp">Difficulty</a></li>
	<li><a href="final.jsp">About</a></li>
</ul>
<ul class="nav nav-tabs nav-justified" style="padding-top:2%;padding-left:6%;padding-bottom:1%">
	<li>
		<form action="SearchProblem.jsp?SearchType=ID" method="POST">
		<div class="input-group">
			<input name="SearchKeyword" type="text" placeholder="Search By Problem ID" class="form-control" />
			<div class="input-group-btn">
				<button class="btn btn-primary" name="submit" type="submit">ID</button>
			</div>
		</div>
		</form>
	</li>
	<li>
		<form action="SearchProblem.jsp?SearchType=Title" method="POST">
			<div style="padding-left:10%" class="input-group">
				<input name="SearchKeyword" type="text" placeholder="Search By Problem Title" class="form-control" />
				<div class="input-group-btn">
           			<button class="btn btn-primary" name="submit" type="submit">Title</button>
           		</div>
           	</div>
		</form>
	</li>
	<li>
		<form action="SearchProblem.jsp?SearchType=Knowledge" method="POST">
			<div style="padding-left:10%;padding-right:10%" class="input-group">
				<input name="SearchKeyword" type="text" placeholder="Search By Knowledge" class="form-control" />
				<div class="input-group-btn">
					<button class="btn btn-primary" name="submit" type="submit">Knowledge</button>
				</div>
			</div>
		</form>
	</li>
</ul>

<script src="js/jquery-1.11.0.js" type="text/javascript"></script>
<script src="js/bootstrap3.0/bootstrap.js" type="text/javascript"></script>
	
