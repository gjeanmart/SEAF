(function () {

    var injectParams = ['$scope', '$rootScope', '$routeParams', 'userService', 'ngTableParams'];

    var groupController = function ($scope, $rootScope, $routeParams, userService, ngTableParams) {
    	
    	$scope.editId 		= -1;
    	$scope.group		= {};
        
    	$scope.initialize = function() {
            $scope.groupsTableParams = new ngTableParams({
                page: 1,            
                count: $rootScope.config.itemPerPage,          
                sorting: {
                    id: 'desc'
                }
            }, {
                total: 0,           
                getData: function($defer, params) {
                	var searchString = params.$params.filter.name;
                	var sort = Object.keys(params.$params.sorting)[0];
                	var dir = params.$params.sorting[Object.keys(params.$params.sorting)[0]];
                	
                	$rootScope.showSpinner();
                	userService.getGroups(params.page(), params.count(), searchString, sort, dir).then(function (data) {
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

        $scope.setEditId = function (pid) {
        	$scope.editId = pid;
        };
          
    	$scope.saveEdit = function(group) {
    		$rootScope.showSpinner();
    		
        	userService.updateGroup(group).then(function (data) {
        		$scope.editId = -1;
        		$scope.groupsTableParams.reload();
        		$rootScope.displayAlert('success', "Group " + group.name + " has been successfuly edited.");
        	}, function (error) {
            	$rootScope.hideSpinner();
            	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
            });
    	};
          
    	$scope.cancelEdit = function() {
    		$scope.editId = -1;
    	};   
    	
    	$scope.deleteGroup = function(group) {
    		bootbox.confirm("Are you sure you want to delete group "+group.name + " ?", function(result) {
    			if(result) {
    				$rootScope.showSpinner();
    				
                	userService.deleteGroup(group.id).then(function (data) {
                		$scope.groupsTableParams.reload();
                		$rootScope.displayAlert('success', "Group " + group.name + " has been successfuly deleted.");
                	}, function (error) {
                    	$rootScope.hideSpinner();
                    	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                    });
    			}
    		}); 
    	};

    	$scope.addGroup = function(group) {
  
    	    if ($scope.groupForm.$valid) {
    	    	$rootScope.showSpinner();
    	    	
            	userService.insertGroup(group).then(function (data) {
            		$scope.groupsTableParams.reload();
            		$scope.reset();
            		$rootScope.displayAlert('success', "Group " + group.name+ " has been successfuly added.");
            	}, function (error) {
                	$rootScope.hideSpinner();
                	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
                });
    	    }
    	};
    	
    	$scope.reset = function() {
    		$scope.$broadcast('show-errors-reset');
    		$scope.group = null;
    	};
    	
    	$scope.initialize();

    };

    groupController.$inject = injectParams;

    angular.module('SeafApp').controller('groupController', groupController);

}());