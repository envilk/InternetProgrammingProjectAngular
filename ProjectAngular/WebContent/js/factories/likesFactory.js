angular.module('chollosApp')
.factory("likesFactory", ['$http',function($http){
	var url = 'https://localhost:8443/ProjectAngular/rest/likes/';
	var likesInterface = {
			postLike:  function(cholloId){
				console.log(cholloId)
				return $http.post(url, cholloId)
				.then(function(response){
					return response.status;
				});
			}, 			  
	}
	return likesInterface;
}])