'use strict';

/**
 * @ngdoc function
 * @name eidpngApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the eidpngApp
 */
angular.module('login',['http-auth-interceptor'])
    .controller('LoginCtrl', function ($scope, $http, authService) {

        $scope.status = 401;

        $scope.login = function(data) {
            
            $http.post('http://localhost/api/auth', data, {}).then(function(response) {
                $scope.status = response.status;
                authService.loginConfirmed();
            }).catch(function(){
                $scope.status = 401;
            });
            
        };

    });
