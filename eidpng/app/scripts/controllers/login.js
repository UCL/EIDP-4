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
    $scope.login = function(user) {
      $http.post('http://localhost/api/auth/',user).success(function() {
        authService.loginConfirmed();
      });
    };

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
