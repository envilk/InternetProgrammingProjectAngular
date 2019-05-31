angular.module('chollosApp')
.controller('listCtrl', ['chollosFactory','likesFactory','$location',function(chollosFactory, likesFactory, $location){
	var listViewModel = this;
	listViewModel.chollos=[];
	listViewModel.cholloId={};
	listViewModel.viewChollo={};
	listViewModel.relatedChollos=[];
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
			likesFactory.postLike(cholloId)
			.then(function(response){
				console.log("Liked chollo: "+cholloId);
			}, function(response){
				console.log("Error in likes");
			})
		},
		viewChollo : function(cholloid) {
			chollosFactory.getChollo(cholloid)
			.then(function(response){
				listViewModel.viewChollo = response;
				console.log("Getting chollo with id: "+cholloid);
				console.log(listViewModel.viewChollo)
			}, function(response){
				console.log("Error getting chollo: "+cholloid);
			})
		},
		relatedChollos : function(cholloid) {
			chollosFactory.getChollosRelated(cholloid)
			.then(function(response){
				listViewModel.relatedChollos = response;
				console.log("Getting chollos by a search");
				console.log(listViewModel.relatedChollos)
			}, function(response){
				console.log("Error getting chollos by a search");
			})
		}
	}
	var str = $location.path();
	if (str.includes("/search")){
		var array = str.split("/");
		listViewModel.functions.readChollosBySearch(array[2]);
		$location.path('/search/'+array[2]);
	}
	if (str.includes("/likes/")){
		var array = str.split("/");
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
	if (str.includes("/viewChollo")){
		var array = str.split("/");
		listViewModel.functions.viewChollo(array[2]);
		listViewModel.functions.relatedChollos(array[2]);
		$location.path('/viewChollo/'+array[2]);
	}
	if(str.includes("/likes/") || str == "/")
		listViewModel.functions.readChollos();
}])