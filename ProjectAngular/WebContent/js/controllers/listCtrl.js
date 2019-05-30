angular.module('chollosApp')
.controller('listCtrl', ['chollosFactory','likesFactory','$location',function(chollosFactory, likesFactory, $location){
	var listViewModel = this;
	listViewModel.chollos=[];
	listViewModel.cholloId={}
	console.log("HERE1")
	listViewModel.functions = {
		where : function(route){
			return $location.path() == route;
		},
		readChollos : function() {
			chollosFactory.getChollos()
			.then(function(response){
				console.log("Reading all the chollos:--------------------------------------- ", response);
				listViewModel.chollos = response;
			}, function(response){
				console.log("Error reading chollos----------------------------------------------");
			})
		},
		readChollosOrderByLikes : function() {
			chollosFactory.getChollosByLikes()
			.then(function(response){
				console.log("Reading all the chollos ordered by likes:--------------------------------------- ", response);
				listViewModel.chollos = response;
			}, function(response){
				console.log("Error reading chollos----------------------------------------------");
			})
		},
		readChollosShowAvailable : function() {
			chollosFactory.getChollosShowAvailable()
			.then(function(response){
				console.log("Reading all the chollos showgind available:--------------------------------------- ", response);
				listViewModel.chollos = response;
			}, function(response){
				console.log("Error reading chollos----------------------------------------------");
			})
		},
		readChollosBySearch : function(search) {
			chollosFactory.getChollosBySearch(search)
			.then(function(response){
				console.log("Reading all the chollos by search:--------------------------------------- ", response);
				listViewModel.chollos = response;
			}, function(response){
				console.log("Error reading chollos----------------------------------------------");
			})
		},
		createLike : function(cholloId) {
			console.log("HERE2")
			console.log(cholloId)
			likesFactory.postLike(cholloId)
			.then(function(response){
				console.log("Liked chollo: "+cholloId);
			}, function(response){
				console.log("Error in likes");
			})
		}
	}
	var str = $location.path();
	console.log("HERE3")
	console.log(str)
	if (str.includes("/search")){
		var array = str.split("/");
		console.log(array[2])
		listViewModel.functions.readChollosBySearch(array[2]);
		$location.path('/search/'+array[2]);
	}
	if (str.includes("/likes/")){
		var array = str.split("/");
		console.log(array[2])
		listViewModel.functions.createLike(array[2]);
		$location.path('/');
	}
	if (str.includes("/orderBylikes")){
		listViewModel.functions.readChollosOrderByLikes();
		$location.path('/orderBylikes');
	}
	if (str.includes("/showAvailable")){
		listViewModel.functions.readChollosShowAvailable();
		$location.path('/showAvailable');
	}
	if(str.includes("/likes/") || str == "/")
		listViewModel.functions.readChollos();
}])