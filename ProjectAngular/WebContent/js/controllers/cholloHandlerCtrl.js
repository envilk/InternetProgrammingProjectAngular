angular.module('chollosApp')
.controller('cholloHandlerCtrl', ['chollosFactory','usersFactory','$routeParams','$location',
	function(chollosFactory,usersFactory,$routeParams,$location){
	var cholloHandlerViewModel = this;
	cholloHandlerViewModel.chollo={
			title: "",
			description: "",
			link: ""
	};
	cholloHandlerViewModel.newChollo={
			title: "",
			description: "",
			link: ""
	};
	cholloHandlerViewModel.functions = {
			where : function(route){
				return $location.path() == route;
			},
			readChollo : function(id) {
				chollosFactory.getChollo(id)
				.then(function(response){
					console.log("Reading chollo with id: ", id," Response: ", response);
					cholloHandlerViewModel.chollo = response;
				}, function(response){
					console.log("Error reading chollo");
					$location.path('/');
				})
			},
			updateChollo : function() {
				if(cholloHandlerViewModel.newChollo.title !== "")
					cholloHandlerViewModel.chollo.title = cholloHandlerViewModel.newChollo.title
				if(cholloHandlerViewModel.newChollo.description !== "")
					cholloHandlerViewModel.chollo.description = cholloHandlerViewModel.newChollo.description
				if(cholloHandlerViewModel.newChollo.link !== "")
					cholloHandlerViewModel.chollo.link = cholloHandlerViewModel.newChollo.link
				chollosFactory.putChollo(cholloHandlerViewModel.chollo)
				.then(function(response){
					console.log("Updating chollo with id:",cholloHandlerViewModel.chollo.id," Response:", response);
				}, function(response){
					console.log("Error updating chollo");
				})
			},	
			createChollo : function() {
				cholloHandlerViewModel.chollo.title = cholloHandlerViewModel.newChollo.title
				cholloHandlerViewModel.chollo.description = cholloHandlerViewModel.newChollo.description
				cholloHandlerViewModel.chollo.link = cholloHandlerViewModel.newChollo.link
				console.log(cholloHandlerViewModel.chollo)
				chollosFactory.postChollo(cholloHandlerViewModel.chollo)
				.then(function(response){
					console.log("Creating chollo. Response:", response);
				}, function(response){
					console.log("Error creating the chollo");
				})
			},
			deleteChollo : function(id) {
				chollosFactory.deleteChollo(id)
				.then(function(response){
					console.log("Deleting chollo with id:",id," Response:", response);
				}, function(response){
					console.log("Error deleting chollo");
				})
			},
			cholloHandlerSwitcher : function(){
				if (cholloHandlerViewModel.functions.where('/insertChollo')){
					console.log($location.path());
					cholloHandlerViewModel.functions.createChollo();
				}
				else if (cholloHandlerViewModel.functions.where('/editChollo/'+cholloHandlerViewModel.chollo.id)){
					console.log($location.path());
					cholloHandlerViewModel.functions.updateChollo();
				}
				else if (cholloHandlerViewModel.functions.where('/deleteChollo/'+cholloHandlerViewModel.chollo.id)){
					console.log($location.path());
					cholloHandlerViewModel.functions.deleteChollo(cholloHandlerViewModel.chollo.id);
				}
				else {
					console.log($location.path());
				}
				$location.path('/');
			}
	}
	console.log("Entering cholloHandlerCtrl with $routeParams.ID=",$routeParams.ID);
	if ($routeParams.ID!=undefined) cholloHandlerViewModel.functions.readChollo($routeParams.ID);
}]);