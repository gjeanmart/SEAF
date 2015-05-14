(function () {

    var injectParams = ['$http', '$q', 'Base64'];

    var loggerFactory = function ($http, $q, Base64) {
        var serviceBase = 'http://localhost:8080/com.seaf.core.service/api/v1/logger/',
        	serviceLogBase = 'http://localhost:8080/com.seaf.core.service/api/v1/log/',
        	username = "admin",
        	password = "secret", //TODO A proteger
            factory = {};

        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode(username + ':' + password);

        factory.changeLogLevel = function (loggerName, logLevel) {
            return $http.put(serviceBase + loggerName + "/" + logLevel).then(function (status) {
                return status.data;
            });
        };
        
        factory.getLogger = function (page, size, sort, dir) {
            return $http.get(serviceBase
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
            	return response.data;
            });
        }
        
        factory.getLogFiles = function () {
            return $http.get(serviceLogBase).then(function (response) {
                return response;
            });
        };
        
        factory.startTailer = function (logFile) {
            return $http.post(serviceLogBase, logFile).then(function (status) {
                return status.data;
            });
        };

        return factory;
    };

    loggerFactory.$inject = injectParams;

    angular.module('SeafApp').factory('logService', loggerFactory);

}());