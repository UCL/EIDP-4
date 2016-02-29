'use strict';

function AuthFactoryFx($rootScope, $http) {

    var authFactory = {
        authData: undefined
    };

    authFactory.login = function(user) {
        return $http.post('http://localhost/api/auth',user);
    };

    authFactory.setAuthData = function(authData) {
        this.authData = {
            authId: authData.authId,
            authToken: authData.authToken,
            authPermission: authData.authPermission
        };
        $rootScope.$broadcast('authChanged');
    };

    authFactory.getAuthData = function() {
        return this.authData;
    };

    authFactory.isAuthenticated = function () {
        return !angular.isUndefined(this.getAuthData());
    };

    return authFactory;
}

/**
 * @ngdoc service
 * @name eidpngApp.authFactory
 * @description
 * # authFactory
 * Factory in the eidpngApp.
 */
angular.module('eidpngApp').factory('authFactory', ['$rootScope', AuthFactoryFx]);
