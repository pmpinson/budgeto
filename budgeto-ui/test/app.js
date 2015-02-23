'use strict';

describe("Budgeto module", function() {
    var $log;
    var $location;
    var $rootScope;

    beforeEach(function() {
        module('budgeto');

        inject(function(_$log_, _$location_, _$rootScope_){
            $log = _$log_;
            $location = _$location_;
            $rootScope = _$rootScope_;
        });
    });

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
        myModule._runBlocks[0][4]($location, $rootScope, $log, MessageService);

        expect($location.path).toHaveBeenCalledWith();
        expect($location.search).toHaveBeenCalledWith('sourcePage', $location.path());
        expect($location.path).toHaveBeenCalledWith('/loading');
      }));

  it('application run set MessageService in rootScope', inject(function(MessageService) {

        // call run
        var myModule = angular.module('budgeto');
        myModule._runBlocks[0][4]($location, $rootScope, $log, MessageService);

        expect($rootScope.MessageService).toBe(MessageService);
      }));
});

describe("Budgeto module configuration providers configuration", function() {
    beforeEach(function() {
        module('budgeto');


        inject(function(){
        });
    });

    it('$infiniteLoader take wait message', inject(function($infiniteLoader) {
        expect($infiniteLoader.config().getMessage()).toBe('Work in progress. Pleas wait...');
    }));

    it('ApiServiceProvider take url', inject(function($infiniteLoader, ApiService) {
        expect(ApiService.config().getUrl()).toBe('http://localhost:9001/budgeto-api');
    }));

    it('LoadingService take services', inject(function(LoadingService) {
        expect(LoadingService.config().getServicesNames().length).toBe(1);
        expect(LoadingService.config().getServicesNames()).toContain('ApiService');
    }));
});