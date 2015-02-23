'use strict';

// Declare module
var budgetoAnalytics= angular.module('budgeto.analytics', [
    'ngRoute',
    'ngResource'
]);

budgetoAnalytics.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/analytics', {
            templateUrl: 'components/home/analytics.html',
            controller: 'AnalyticsCtrl'
        });
}]);

budgetoAnalytics.factory('LogsService', function($resource){
	return {
		load : function() {
			return $resource('/components/home/logs.json', {}, {
				get: {isArray:true}
			});
		}
	}
});

budgetoAnalytics.directive('analyticsChart', [function() {
    return {
        restrict: 'A',
        scope: {
            data: "="
        },
        link: function(scope, iElement, iAttrs) {

            scope.render = function(data) {

					d3.selectAll("svg > *").remove();

					var margin = {top: 40, right: 20, bottom: 30, left: 80};
                    var width = 500;
                    var height = 500;

					var x = d3.scale.ordinal()
						.rangeRoundBands([0, width], .2);

					var y = d3.scale.linear()
						.range([height, 0]);

					var xAxis = d3.svg.axis()
						.scale(x)
						.orient("bottom")
						.tickValues([]);

					var yAxis = d3.svg.axis()
						.scale(y)
						.orient("left");

					var chart = d3.select(".chart")
						.attr("width", 500 + margin.left + margin.right)
						.attr("height", 500 + margin.top + margin.bottom)
						.append("g")
						.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

					x.domain(data.map(function(d) { return d.date; }));
					y.domain(data.map(function(d) { return d.count; }));


					chart.append("g")
						.attr("class", "x axis")
						.attr("transform", "translate(0," + height + ")")
						.call(xAxis);

					chart.append("g")
						.attr("class", "y axis")
						.call(yAxis);

					var bar = chart.selectAll("rect")
						.data(data)
						.enter()
						.append("svg:rect")
						.attr("class", "bar")
						.attr("x", function(d) { return x(d.date); })
						.attr("y", function(d) { return y(d.count); })
						.attr("width", x.rangeBand())
						.style("fill", '#00FF00')
						.style("stroke-width",2)
						.style("stroke","white")
						.style("stroke-opacity",0.5)
						.style("stroke-linejoin","bevel");
//						.on('mouseover', tip.show)
//						.on('mouseout', tip.hide)
//						.on('click', tip.show);

            }
            scope.render(scope.data);
        }
    };
}]);

/**
 * controller to manage home page
 */
budgetoAnalytics.controller('AnalyticsCtrl', ['$scope', '$location', '$log', 'LogsService', function($scope, $location, $log, LogsService) {
    $log.debug('budgeto.home : load HomeCtrl');

//    $scope.logs = LogsService.load();


    $scope.logs = [];
    $scope.logs.push({date:'monday', url: '/realtime', user: 'admin', count:10});
    $scope.logs.push({date:'monday', url: '/audience', user: 'admin', count:12});
    $scope.logs.push({date:'tuesday', url: '/realtime', user: 'user1', count:20});
    $scope.logs.push({date:'tuesday', url: '/audience', user: 'user1', count:15});
}]);
