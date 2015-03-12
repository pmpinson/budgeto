'use strict';

/* https://github.com/angular/protractor/blob/master/docs/toc.md */

describe('my app', function() {

  browser.get('index.html');

  it('should automatically redirect to /home when location hash/fragment is empty', function() {
    expect(browser.getLocationAbsUrl()).toMatch("/home");
  });


  describe('view1', function() {

    beforeEach(function() {
      browser.get('index.html');
    });


    it('should render home but search for element not exist', function() {
      expect(element.all(by.css('.container')).count()).toEqual(1);
    });

  });
});
