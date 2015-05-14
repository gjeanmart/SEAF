(function () {

    var injectParams = ['$scope', '$rootScope', 'logService', 'ngTableParams'];

    var loggerController = function ($scope, $rootScope, logService, ngTableParams) {
    	
    	$scope.initialize = function() {
            $scope.loggerTableParams = new ngTableParams({
                page	: 1,            
                count	: $rootScope.config.itemPerPage,          
                sorting	: {
                	loggerName	: 'asc'
                }
            }, {
                total	: 0,           
                getData	: function($defer, params) {
                	$rootScope.showSpinner();
                	
                	var sort = Object.keys(params.$params.sorting)[0];
                	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
                	
                	logService.getLogger(params.page(), params.count(), sort, dir).then(function (data) {
                        params.total(data.total);
                        $defer.resolve(data.data);
                        
                        $rootScope.hideSpinner();
                    }, function (error) {
	                	$rootScope.hideSpinner();
	                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                    });	
                }
            });
    	};

    	$scope.changeLogLevel = function(loggerName, logLevel) {
    		$rootScope.showSpinner();
    		
           	logService.changeLogLevel(loggerName, logLevel).then(function (data) {
           		$scope.loggerTableParams.reload();
           		
           		$rootScope.hideSpinner();
            }, function (error) {
            	$rootScope.hideSpinner();
            	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
            });	
    	};
    	
    	$scope.initialize();
    };

    loggerController.$inject = injectParams;

    angular.module('SeafApp').controller('loggerController', loggerController);

}());