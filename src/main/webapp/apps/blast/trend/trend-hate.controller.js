// code style: https://github.com/johnpapa/angular-styleguide 

(function() {
    'use strict';
    angular
      .module('app')
      .controller('TrendHateController', TrendHateController);
    
    TrendHateController.$inject = ['$controller', '$scope', 'Keyword', 'FeedItem'];

      function TrendHateController($controller, $scope, Keyword, FeedItem){
    	  var vm = this;
			
	  		vm.itemTopKeywordFriendLike = [];
	        vm.itemTopKeywordFriendHate = [];
	        vm.itemTopKeywordAllLike = [];
	        vm.itemTopKeywordAllHate = [];
	        
			// Init        
			(function initController() {
				// instantiate base controller
				$controller('BlastBaseController', {
					vm : vm
				});
				
		        // Like
				topKeywordFriend(0);
		        topKeywordAll(0);
			})();
			
			// Document ready
			angular.element(document).ready(function() {
				
			});
			
			function topKeywordAll(status) {
				FeedItem.topKeywordByAll({
	                page: 0,
	                size: 9, //
	                sort: sort(),
	                status: status
	            }, onSuccess, onError);
	            function sort() {
	                return "id,desc";
	            }
	            function onSuccess(data, headers) {
  	            	if (status == 0) {
  	            		vm.itemTopKeywordAllHate = data;
  	            	} else {
  	            		vm.itemTopKeywordAllLike = data;
  	            	}
	            }
	            function onError(error) {
	                //AlertService.error(error.data.message);
	            }
			}
			
			function topKeywordFriend(status) {
				FeedItem.topKeywordByFriend({
	                page: 0,
	                size: 2, //
	                sort: sort(),
	                status: status
	            }, onSuccess, onError);
	            function sort() {
	            		return "id,desc";
	            }
	            function onSuccess(data, headers) {
  	            	if (status == 0) {
  	            		vm.itemTopKeywordFriendHate = data;
  	            		updateKeywordInfo(vm.itemTopKeywordFriendHate);
  	            	} else {
  	            		vm.itemTopKeywordFriendLike = data;
  	            		updateKeywordInfo(vm.itemTopKeywordFriendLike);
  	            	}
	            }
	            function onError(error) {
	                //AlertService.error(error.data.message);
	            }
			}
			
			function updateKeywordInfo(data) {
				 angular.forEach(data, function(value, key){
					 Keyword.info({name: value.name}, onSuccessInfo, onErrorInfo);
			            function onSuccessInfo(data, headers) {
			            		value.keywordInfo = data;
			            }
			            function onErrorInfo(error) {
			                //AlertService.error(error.data.message);
			            }
				 });
			}
      }
      
})();
