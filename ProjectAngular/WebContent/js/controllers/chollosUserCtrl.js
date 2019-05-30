angular.module('chollosApp')
.controller('chollosUserCtrl', ['chollosFactory','usersFactory','$location',function(chollosFactory,usersFactory,$location){
	var chollosUserViewModel = this;
	chollosUserViewModel.user={};
	chollosUserViewModel.chollosUser=[];
	chollosUserViewModel.aux=[];
	chollosUserViewModel.functions = {
			readChollosUser : function() {
				usersFactory.getUser()
				.then(function(response){
					chollosUserViewModel.user = response;
					console.log(chollosUserViewModel.user)
					chollosFactory.getChollos()
					.then(function(response){
						chollosUserViewModel.aux = response;
						for(i = 0; i < chollosUserViewModel.aux.length; i++){
							if(chollosUserViewModel.aux[i].idu == chollosUserViewModel.user.id){
								chollosUserViewModel.chollosUser.push(chollosUserViewModel.aux[i]);
							}
						}
					})
					console.log("Reading chollos user");
				}, function(response){
					console.log("Error reading user data");
				})
			},
			setCholloAsSoldout : function(cholloid) {
				chollosFactory.postCholloSoldout(cholloid)
				.then(function(response){
					console.log("Setting chollo soldout");
				}, function(response){
					console.log("Error setting chollo soldout");
				})
			}
	}
	var str = $location.path();
	if (str.includes("/setSoldout")){
		var array = str.split("/");
		chollosUserViewModel.functions.setCholloAsSoldout(array[2]);
		$location.path('/myChollos/');
	}
	chollosUserViewModel.functions.readChollosUser();
}])