'use strict';

// Declare module
angular.module('budgeto.account', [
  'ngRoute',
  'ngResource',
  'budgeto.common',
  'angularMoment'
])

.config(['$routeProvider', function($routeProvider) {
    console.info("account : load $routeProvider");

  $routeProvider.when('/account', {
    templateUrl: 'components/account/account.html',
    controller: 'AccountCtrl'
  });
}])

.controller('AccountCtrl', ['$scope', '$location', 'AccountResource', 'OperationsResource', AccountCtrl])

.constant('AccountApiName', 'account')

.factory('AccountResource', [ '$resource', 'AccountApiName', 'ApiService', AccountResource ])

.factory('OperationsResource', [ '$resource', OperationsResource ]);

function AccountResource($resource, AccountApiName, ApiService) {
    console.info("account : load AccountResource");

    var api = ApiService.find(AccountApiName);
    return $resource("", {}, {});
}

function OperationsResource($resource) {
    console.info("account : load OperationsResource");

    return {
        get: function(account) {
            return $resource(getLink('operations', account.links).href, {}, {});
        }
    }
}

/**
 * controller to manage account
 * @param $scope current scope
 */
function AccountCtrl($scope, $location, AccountResource, OperationsResource) {
    console.info("account : load AccountCtrl");

//  $scope.operations = [];
//  $scope.accounts = AccountResource.query({}, null, function(data) {
//    console.debug("get all account");
//    if (data.length != 0) {
//      $scope.account = data[0];
//    };
//  });
//
//  $scope.formatDate = function(date) {
//    return moment(date).format("ddd, hA");
//  }
//
//  $scope.$watch(
//      function($scope) {
//        return $scope.account
//      }
//      , function() {
//        if ($scope.account !== undefined) {
//          console.log("update account");
//          $scope.operations = OperationsResource.get($scope.account).query({}, null);
//        }
//      }
//  );

  $scope.home = function() {
    $location.path('/');
  }
};
