(function () {

    var injectParams = [];

    var sockJsClientService = function () {
        var socket,
        	SOCKET_URL = "http://localhost:8080/com.seaf.core.service/log",
        	buffers = [],
        	BUFFER_SIZE = 500;

        this.initSockets = function() {
        	if(!socket) {
            	socket = new SockJS(SOCKET_URL);  
                
            	socket.onopen = function() { 
                	//console.log("SOCKJS : Open socket");
                };
                socket.onmessage = function(a) {
                	//console.log("SOCKJS : On message > " + a.data);
                	
                	var logFile = a.data.split("[###]")[0];
                	var logMessage = a.data.split("[###]")[1];

                	buffers[logFile].add(logMessage);
                	
                	var fileName = logFile.split("/")[logFile.split("/").length-1].split(".")[0];
                	
                	if($("#console-box").length > 0) { // If i'm on view Console
                    	var d = document.createElement('div');
                    	var smessage = document.createElement('span');
                      
                    	$(smessage).text(logMessage).appendTo($(d));
                    	$(d).appendTo("#tab_"+fileName);
                    	$("#tab_"+fileName).scrollTop($("#tab_"+fileName)[0].scrollHeight);	
                	}
                };
                socket.onclose = function() { 
                	//console.log("SOCKJS : Closed socket");
                };
                
                socket.onerror = function() { 
                	//console.log("SOCKJS : Error during transfer"); 
                	$scope.addAlertClientSideException('danger', "WebSocket : Error during transfer.");
                };	
        	};
        	
        	this.sendMessage = function (prefix, message) {
        		console.log("SOCKJS : sendMessage " + message + " | prefix = " + prefix); 
        		socket.send(prefix + " " + message);
        		
        		if(prefix === "start") {
        			buffers[message] = new CircularBuffer(BUFFER_SIZE);
        		} else {
        			delete buffers[message]; 
        		}
        	};
        	
        	this.getBuffers = function() {
        		return buffers;
        	};
        	
        	return socket;
        };
    };

    sockJsClientService.$inject = injectParams;

    angular.module('SeafApp').service('sockJsClientService', sockJsClientService);

}());