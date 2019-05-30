angular.module('chollosApp')
.controller('chollosUserCtrl', ['chollosFactory','usersFactory',function(chollosFactory,usersFactory){
	var chollosUserViewModel = this;
	chollosUserViewModel.user={};
	chollosUserViewModel.chollosUser=[];
	chollosUserViewModel.aux=[];
	chollosUserViewModel.functions = {
			readChollosUser : function() {
				usersFactory.getUser()
				.then(function(response){
					chollosUserViewModel.user = response;
					chollosFactory.getChollos()
					.then(function(response){
						chollosUserViewModel.aux = response;
						for(i = 0; i < chollosUserViewModel.aux.length; i++){
							if(chollosUserViewModel.aux[i].idu == chollosUserViewModel.user.id)
								chollosUserViewModel.chollosUser.push(chollosUserViewModel.aux[i]);
						}
					})
					console.log("Reading chollos user");
				}, function(response){
					console.log("Error reading user data");
				})
			}
	}
	chollosUserViewModel.functions.readChollosUser();
}])