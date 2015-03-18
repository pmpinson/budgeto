'use strict';

define(['components/tools/utils'], function(utils) {

    describe('Budgeto utils module', function () {

        describe('provider $utils', function () {

            it('initialised', function () {
                expect(utils).not.toBeNull();
            });

            it('isObject method return true if it is an object', function () {

                expect(utils.isObject({})).toBe(true);
                expect(utils.isObject({name:'value', val:12, sub:{label:'!!'}})).toBe(true);
            });

            it('isObject method return false if it is not an object', function () {

                expect(utils.isObject('4546546')).toBe(false);
                expect(utils.isObject(12)).toBe(false);
            });

            it('formatObject method return object formatted', function () {

                expect(utils.formatObject({})).toBe('{}');
                expect(utils.formatObject({name:'value', val:12, sub:{label:'!!'}})).toBe('{\n    "name": "value",\n    "val": 12,\n    "sub": {\n        "label": "!!"\n    }\n}');
                expect(utils.formatObject('4546546')).toBe('"4546546"');
                expect(utils.formatObject(12)).toBe('12');
            });
        });
    });
});