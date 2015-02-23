'use strict';

describe("Budgeto module", function() {
    beforeEach(module('budgeto'));

    var $location;
    var $rootScope;

    beforeEach(inject(function(_$location_, _$rootScope_){
        $location = _$location_;
        $rootScope = _$rootScope_;
      }));

  it('constant BudgetoRestApiURL must be correct', inject(function(BudgetoRestApiURL) {
        expect(BudgetoRestApiURL).toEqual('http://localhost:9001/budgeto-api');
      }));

  it('constant moment tz must be correct', inject(function(angularMomentConfig) {
        expect(angularMomentConfig).toEqual({timezone: 'UTC'});
      }));

  it('messages defined correctly', inject(function(MessageService) {
        expect(Object.keys(MessageService).length).toBe(10);
        expect(Object.keys(MessageService.apisLinks).length).toBe(2);
        expect(Object.keys(MessageService.apisTitles).length).toBe(2);
      }));

  it('application run redirecting to /loading and keep source path', inject(function(MessageService) {
        spyOn($location, 'path');
        spyOn($location, 'search');

        // call run
        var myModule = angular.module('budgeto');
        myModule._runBlocks[0][3]($location, $rootScope, MessageService);

        expect($location.path).toHaveBeenCalledWith();
        expect($location.search).toHaveBeenCalledWith('sourcePage', $location.path());
        expect($location.path).toHaveBeenCalledWith('/loading');
      }));

  it('application run set MessageService in rootScope', inject(function(MessageService) {

        // call run
        var myModule = angular.module('budgeto');
        myModule._runBlocks[0][3]($location, $rootScope, MessageService);

        expect($rootScope.MessageService).toBe(MessageService);
      }));
});