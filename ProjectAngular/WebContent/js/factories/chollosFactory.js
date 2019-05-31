angular.module('chollosApp')
.factory("chollosFactory", ['$http',function($http){
	var url = 'https://localhost:8443/ProjectAngular/rest/chollos/';
	var chollosInterface = {
			getChollos: function(){
				return $http.get(url)
				.then(function(response){
					return response.data;
				});
			},
			getChollosByLikes: function(){
				var urlOrd = url + 'orderBylikes/';
				return $http.get(urlOrd)
				.then(function(response){
					return response.data;
				});
			},
			getChollosShowAvailable: function(){
				var urlOrd = url + 'showAvailable/';
				return $http.get(urlOrd)
				.then(function(response){
					return response.data;
				});
			},
			getChollosBySearch: function(search){
				var urlOrd = url + 'search/' + search;
				return $http.get(urlOrd)
				.then(function(response){
					return response.data;
				});
			},
			getChollo : function(id){
				var urlid = url + id;
				return $http.get(urlid)
				.then(function(response){
					console.log(response)
					return response.data;
				});
			},
			getChollosRelated : function(chollosid){
				var urlid = url + 'chollosRelated/' + chollosid;
				return $http.get(urlid)
				.then(function(response){
					return response.data;
				});
			},
			putChollo : function(chollo){
				var urlid = url+chollo.id;
				return $http.put(urlid, chollo)
				.then(function(response){
					return response.status;
				});                   
			},
			postChollo:  function(chollo){
				return $http.post(url,chollo)
				.then(function(response){
					return response.status;
				});
			}, 
			postCholloSoldout: function(cholloid){
				var urlid = url+'soldout/'+cholloid;
				return $http.put(urlid, cholloid)
				.then(function(response){
					return response.status;
				}); 
			},
			deleteChollo : function(id){
				var urlid = url+id;
				return $http.delete(urlid)
				.then(function(response){
					return response.status;
				});
			}				  
	}
	return chollosInterface;
}])