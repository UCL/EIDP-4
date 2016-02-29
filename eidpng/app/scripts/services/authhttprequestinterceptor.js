'use strict';

function AuthHttpRequestInterceptorFx($rootScope, $location, $injector) {
    var authFactory = $injector.get('authFactory');

    var authHttpRequestInterceptor = {
        
        request: function($request) {
            
            if (authFactory.isAuthenticated()) {
                $request.headers['auth-id'] = authFactory.getAuthData().authId;
                $request.headers['auth-token'] = authFactory.getAuthData().authToken;
            } 
            return $request;
        },
        response: function($response) {
            if (!authFactory.isAuthenticated()) {
                $location.path('/login');
            }
            return $response;
        }
    };
 
    return authHttpRequestInterceptor;
}

/**
 * @ngdoc service
 * @name eidpngApp.authHttpRequestInterceptor
 * @description
 * # authHttpRequestInterceptor
 * Factory in the eidpngApp.
 */
angular.module('eidpngApp').factory('authHttpRequestInterceptor', ['$rootScope', '$location', '$injector', AuthHttpRequestInterceptorFx]);
