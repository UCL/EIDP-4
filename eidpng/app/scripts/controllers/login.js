'use strict';

function LoginCtrlFx($scope, authFactory) {
    $scope.login = function(user) {
        authFactory.login(user).success(function(data) {
            authFactory.setAuthData(data);
            // Redirect etc.
        }).error(function() {
            // Error handling
        });
    };
}

/**
 * @ngdoc function
 * @name eidpngApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the eidpngApp
 */
angular.module('login',[]).controller('LoginCtrl', ['$scope','authFactory', LoginCtrlFx]);

