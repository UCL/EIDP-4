'use strict';

/**
 * @ngdoc directive
 * @name eidpngApp.directive:authDirective
 * @description
 * # authDirective
 */
angular.module('eidpngApp')
  .directive('authDirective', function () {
    return {
      restrict: 'C',
        link: function postLink(scope, element, attrs) {
            element.text('this is the authDirective directive');

            scope.$on('event:auth-loginRequired', function() {
                // redirect / show login!!
            });

            scope.$on('event:auth-loginConfirmed', function() {
                // redirect / show main section
            });
      }
    };
  });
