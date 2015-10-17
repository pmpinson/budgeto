'use strict';

/* https://github.com/angular/protractor/blob/master/docs/toc.md */

describe('budgeto', function() {

  it('should open index index.html', function() {
    browser.get('index.html');

    expect(browser.getLocationAbsUrl()).toMatch('');
    expect(element.all(by.css('.container')).count()).toEqual(1);
  });


  describe('home', function() {

    it('should open home page', function() {
      browser.get('index.html');

      expect(element(by.css('.page-header>h1')).getText()).toEqual('Welcome to budgeto');
    });

  });
});