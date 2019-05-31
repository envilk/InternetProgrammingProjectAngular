angular.module('chollosApp')
.controller('headerCtrl', ['usersFactory',function(usersFactory){
	var headerViewModel = this;
	headerViewModel.user={};
	headerViewModel.newUser={id: -1,
			username: "", //This variable is for registering in the html form a new user, and I use this and not the user variable 
			email: "",					   //because user would not have defined and I couldnt use user.username, user.email and user.password
			password:""};
	headerViewModel.passwordConf="";
	headerViewModel.functions = {
			readUser : function() {
				usersFactory.getUser()
				.then(function(response){
					headerViewModel.user = response
					console.log("Getting user with id: ", headerViewModel.user.id," Response: ", response);
				}, function(response){
					console.log("Error reading user data");
				});
			},
			registerUser : function() {
				if(headerViewModel.passwordConf == headerViewModel.newUser.password){
					headerViewModel.user = headerViewModel.newUser;
					console.log(headerViewModel.user)
					usersFactory.postUser(headerViewModel.user)
					.then(function(response){
						console.log("Posting user with id: ", headerViewModel.newUser.id," Response: ", response);
					}, function(response){
						console.log("Error reading user data");
					});
					window.location = "/ProjectAngular";    //This is because the form is stuck in the same state all the time when this function returns
					return;									//This is only for returning the function
				}
				else
					console.log("DIFFERENT PASSWORDS");
			},
			editUser : function() {
				if(headerViewModel.newUser.email !== "")
					headerViewModel.user.email = headerViewModel.newUser.email
				if(headerViewModel.newUser.password !== "")
					headerViewModel.user.password = headerViewModel.newUser.password
						usersFactory.putUser(headerViewModel.user)
						.then(function(response){
							console.log("Updating user with id: ", headerViewModel.user.id," Response: ", response);
						}, function(response){
							console.log("Error reading user data");
						});
				window.location = "/ProjectAngular";
				return;
			},
			deleteUser : function() {
				usersFactory.deleteUser()
				.then(function(response){
					console.log("Deleting user with id: ", headerViewModel.user.id," Response: ", response);
				}, function(response){
					console.log("Error reading user data");
				});
				window.location = "/ProjectAngular";
				return;
			}
	}
	return headerViewModel.functions.readUser();
}])