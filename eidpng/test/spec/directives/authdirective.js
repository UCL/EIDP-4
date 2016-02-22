'use strict';

describe('Directive: authDirective', function () {

  // load the directive's module
  beforeEach(module('eidpngApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<auth-directive></auth-directive>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the authDirective directive');
  }));
});
