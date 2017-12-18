// code style: https://github.com/johnpapa/angular-styleguide 

(function() {
    'use strict';
    angular
      .module('app')
      .controller('HomeController', HomeController);
    
    HomeController.$inject = ['$scope', 'Principal', 'FeedItem', 'SocialFollow'];

      function HomeController($scope, Principal, FeedItem, SocialFollow){
    	  		var vm = this;
    	  		
    	  		vm.account = null;
    	        vm.isAuthenticated = null;
    	        vm.itemUserPost = [];
    	        vm.itemUserLike = [];
    	        vm.itemUserHate = [];
    	        vm.followingUsers = [];
    	        
    	        // Init controller
    			(function initController() {
    		        getAccount();
    		        loadItemByUserPost();
    		        loadItemByUserLike();
    		        loadItemByUserHate();
    		        loadFollowingUser();
    		    })();
    			
//    			function openDetail(feedId) {
//    				FeedItemDetailDialogService.open(feedId);
//    			}

    			function loadFollowingUser() {
    				SocialFollow.getFollowing({
    					providerId: 'facebook'
    	            }, onSuccess, onError);
    	            function onSuccess(data, headers) {
    	                vm.followingUsers = data;
    	                console.log(vm.followingUsers);
    	            }
    	            function onError(error) {
    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserLike() {
    				FeedItem.queryByUserStatus({
    	                page: 0,
    	                size: 12,
    	                sort: sort(),
    	                status: 1
    	            }, onSuccess, onError);
    	            function sort() {
    	                return "id,desc";
    	            }
    	            function onSuccess(data, headers) {
    	                vm.itemUserLike = data;
    	            }
    	            function onError(error) {
//    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserHate() {
    				FeedItem.queryByUserStatus({
    	                page: 0,
    	                size: 12,
    	                sort: sort(),
    	                status: 0
    	            }, onSuccess, onError);
    	            function sort() {
    	            		return "id,desc";
    	            }
    	            function onSuccess(data, headers) {
    	                vm.itemUserHate = data;
    	            }
    	            function onError(error) {
//    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserPost() {
    				FeedItem.queryByUserPost({
    	                page: 0,
    	                size: 12,
    	                sort: sort()
    	            }, onSuccess, onError);
    	            function sort() {
    	                return "id,desc";
    	            }
    	            function onSuccess(data, headers) {
    	                vm.itemUserPost = data;
    	                console.log(vm.itemUserPost);
    	            }
    	            function onError(error) {
//    	                AlertService.error(error.data.message);
    	            }
    			}

    	        function getAccount() {
    	            Principal.identity().then(function(account) {
    	                vm.account = account;
    	                vm.isAuthenticated = Principal.isAuthenticated;
    	                console.log(vm.account);
    	            });
    	        }
      }
      
})();
