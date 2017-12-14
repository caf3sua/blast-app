// code style: https://github.com/johnpapa/angular-styleguide 

(function() {
    'use strict';
    angular
      .module('app')
      .controller('HomeController', HomeController);
    
    HomeController.$inject = ['$scope', 'Principal'];

      function HomeController($scope, Principal){
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
//    		        loadItemByUserPost();
//    		        loadItemByUserLike();
//    		        loadItemByUserHate();
//    		        loadFollowingUser();
    		    })();
    			
    			function openDetail(feedId) {
    				FeedItemDetailDialogService.open(feedId);
    			}

    			function loadFollowingUser() {
    				SocialFollow.getFollowing({
    					providerId: 'facebook'
    	            }, onSuccess, onError);
    	            function onSuccess(data, headers) {
    	                vm.followingUsers = data;
    	                console.log(vm.loadFollowingUser);
    	            }
    	            function onError(error) {
    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserLike() {
    				FeedItem.queryByUserStatus({
    	                page: pagingParams.page - 1,
    	                size: vm.itemsPerPage,
    	                sort: sort(),
    	                status: 1
    	            }, onSuccess, onError);
    	            function sort() {
    	                var result = [vm.predicate + ',' + (vm.reverse ? 'desc' : 'asc')];
    	                if (vm.predicate !== 'id') {
    	                    result.push('id');
    	                }
    	                return result;
    	            }
    	            function onSuccess(data, headers) {
//    	                vm.links = ParseLinks.parse(headers('link'));
//    	                vm.totalItems = headers('X-Total-Count');
//    	                vm.queryCount = vm.totalItems;
    	                vm.itemUserLike = data;
//    	                vm.page = pagingParams.page;
    	            }
    	            function onError(error) {
    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserHate() {
    				FeedItem.queryByUserStatus({
    	                page: pagingParams.page - 1,
    	                size: vm.itemsPerPage,
    	                sort: sort(),
    	                status: 0
    	            }, onSuccess, onError);
    	            function sort() {
    	                var result = [vm.predicate + ',' + (vm.reverse ? 'desc' : 'asc')];
    	                if (vm.predicate !== 'id') {
    	                    result.push('id');
    	                }
    	                return result;
    	            }
    	            function onSuccess(data, headers) {
//    	                vm.links = ParseLinks.parse(headers('link'));
//    	                vm.totalItems = headers('X-Total-Count');
//    	                vm.queryCount = vm.totalItems;
    	                vm.itemUserHate = data;
//    	                vm.page = pagingParams.page;
    	            }
    	            function onError(error) {
    	                AlertService.error(error.data.message);
    	            }
    			}
    			
    			function loadItemByUserPost() {
    				FeedItem.queryByUserPost({
    	                page: pagingParams.page - 1,
    	                size: vm.itemsPerPage,
    	                sort: sort()
    	            }, onSuccess, onError);
    	            function sort() {
    	                var result = [vm.predicate + ',' + (vm.reverse ? 'desc' : 'asc')];
    	                if (vm.predicate !== 'id') {
    	                    result.push('id');
    	                }
    	                return result;
    	            }
    	            function onSuccess(data, headers) {
    	                vm.links = ParseLinks.parse(headers('link'));
    	                vm.totalItems = headers('X-Total-Count');
    	                vm.queryCount = vm.totalItems;
    	                vm.itemUserPost = data;
    	                vm.page = pagingParams.page;
    	                console.log(vm.itemUserPost);
    	            }
    	            function onError(error) {
    	                AlertService.error(error.data.message);
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
