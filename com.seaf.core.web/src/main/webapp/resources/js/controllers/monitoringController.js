(function () {

    var injectParams 			= ['$scope', '$rootScope', '$stateParams',  '$filter', 'javaService'],
    	CHARTID_CPU		 		= "CPU",
     	CHARTID_MEMORY 			= "Memory",
     	PERIODICITY_REALTIME	= "REALTIME",
     	PERIODICITY_HOURLY		= "HOURLY",
     	PERIODICITY_DAILY		= "DAILY",
     	PERIODICITY_WEEKLY		= "WEEKLY",
     	PERIODICITY_MONTHLY		= "MONTHLY";
    
    
    var monitoringController = function ($scope, $rootScope, $stateParams, $filter, javaService) {

  	  	$scope.chartid		= $stateParams.chartid;
  	  	$scope.filter		= {};
  	  	
  	  	$scope.heapData		= [
  	                              {
  	                                   "key": "Used",
  	                                   "values": [ ],
  	                                   "area": true
  	                              },
  	                              {
  	                                  "key": "Total",
  	                                  "values": [ ]
  	                              },
  	                              {
  	                                  "key": "Max",
  	                                  "values": [ ]
  	                              }];
  	  	$scope.cpuData		= [
	                              {
	                                   "key": "Process",
	                                   "values": [ ],
	                                   "area": true
	                              },
	                              {
	                                  "key": "System",
	                                  "values": [ ]
	                              }];
  	  	
  	  	$scope.buildCPUChart = function() {
  	  		$rootScope.showSpinner();
  	  		
  	     	javaService.getCpuMonitoring(
  	     			$scope.filter.periodicity,
  	     			$scope.filter.startDate, 
  	     			$scope.filter.endDate).then(function (data) {
  	     				
  	            $scope.cpuData[0].values = data.processCpuLoad;
  	            $scope.cpuData[1].values = data.systemCpuLoad;
  	            
  	            var chart = nv.models.lineChart()
  	             	.x(function(d) { return d[0] })   
  	                .y(function(d) { return d[1] })
  	                .showLegend(true)       
  	                .useInteractiveGuideline(true)
  	                .forceY(0);   
  	            
  	            chart.margin().left = 75;

  	            chart.xAxis
  	            	.axisLabel('Time')
  	            	.tickFormat(function(d) { 
  	            		if($scope.filter.periodicity == PERIODICITY_REALTIME || $scope.filter.periodicity == PERIODICITY_HOURLY) {
  	            			return d3.time.format('%X')(new Date(d)) ;
  	            		} else {
  	            			return d3.time.format('%Y-%m-%d')(new Date(d)) 	;
  	            		}
  	            });

  	            chart.yAxis 
  	            	.axisLabel('Cpu (%)')
  	            	.tickFormat(d3.format('.0%'));

  	            d3.select('#cpuDataChart svg')    
  	            	.datum($scope.cpuData)      
  	            	.call(chart);          

  	            nv.utils.windowResize(function() { chart.update() });
  	            
  	            $rootScope.hideSpinner();
  	            
  	        }, function (error) {
  	        	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
  	        });	
  	  	};
  	  	
  	  	$scope.buildMemoryChart = function() {
  	  		$rootScope.showSpinner();
  	  		
  	     	javaService.getHeapMonitoring(
  	     			$scope.filter.periodicity,
  	     			$scope.filter.startDate, 
  	     			$scope.filter.endDate).then(function (data) {

  	            $scope.heapData[0].values = data.usedMemory;
  	            $scope.heapData[1].values = data.totalMemory;
  	            $scope.heapData[2].values = data.maxMemory;
  	                        
  	            var chart = nv.models.lineChart()
  	             	.x(function(d) { return d[0] })   
  	                .y(function(d) { return d[1] })
  	                .showLegend(true)       
  	                .showYAxis(true)        
  	                .showXAxis(true)
  	                .useInteractiveGuideline(true)
  	                .forceY(0);  

  	            chart.margin().left = 75;
  	            
  	            chart.xAxis
  	            	.axisLabel('Time')
  	            	.tickFormat(function(d) { 
  	            		if($scope.filter.periodicity == PERIODICITY_REALTIME || $scope.filter.periodicity == PERIODICITY_HOURLY) {
  	            			return d3.time.format('%X')(new Date(d)) ;
  	            		} else {
  	            			return d3.time.format('%Y-%m-%d')(new Date(d)) 	;
  	            		}
  	            	});

  	            chart.yAxis 
  	            	.axisLabel('Memory (Mb)')
  	            	.tickFormat(d3.format('.02f'));

  	            d3.select('#heapDataChart svg')    
  	            	.datum($scope.heapData)      
  	            	.call(chart);          

  	            nv.utils.windowResize(function() { chart.update() });
  	            
  	          $rootScope.hideSpinner();
  	            
  	        }, function (error) {
  	        	$rootScope.displayAlert('danger', '[' + error.data.internalErrorCode + '] ' + error.data.message);
  	        });	
		};

  	  	$scope.setDateTime = function(periodicity) {
  	  		var endDate 	= new Date();
  	  		var now = new Date();
  	  		
  	  		if(periodicity == PERIODICITY_REALTIME) {
  	  	  		var startDate 	= now.setHours(now.getHours() - 1);
  	  	  		
  	  		} else if(periodicity == PERIODICITY_HOURLY) {
  	  	  		var startDate 	= now.setDate(now.getDate() - 1);
  	  	  		
  	  		} else if(periodicity == PERIODICITY_DAILY) {
  	  	  		var startDate 	= now.setDate(now.getDate() - 7);
  	  			
  	  		}  else if(periodicity == PERIODICITY_WEEKLY) {
  	  	  		var startDate 	= now.setMonth(now.getMonth() - 1);
  	  		
  	  		} else if(periodicity == PERIODICITY_MONTHLY) {
  	  	  		var startDate 	= now.setFullYear(now.getFullYear() - 1);
  	  		}

	  		$("#startDate").val($filter('date')(startDate, "yyyy-MM-dd HH:mm"));
	  		$("#endDate").val($filter('date')(endDate, "yyyy-MM-dd HH:mm"));
	  		$("#periodicity-input").val(periodicity);
	  		
	  		
	  		$scope.filter = {
		  						"periodicity"	:	periodicity,
		  						"startDate" 	:	$("#startDate").val(),
		  						"endDate" 		: 	$("#endDate").val()
		  					};
	  		
  	  	};
  	  	
  	  	$scope.refreshChart = function() {
	  		$scope.filter = {
				"periodicity"	:	$("#periodicity-input").val(),
				"startDate" 	:	$("#startDate").val(),
				"endDate" 		: 	$("#endDate").val()
			};

	    	 if($scope.chartid == CHARTID_MEMORY)
	    		 $scope.buildMemoryChart();
	    	 else
	    		 $scope.buildCPUChart();
  	  	};
  
  	  	
  	  	// INIT
    	 $("#"+$scope.chartid).addClass("active");
    	 $("#"+$scope.chartid+"Tab").addClass("active");

    	 $scope.setDateTime(PERIODICITY_REALTIME);

    	 if($scope.chartid == CHARTID_MEMORY)
    		 $scope.buildMemoryChart();
    	 else
    		 $scope.buildCPUChart();
    	 	
    };

    monitoringController.$inject = injectParams;

    angular.module('SeafApp').controller('monitoringController', monitoringController);

}());