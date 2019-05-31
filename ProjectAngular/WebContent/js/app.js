angular.module('chollosApp', ['ngRoute'])
.config(function($routeProvider){
	$routeProvider
	.when("/", {
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/chollosTemplate.html",
		resolve: {
			// produce 500 miliseconds (0,5 seconds) of delay that should be enough to allow the server
			//does any requested update before reading the Chollos.
			// Extracted from script.js used as example on https://docs.angularjs.org/api/ngRoute/service/$route
			delay: function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 250);
				return delay.promise;
			}
		}
	})
	.when("/myChollos", {
		controller: "chollosUserCtrl",
		controllerAs: "chollosUserVM",
		templateUrl: "./pages/chollosUser.html"
	})
	.when("/insertChollo", {
		controller: "cholloHandlerCtrl",
		controllerAs: "cholloHandlerVM",
		templateUrl: "./pages/cholloHandlerTemplate.html"
	})
	.when("/editChollo/:ID", {
		controller: "cholloHandlerCtrl",
		controllerAs: "cholloHandlerVM",
		templateUrl: "./pages/cholloHandlerTemplate.html"
	})
	.when("/deleteChollo/:ID", { 
		controller: "cholloHandlerCtrl",
		controllerAs: "cholloHandlerVM",
		templateUrl: "./pages/cholloHandlerTemplate.html"
	})
	.when("/likes/:ID", { 
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/chollosTemplate.html",
	})
	.when("/orderBylikes", { 
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/chollosTemplate.html",
	})
	.when("/showAvailable", {
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/chollosTemplate.html",
	})
	.when("/search/:STR", {
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/chollosTemplate.html",
	})
	.when("/setSoldout/:ID", {
		controller: "chollosUserCtrl",
		controllerAs: "chollosUserVM",
		templateUrl: "./pages/chollosUser.html"
	})
	.when("/viewChollo/:ID", {
		controller: "listCtrl",
		controllerAs: "listVM",
		templateUrl: "./pages/relatedChollos.html"
	});
})