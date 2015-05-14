<!-- index.html -->
<!DOCTYPE html>
<html ng-app="SeafApp">
	<head>
	  <meta charset="utf-8">
	
	  <!-- CSS -->
	  <link rel="stylesheet" href="resources/lib/bootstrap/css/bootstrap-theme.min.css" />
	  <link rel="stylesheet" href="resources/lib/bootstrap/css/bootstrap.min.css" />
	  

	  <link rel="stylesheet" href="resources/lib/bootstrap/module/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" />
	  <link rel="stylesheet" href="resources/lib/font-awesome/css/font-awesome.css" />
	  <link rel="stylesheet" href="resources/lib/angularjs/modules/ng-table/ng-table.css" />
	  <link rel="stylesheet" href="resources/lib/nvd3/nv.d3.css" />
	  <link rel="stylesheet" href="resources/css/app.css" />
	 
	 <!-- JAVASCRIPT -->
	  <script src="resources/lib/jquery/jquery-2.1.1.js"></script>
	  <script src="resources/lib/bootstrap/js/bootstrap.js"></script>
	  <script src="resources/lib/bootstrap/module/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	  <script src="resources/lib/Bootbox/bootbox.js"></script>
	  <script src="resources/lib/sockjs/sockjs-0.3.js"></script>
      <script src="resources/lib/d3/d3.min.js"></script>
      <script src="resources/lib/nvd3/nv.d3.js"></script>
      <script src="resources/lib/spin/spin.js"></script>
	  <script src="resources/lib/angularjs/angular.js"></script>
      <script src="resources/lib/angularjs/modules/angular-route/angular-route.js"></script>
      <script src="resources/lib/angularjs/modules/angular-resource/angular-resource.js"></script>
      <script src="resources/lib/angularjs/modules/angular-animate/angular-animate.js"></script>
      <script src="resources/lib/angularjs/modules/angular-sanitize/angular-sanitize.js"></script>
      <script src="resources/lib/angularjs/modules/angular-spinner/angular-spinner.js"></script>
      <script src="resources/lib/angularjs/modules/ng-table/ng-table.js"></script>
	  <script src="resources/lib/angularjs/modules/angular-strap/angular-strap.js"></script>
	  <script src="resources/lib/angularjs/modules/angular-strap/angular-strap.tpl.js"></script>
      <script src="resources/lib/angularjs/modules/angularjs-nvd3/angularjs-nvd3-directives.js"></script>
      <script src="resources/lib/angularjs/modules/angular-ui-router/angular-ui-router.js"></script>
	  <script src="resources/lib/angularjs/modules/angular-breadcrumb/angular-breadcrumb.js"></script>

	  <script src="resources/js/app.js"></script>
	  <script src="resources/js/directives/showErrorsDirective.js"></script>
	  <script src="resources/js/services/rest/UserGroupService.js"></script>
	  <script src="resources/js/services/rest/LogService.js"></script>
	  <script src="resources/js/services/rest/JavaService.js"></script>
	  <script src="resources/js/services/websocket/sockJsClientService.js"></script>
	  <script src="resources/js/controllers/HomeController.js"></script>
	  <script src="resources/js/controllers/UserController.js"></script>
	  <script src="resources/js/controllers/GroupController.js"></script>
	  <script src="resources/js/controllers/loggerController.js"></script>
	  <script src="resources/js/controllers/ConsoleController.js"></script>
	  <script src="resources/js/controllers/javaController.js"></script>
	  <script src="resources/js/controllers/monitoringController.js"></script>
	  <script src="resources/js/Utils/Base64.js"></script>
	  <script src="resources/js/Utils/CircularBuffer.js"></script>
	</head>
	
	<body ng-app="SeafApp">

		<div id="wrapper" class="active" >

			<!-- HEADER AND NAVBAR -->
			<header>
				<nav class="navbar navbar-default navbar-fixed-top">
					<div class="container">
						<div class="navbar-header">
							<a class="navbar-brand" style="padding-left: 200px;" href="#">Semantic Enterprise Application Framework</a>
						</div>
		
						<ul class="nav navbar-nav navbar-right">
							<li><a ui-sref="home"><i class="fa fa-home"></i> Home</a></li>
						</ul>
						
					</div>
				</nav>
			</header>
			
			<!-- SIDEBAR --> 
			<div id="sidebar-wrapper">
		      	<ul id="sidebar_menu" class="sidebar-nav">
		        	<li class="sidebar-brand">
		        		<a id="menu-toggle" href="#">ADMIN<span class="main_icon glyphicon glyphicon-align-justify"></span></a>
		        	</li>
		      	</ul>
		        <ul class="sidebar-nav" id="sidebar">     
		          	<li><a ui-sref="user">Users<span class="icon fa fa-user"></span></a></li>
		          	<li><a ui-sref="group">Groups<span class="icon fa fa-users "></span></a></li>
		        </ul>
		        
		      	<ul id="sidebar_menu" class="sidebar-nav">
		        	<li class="sidebar-brand"><a id="menu-toggle">Logs<span class="main_icon fa fa-lg fa-file-text-o"></span></a>
		        	</li>
		      	</ul>
		        <ul class="sidebar-nav" id="sidebar">     
		          	<li><a ui-sref="logger">Loggers<span class="icon fa fa-toggle-on"></span></a></li>
		          	<li><a ui-sref="console">Console<span class="icon fa fa-files-o"></span></a></li>
		        </ul>
		        
		      	<ul id="sidebar_menu" class="sidebar-nav">
		        	<li class="sidebar-brand"><a id="menu-toggle">System & App<span class="main_icon fa fa-lg fa-cog "></span></a>
		        	</li>
		      	</ul>
		        <ul class="sidebar-nav" id="sidebar">     
		        	<li><a ui-sref="java({ pageid : 'threads' })">Threads<span class="icon fa fa-share-alt"></span></a></li>
		          	<li><a ui-sref="java({ pageid : 'app_properties' })">Application Properties<span class="icon fa fa-th-list"></span></a></li>
		          	<li><a ui-sref="java({ pageid : 'system_properties' })">System Properties<span class="icon fa fa-th-list"></span></a></li>
		          	<li><a ui-sref="java({ pageid : 'jvm_arguments' })">JVM Arguments<span class="icon fa fa-th-list"></span></a></li>
		          	<li><a ui-sref="monitoring({ chartid : 'Memory' })">Monitoring<span class="icon fa fa-bar-chart"></span></a></li>
		        </ul>
			</div>
	
			<!-- MAIN CONTENT AND INJECTED VIEWS -->
			<div id="main">
				
				<span id="spinner" us-spinner="{radius:30, width:8, length: 16, className: 'spinner'}" spinner-key="spinner"></span>
				
				<div ncy-breadcrumb style="padding-top: 60px; margin: 0"></div>
				
				<div ui-view></div>
			</div>
			
			<!-- FOOTER -->
			<header>
				<nav class="navbar navbar-default navbar-fixed-bottom">
				<div class="container">
					<div class="navbar-header" style="padding-left: 200px;">
						<a class="navbar-brand" href="#">Semantic Enterprise Application Framework</a>
					</div>
				</div>
				</nav>
			</header>
			
		</div>
		
		<script type="text/javascript">
			$("#menu-toggle").click(function(e) {
		        e.preventDefault();
		        $("#wrapper").toggleClass("active");
			});
		</script>
	</body>
</html>