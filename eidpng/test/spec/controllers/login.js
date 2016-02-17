'use strict';

describe('Controller: LoginCtrl', function () {

    // load the controller's module
    beforeEach(module('login'));

    var createController, $httpBackend, $rootScope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($injector) {

        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');

        // Get hold of a scope (i.e. the root scope)
        $rootScope = $injector.get('$rootScope');

        // The $controller service is used to create instances of controllers
        var $controller = $injector.get('$controller');

        createController = function() {
            return $controller('LoginCtrl', {'$scope' : $rootScope });
        };
        
    }));

    it('should return 201 code', function() {

        createController();

	    var u = {username: 'username', password: 'password'};

	    $httpBackend.expectPOST('http://localhost/api/auth',u).respond(201,'');

        // Default code should be 401
        expect($rootScope.status).toBe(401);

        // After login is called, code should be 201
        $rootScope.login(u);
        $httpBackend.flush();
        expect($rootScope.status).toBe(201);
    });
});
