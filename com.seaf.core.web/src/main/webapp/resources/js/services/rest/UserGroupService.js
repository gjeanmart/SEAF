(function () {

    var injectParams = ['$http', '$q', 'Base64'];

    var userFactory = function ($http, $q, Base64) {
        var serviceBaseUser = 'http://localhost:8080/com.seaf.core.service/api/v1/user/',
        	serviceBaseGroup = 'http://localhost:8080/com.seaf.core.service/api/v1/group/',
        	username = "admin",
        	password = "secret", //TODO A proteger
            factory = {};

        $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode(username + ':' + password);

        factory.insertUser = function (user) {
            return $http.post(serviceBaseUser, user).then(function (results) {
            	user.id = results.data.id;
                return results.data;
            });
        };

        factory.updateUser = function (user) {
            return $http.put(serviceBaseUser + user.id, user).then(function (status) {
                return status.data;
            });
        };

        factory.deleteUser = function (id) {
            return $http.delete(serviceBaseUser + id).then(function (status) {
                return status.data;
            });
        };

        factory.getUser = function (id) {
            return $http.get(serviceBaseUser + id).then(function (results) {
                return results.data;
            });
        };

        factory.getUsers = function (page, size, search, sort, dir) {
        	if(search === undefined||search === null)
        		search = '';
        	
            return $http.get(serviceBaseUser 
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&search=' + search
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
            	return response.data;
            });
        };
        
        factory.insertGroup = function (group) {
            return $http.post(serviceBaseGroup, group).then(function (results) {
            	group.id = results.data.id;
                return results.data;
            });
        };

        factory.updateGroup = function (group) {
            return $http.put(serviceBaseGroup + group.id, group).then(function (status) {
                return status.data;
            });
        };

        factory.deleteGroup = function (id) {
            return $http.delete(serviceBaseGroup + id).then(function (status) {
                return status.data;
            });
        };

        factory.getGroup = function (id) {
            return $http.get(serviceBaseGroup + id).then(function (results) {
                return results.data;
            });
        };

        factory.getGroups = function (page, size, search, sort, dir) {
        	if(search === undefined||search === null)
        		search = '';
        	
            return $http.get(serviceBaseGroup 
            		+ '?page=' + page 
            		+ '&size=' + size 
            		+ '&search=' + search
            		+ '&sort=' + sort
            		+ '&dir=' + dir).then(function (response) {
            	return response.data;
            });
        };
        
        factory.subscribeGroup = function (userid, groupid) {
            return $http.put(serviceBaseUser + userid + "/group/" + groupid).then(function (status) {
                return status.data;
            });
        };
        
        factory.unsubscribeGroup = function (userid, groupid) {
            return $http.delete(serviceBaseUser + userid + "/group/" + groupid).then(function (status) {
                return status.data;
            });
        };        

        return factory;
    };

    userFactory.$inject = injectParams;

    angular.module('SeafApp').factory('userService', userFactory);

}());