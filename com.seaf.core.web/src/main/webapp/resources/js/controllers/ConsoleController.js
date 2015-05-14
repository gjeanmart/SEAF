(function () {

    var injectParams = ['$scope', '$rootScope', 'logService', 'sockJsClientService'];

    var consoleController = function ($scope, $rootScope, logService,sockJsClientService) {
    	
    	$scope.logFiles 	= [];
    	$scope.socket		= null;
    	
    	$scope.initialize = function() {
    		$rootScope.showSpinner();
    		
	       	 $scope.getLogFiles();
	    	 $scope.initSockets();
	    	 $scope.initLogTabs();
    	};
    	
    	$scope.getLogFiles		= function() {
    		logService.getLogFiles().then(function (result) {
    			$scope.logFiles = result.data;
    			$rootScope.hideSpinner();
            }, function (error) {
            	$rootScope.hideSpinner();
            	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
            });	
    	};
    	
    	$scope.selectLogFile = function () {
    		
    		var filePath = $scope.logFile;
    		var fileName = filePath.split("/")[filePath.split("/").length-1].split(".")[0];
			
    		if($("#tab_"+fileName).length === 0) {
        		sockJsClientService.sendMessage("start", $scope.logFile); 
        		
        		
        	  	$('<li><a href="#tab_'+fileName+'" data-toggle="tab"><button class="close closeTab" type="button">x</button>'+$scope.logFile+'</a></li>').appendTo('#tabs');
        	  	$('<div class="tab-pane console-table" id="tab_'+fileName+'"></div>').appendTo('.tab-content');
        	  	$('#tabs a:last').tab('show');
        	  	
        	  	$('#tabs a:last').click(function (e) {
        	  		e.preventDefault();
     		    	$(this).tab('show');
        	  	});
        	  	
        	    $("#tabs a:last button").click(function (e) {
        	    	e.preventDefault();
        	        var tabContentId = $(this).parent().attr("href");
        	        $(this).parent().parent().remove(); //remove li of tab
        	        $('#tabs a:last').tab('show'); // Select first tab
        	        $(tabContentId).remove(); //remove respective tab content
        	        var logFile = filePath;
        	        console.log(logFile);
        	        sockJsClientService.sendMessage("stop", logFile); 
        	    });
    		}

        }

        $scope.initSockets = function() {
        	sockJsClientService.initSockets();
        };
        
        $scope.initLogTabs = function() {
        	var buffers = sockJsClientService.getBuffers();
  
        	for(var key in buffers) {
        		var fileName = key.split("/")[key.split("/").length-1].split(".")[0];
        		
        	  	$('<li><a href="#tab_'+fileName+'" data-toggle="tab"><button class="close closeTab" type="button">x</button>'+key+'</a></li>').appendTo('#tabs');
        	  	$('<div class="tab-pane console-table" id="tab_'+fileName+'"></div>').appendTo('.tab-content');
        	  	$('#tabs a:last').tab('show');
        	  	
        	  	$('#tabs a:last').click(function (e) {
        	  		e.preventDefault();
     		    	$(this).tab('show');
        	  	});
        	  	
        	    $("#tabs a:last button").click(function (e) {
        	    	e.preventDefault();
        	        //there are multiple elements which has .closeTab icon so close the tab whose close icon is clicked
        	        var tabContentId = $(this).parent().attr("href");
        	        $(this).parent().parent().remove(); //remove li of tab
        	        $('#tabs a:last').tab('show'); // Select first tab
        	        $(tabContentId).remove(); //remove respective tab content
        	        var logFile = key;
        	        console.log(logFile);
        	        sockJsClientService.sendMessage("stop", logFile); 
  
        	    });
        	  	
        	  	var buffer = buffers[key];
            	for(var i = 0; i < buffer.arr.length; i++) {
            		var message = buffer.arr[i];
            		
                	var d = document.createElement('div');
                	var smessage = document.createElement('span');
                  
                	$(smessage).text(message).appendTo($(d));
                	$(d).appendTo("#tab_"+fileName);
                	$("#tab_"+fileName).scrollTop($("#tab_"+fileName)[0].scrollHeight);	  	
            	}
        	}
        };
        
        $scope.initialize();
    	 
    }
    
    consoleController.$inject = injectParams;

    angular.module('SeafApp').controller('consoleController', consoleController);

}());