'use strict';

// Declare module
angular.module('budgeto.account', [
  'ngRoute',
  'ngResource'
])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/account', {
    templateUrl: 'components/account/account.html',
    controller: 'AccountCtrl'
  });
}])

.controller('AccountCtrl', ['$scope', 'AccountResource', 'OperationsResource', AccountCtrl])

.factory('AccountResource', [ '$resource', AccountResourceFactory ])

.factory('OperationsResource', [ '$resource', OperationsResourceFactory ]);

function AccountResourceFactory($resource) {
  return $resource('http://localhost:9001/budgeto-service/account', {}, {});
}

function OperationsResourceFactory(resource) {
  return resource('http://localhost:9001/budgeto-service/account/:name/operations', {}, {});
}

/**
 * controller to manage account
 * @param $scope current scope
 */
function AccountCtrl($scope, AccountResource, OperationsResource) {

  $scope.operations = [];
  $scope.accounts = AccountResource.query({}, null, function(data) {
    console.debug("get all account");
    if (data.length != 0) {
      $scope.account = data[0];
    };
  });

  $scope.$watch(
      function($scope) {
        return $scope.account
      }
      , function() {
        if ($scope.account !== undefined) {
          console.log("update account");
          $scope.operations = OperationsResource.query({name:$scope.account.name}, null);
        }
      }
  );
};
