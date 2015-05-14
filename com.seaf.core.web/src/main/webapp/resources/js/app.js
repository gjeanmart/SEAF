	/**
	 * MODULE
	 */
	var seafApp = angular.module('SeafApp', 
			['ngRoute', 
			 'ngTable',
			 'ngResource',
			 'angularSpinner',
			 'mgcrea.ngStrap',
			 'nvd3ChartDirectives',
			 'ui.router',
			 'ncy-angular-breadcrumb'
			]);

	

	/**
	 * CONFIG
	 */
	// configure our routes
	seafApp.config(function($stateProvider, $urlRouterProvider) {
		  $urlRouterProvider.otherwise("/");
		  
		  var viewBase = 'resources/views/';
			
		  $stateProvider
		    	.state('home', {
		    		url				: "/",
		    		controller		: 'mainController',
		    		templateUrl		: viewBase + 'home.html',
		    		ncyBreadcrumb	: { label: 'Home page'  }
		    	})
		    	.state('user', {
		    		url				: "/user",
		    		controller		: 'userController',
		    		templateUrl		: viewBase + 'user.html',
		    		ncyBreadcrumb	: { parent: 'home', label: 'Users'  }
		    	})
		    	.state('user_detail', {
		    		url				: "/user/detail/:userid",
		    		controller		: 'userController',
		    		templateUrl		: viewBase + 'user-detail.html',
		    		ncyBreadcrumb	: { parent: 'user', label: '{{user.firstName}} {{user.lastName}}'  }
		    	})
		    	.state('user_edit', {
		    		url				: "/user/edit/:userid",
		    		controller		: 'userController',
		    		templateUrl		: viewBase + 'user-edit.html',
		    		ncyBreadcrumb	: { parent: 'user', label: 'Editing {{user.firstName}} {{user.lastName}}'  }
		    	})
		    	.state('group', {
		    		url				: "/group",
		    		controller		: 'groupController',
		    		templateUrl		: viewBase + 'group.html',
		    		ncyBreadcrumb	: { parent: 'home', label: 'Groups'  }
		    	})
		    	.state('logger', {
		    		url				: "/logger",
		    		controller		: 'loggerController',
		    		templateUrl		: viewBase + 'logger.html',
		    		ncyBreadcrumb	: { parent: 'home', label: 'Loggers'  }
		    	})
		    	.state('console', {
		    		url				: "/console",
		    		controller		: 'consoleController',
		    		templateUrl		: viewBase + 'console.html',
		    		ncyBreadcrumb	: { parent: 'home', label: 'Console'  }
		    	})
		    	.state('monitoring', {
		    		url				: "/monitoring/:chartid",
		    		controller		: 'monitoringController',
		    		templateUrl		: viewBase + 'monitoring.html',
		    		ncyBreadcrumb	: { parent: 'home', label: '{{chartid}}'  }
		    	})
		    	.state('java', {
		    		url				: "/java/:pageid",
		    		controller		: 'javaController',
		    		templateUrl		: viewBase + 'java.html',
		    		ncyBreadcrumb	: { parent: 'home', label: '{{title}}'  }
		    	});
		})
	
	.config(function($routeProvider) {
		var viewBase = 'resources/views/';
		
		$routeProvider
			.when('/', {
				templateUrl : viewBase + 'home.html',
				controller  : 'mainController',
				ncyBreadcrumb: { label: 'Home page'  }
			})
			.when('/user', {
				templateUrl : viewBase + 'user.html',
				controller  : 'userController',
				ncyBreadcrumb: { label: 'Users'  }
			})
			.when('/user/detail/:userid', {
				templateUrl : viewBase + 'user-detail.html',
				controller  : 'userController',
				ncyBreadcrumb: { label: 'Detail'  }
			})
			.when('/user/edit/:userid', {
				templateUrl : viewBase + 'user-edit.html',
				controller  : 'userController',
				ncyBreadcrumb: { label: 'Editing'  }
			})
			.when('/group', {
				templateUrl : viewBase + 'group.html',
				controller  : 'groupController',
				ncyBreadcrumb: { label: 'Groups'  }
			})
			.when('/log', {
				templateUrl : viewBase + 'logger.html',
				controller  : 'loggerController',
				ncyBreadcrumb: { label: 'Loggers'  }
			})
			.when('/console', {
				templateUrl : viewBase + 'console.html',
				controller  : 'consoleController',
				ncyBreadcrumb: { label: 'Console'  }
			})
			.when('/monitoring/:chartid', {
				templateUrl : viewBase + 'monitoring.html',
				controller  : 'monitoringController',
				ncyBreadcrumb: { label: 'Monitoring'  }
			})
			.when('/java/:pageid', {
				templateUrl : viewBase + 'java.html',
				controller  : 'javaController',
				ncyBreadcrumb: { label: 'Java'  }
			})
			.otherwise({
				redirectTo: '/'
			});
		
	}).config(function($datepickerProvider) {
		angular.extend($datepickerProvider.defaults, {
			dateFormat: 'yyyy-MM-dd',
			startWeek: 1
		});
		  
	}).config(function($alertProvider) {
		angular.extend($alertProvider.defaults, {
			animation: 'am-fade-and-slide-top',
			placement: 'top'
		});
	
	}).config(function($modalProvider) {
		  angular.extend($modalProvider.defaults, {
			  html: true
		  });
	})
	.run(function($rootScope, $location, $alert, usSpinnerService) {
		$rootScope.config = {
				'itemPerPage' : 10
		};
		
	    $rootScope.showSpinner = function() {
	    	$("#spinner").addClass("spinner");
	    	usSpinnerService.spin('spinner');
	    };
	    
	    $rootScope.hideSpinner = function() {
	    	$("#spinner").removeClass();
	    	usSpinnerService.stop('spinner');
	    };
	    
	    $rootScope.displayAlert = function(type, content) {
	    	var duration = (type == "danger") ? null : 3;
	    	
    		$alert({
    			//title		: title, 
    			content		: content, 
    			type		: type, 
    			duration	: duration,
    			container 	: '#alerts',
    			animation	: 'am-fade-and-slide-top', //TODO not working
    			show		: true});
	    }; 
	    
	    $rootScope.go = function (hash) { 
	    	$location.path(hash);
	    };
	});


