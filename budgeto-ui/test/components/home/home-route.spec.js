'use strict';

define(['components/home/home-route', 'angular-mocks'], function() {

    describe('Budgeto home-route module', function () {
        var $state;

        beforeEach(function () {
            module('budgeto.home.route');

            inject(function (_$state_) {
                $state = _$state_;
            });
        });

        it('$stateProvider add route home', inject(function () {
            expect($state.get('home')).not.toBeNull();
            expect($state.get('home').url).toBeUndefined();
            expect($state.get('home').templateUrl).toBe('components/home/home.html');
            expect($state.get('home').controller).toBe('HomeCtrl as homeCtrl');
        }));
    });
});