(function () {

    var injectParams = ['$scope', '$rootScope', '$stateParams', '$modal',  'userService', 'ngTableParams'];

    var userController = function ($scope, $rootScope, $stateParams, $modal, userService, ngTableParams) {

        $scope.groups 		= [];
        $scope.user			= {};

        
		// Get groups
		userService.getGroups(1, 1000, null, "name","asc").then(function (result) {
			$scope.groups = result.data;
			
        }, function (error) {
        	$rootScope.hideSpinner();
        	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
        });	
        
    	$scope.initialize = function() {

    		// Go to user-detail/edit.html
    		if($stateParams.userid !== undefined) {
            	$rootScope.showSpinner();
            	
            	userService.getUser($stateParams.userid).then(function (data) {
                    $scope.user = data;

                    $rootScope.hideSpinner();
                    
                }, function (error) {
                	$rootScope.hideSpinner();
                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                });	
            

            // Go to user.html
    		} else {
    			
        		// Generate User's table
                $scope.usersTableParams = new ngTableParams({
                    page: 1,            
                    count: $rootScope.config.itemPerPage,          
                    sorting: {
                        id: 'desc'
                    }
                }, {
                    total: 0,           
                    getData: function($defer, params) {
                    	var searchString = params.$params.filter.lastName||params.$params.filter.firstName||params.$params.filter.email;
                    	var sort = Object.keys(params.$params.sorting)[0];
                    	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
                    	
                    	$rootScope.showSpinner();
                    	userService.getUsers(params.page(), params.count(), searchString, sort, dir).then(function (data) {
                            params.total(data.total);
                            $defer.resolve(data.data);
                            
                            $rootScope.hideSpinner();
                        }, function (error) {
                        	$rootScope.hideSpinner();
                        	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                        });	
                    }
                });
    		}
    	};

    	$scope.addUser = function(user) {

    	    if ($scope.userForm.$valid) {
    	    	$rootScope.showSpinner();
    	    	
            	userService.insertUser(user).then(function (data) {
            		$rootScope.hideSpinner();
            		
            		$scope.usersTableParams.reload();
            		$scope.reset();
 
            		$rootScope.displayAlert('success', "User " + user.firstName + " " + user.lastName + " has been successfuly added.");
            	}, function (error) {
                	$rootScope.hideSpinner();
                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                });
    	    }
    	};
          
    	$scope.editUser = function(user) {
    		
    	    if ($scope.userForm.$valid) {
    	    	$rootScope.showSpinner();
	    		
	        	userService.updateUser(user).then(function (data) {
	        		$rootScope.hideSpinner();
	        		$rootScope.displayAlert('success', "User " + user.firstName + " " + user.lastName + " has been successfuly edited.");
	        	}, function (error) {
	            	$rootScope.hideSpinner();
	            	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
	            });
    	    }
    	};  
    	
    	$scope.deleteUser = function(user) {
    		bootbox.confirm("Are you sure you want to delete user "+user.firstName + " " + user.lastName + " ?", function(result) {
    			if(result) {
    				$rootScope.showSpinner();
    				
                	userService.deleteUser(user.id).then(function (data) {
                		$rootScope.hideSpinner();
                		$scope.usersTableParams.reload();
                		$rootScope.displayAlert('success', "User " + user.firstName + " " + user.lastName + " has been successfuly deleted.");
                	}, function (error) {
                    	$rootScope.hideSpinner();
                    	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                    });
    			}
    		}); 
    	};
    	
    	$scope.reset = function() {
    		$scope.$broadcast('show-errors-reset');
    		$scope.user = null;
    	};
    	
    	
    	// INIT
    	$scope.initialize(); 
    	
    };

    userController.$inject = injectParams;

    angular.module('SeafApp').controller('userController', userController);
    
}());