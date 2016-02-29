'use strict';

describe('Service: authHttpRequestInterceptor', function () {

  // load the service's module
  beforeEach(module('eidpngApp'));

  // instantiate service
  var authHttpRequestInterceptor;
  beforeEach(inject(function (_authHttpRequestInterceptor_) {
    authHttpRequestInterceptor = _authHttpRequestInterceptor_;
  }));

  it('should do something', function () {
    expect(!!authHttpRequestInterceptor).toBe(true);
  });

});
