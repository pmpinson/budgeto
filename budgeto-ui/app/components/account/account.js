'use strict';

// Declare module
angular.module('budgeto.account', [
  'ngRoute',
  'ngResource',
  'budgeto.common',
  'angularMoment'
])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/account', {
    templateUrl: 'components/account/account.html',
    controller: 'AccountCtrl'
  });
}])

.controller('AccountCtrl', ['$scope', 'AccountResource', 'OperationsResource', AccountCtrl])

.constant('AccountApi', 'account')

.factory('AccountResource', [ '$resource', 'AccountApi', 'ApiService', AccountResourceFactory ])

.factory('OperationsResource', [ '$resource', OperationsResourceFactory ]);

function AccountResourceFactory($resource, AccountApi, ApiService) {
    var api = ApiService.find(AccountApi);
    return $resource(api.href, {}, {});
}

function OperationsResourceFactory($resource) {
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
function AccountCtrl($scope, AccountResource, OperationsResource) {

  $scope.operations = [];
  $scope.accounts = AccountResource.query({}, null, function(data) {
    console.debug("get all account");
    if (data.length != 0) {
      $scope.account = data[0];
    };
  });

  $scope.formatDate = function(date) {
    return moment(date).format("ddd, hA");
  }

  $scope.$watch(
      function($scope) {
        return $scope.account
      }
      , function() {
        if ($scope.account !== undefined) {
          console.log("update account");
          $scope.operations = OperationsResource.get($scope.account).query({}, null);
        }
      }
  );
};
