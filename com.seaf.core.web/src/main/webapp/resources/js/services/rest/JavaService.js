(function () {

    var injectParams = ['$http', '$q', 'Base64'];

    var javaFactory = function ($http, $q, Base64) {
        var serviceBaseThread = 'http://localhost:8080/com.seaf.core.service/api/v1/thread/',
        	serviceBaseSystemProperties = 'http://localhost:8080/com.seaf.core.service/api/v1/systemprops/',
        	serviceBaseJVMArguments = 'http://localhost:8080/com.seaf.core.service/api/v1/jvmarguments/',
        	serviceBaseAppProperties = 'http://localhost:8080/com.seaf.core.service/api/v1/appproperties/',
        	serviceBaseHeapMonitoring = 'http://localhost:8080/com.seaf.core.service/api/v1/heap/',
        	serviceBaseCpuMonitoring = 'http://localhost:8080/com.seaf.core.service/api/v1/cpu/',
        	username = "admin",
        	password = "secret", //TODO A proteger
            factory = {};

        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode(username + ':' + password);

        factory.getThreads = function (page, size, sort, dir) {
            return $http.get(serviceBaseThread
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
                return response.data;
            });
        };
        
        factory.getSystemProperties = function (page, size, sort, dir) {
            return $http.get(serviceBaseSystemProperties
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
                return response.data;
            });
        };
        
        factory.getJVMArguments = function (page, size, sort, dir) {
            return $http.get(serviceBaseJVMArguments
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
                return response.data;
            });
        };
        
        factory.getAppProperties = function (page, size, sort, dir) {
            return $http.get(serviceBaseAppProperties
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
                return response.data;
            });
        };
        
        factory.getHeapMonitoring = function (periodicity, startDate, endDate) {
            return $http.get(serviceBaseHeapMonitoring
            		+ '?periodicity=' + periodicity 
            		+ '&startDate=' + startDate 
            		+ '&endDate=' + endDate).then(function (response) {
                return response.data;
            });
        };
        
        factory.getCpuMonitoring = function (periodicity, startDate, endDate) {
            return $http.get(serviceBaseCpuMonitoring
            		+ '?periodicity=' + periodicity 
            		+ '&startDate=' + startDate 
            		+ '&endDate=' + endDate).then(function (response) {
                return response.data;
            });
        };
        
        return factory;
    };

    javaFactory.$inject = injectParams;

    angular.module('SeafApp').factory('javaService', javaFactory);

}());