(function () {

    var injectParams 		= ['$scope', '$rootScope', '$stateParams', 'javaService', 'ngTableParams'],
    	PAGEID_THREADS 		= "threads",
    	PAGEID_SYSTEMPROPS 	= "system_properties",
    	PAGEID_APPPROPS		= "app_properties",
    	PAGEID_JVMARGS 		= "jvm_arguments";

    var javaController = function ($scope, $rootScope, $stateParams, javaService, ngTableParams) {

    	$scope.title = "";
    	
    	$scope.initialize = function() {

    		$("#"+$stateParams.pageid+"-table").show();
    		
        	if($stateParams.pageid === PAGEID_THREADS) {
        		$scope.title = "Threads";
        		
    	        $scope.threadsTableParams = new ngTableParams({
    	            page: 1,            
    	            count: $rootScope.config.itemPerPage,          
    	            sorting: {
    	            	id: 'asc'
    	            }
    	        }, {
    	            total: 0,           
    	            getData: function($defer, params) {
    	            	$rootScope.showSpinner();
    	            	
    	            	var sort = Object.keys(params.$params.sorting)[0];
    	            	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
    	  
    	            	javaService.getThreads(params.page(), params.count(), sort, dir).then(function (data) {
    	                    params.total(data.total);
    	                    $defer.resolve(data.data);
    	                    
    	                    $rootScope.hideSpinner();
    	                }, function (error) {
    	                	$rootScope.hideSpinner();
    	                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
    	                });	
    	            }
    	        });
    	        
        	} else if($stateParams.pageid === PAGEID_SYSTEMPROPS) {
        		$scope.title = "System properties";
        		
        		$scope.systemPropertiesTableParams = new ngTableParams({
    	            page: 1,            
    	            count: $rootScope.config.itemPerPage,          
    	            sorting: {
    	            	key: 'asc'
    	            }
    	        }, {
    	            total: 0,           
    	            getData: function($defer, params) {
    	            	$rootScope.showSpinner();
    	            	
    	            	var sort = Object.keys(params.$params.sorting)[0];
    	            	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
    	            	
    	            	javaService.getSystemProperties(params.page(), params.count(), sort, dir).then(function (data) {
    	                    params.total(data.total);
    	                    $defer.resolve(data.data);
    	                    
    	                    $rootScope.hideSpinner();
    	                }, function (error) {
    	                	$rootScope.hideSpinner();
    	                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
    	                });	
    	            }
    	        });
        		
        	} else if($stateParams.pageid === PAGEID_APPPROPS) {
        		$scope.title = "Application properties";
        		
        		$scope.appPropertiesTableParams = new ngTableParams({
                    page: 1,            
                    count: $rootScope.config.itemPerPage,          
                    sorting: {
                    	key: 'asc'
                    }
                }, {
                    total: 0,           
                    getData: function($defer, params) {
                    	$rootScope.showSpinner();
                    	
                    	var sort = Object.keys(params.$params.sorting)[0];
                    	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
                    	
                    	javaService.getAppProperties(params.page(), params.count(), sort, dir).then(function (data) {
                            params.total(data.total);
                            $defer.resolve(data.data);
    	                    
    	                    $rootScope.hideSpinner();
                        }, function (error) {
    	                	$rootScope.hideSpinner();
    	                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                        });	
                    }
                });
        		
        	} else if($stateParams.pageid === PAGEID_JVMARGS) {
        		$scope.title = "JVM arguments";
        		
        		$scope.jvmArgumentsTableParams = new ngTableParams({
                    page: 1,            
                    count: $rootScope.config.itemPerPage,          
                    sorting: {
                    	key: 'asc'
                    }
                }, {
                    total: 0,           
                    getData: function($defer, params) {
                    	$rootScope.showSpinner();
                    	
                    	var sort = Object.keys(params.$params.sorting)[0];
                    	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
                    	
                    	javaService.getJVMArguments(params.page(), params.count(), sort, dir).then(function (data) {
                            params.total(data.total);
                            $defer.resolve(data.data);
    	                    
    	                    $rootScope.hideSpinner();
                        }, function (error) {
    	                	$rootScope.hideSpinner();
    	                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                        });	
                    }
                });	
        		
        	} else {
        		$rootScope.go("/");
        	}
    	};
    	
    	$scope.initialize();
    };

    javaController.$inject = injectParams;

    angular.module('SeafApp').controller('javaController', javaController);

}());