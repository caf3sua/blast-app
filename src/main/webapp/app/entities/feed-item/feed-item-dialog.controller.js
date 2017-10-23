(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('FeedItemDialogController', FeedItemDialogController);

    FeedItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FeedItem'
    	, 'Principal', 'StatusItem', 'SocialFollow', 'AlertService'];

    function FeedItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FeedItem
    		, Principal, StatusItem, SocialFollow, AlertService) {
        var vm = this;

        vm.account = null;
        
        vm.feedItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.isLike = false;
        vm.isHate = false;
        vm.lstUserHate = [];
        vm.lstUserLike = [];
        
        vm.updateStatus = updateStatus;
        

        // Init controller
		(function initController() {
	        getAccount();
	        
	        checkStatusLikeAndHate();
	        
	        loadListUser();
	    })();
		
		function loadListUser() {
			FeedItem.info({id : vm.feedItem.id}, onSuccess, onError)
			function onSuccess(data, headers) {
				vm.feedItem = data;
				vm.lstUserHate = vm.feedItem.statusItem.lstUserUnlike;
				vm.lstUserLike = vm.feedItem.statusItem.lstUserLike;
				
				// Check following ?
				// SocialFollow.checkFollowing
				checkFollowing();
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function checkFollowing() {
			angular.forEach(vm.lstUserHate, function(user, key){
        		if (vm.account.id != user.id) {
        			SocialFollow.checkFollowing({providerId: user.providerId, providerUserId: user.providerUserId}, onSuccessInfo, onErrorInfo);
                    function onSuccessInfo(data, headers) {
                    	if (data != null && data.id != '' && data.id != null) {
                    		user.isFollowing = true;
                    	}
                    }
                    function onErrorInfo(error) {
                        AlertService.error(error.data.message);
                    }
        		}
			 });
			angular.forEach(vm.lstUserLike, function(user, key){
				if (vm.account.id != user.id) {
					SocialFollow.checkFollowing({providerId: user.providerId, providerUserId: user.providerUserId}, onSuccessInfo, onErrorInfo);
		            function onSuccessInfo(data, headers) {
		            	if (data != null) {
		            		user.isFollowing = true;
		            	}
		            }
		            function onErrorInfo(error) {
		                AlertService.error(error.data.message);
		            }
        		}
			 });
		}
		
		function updateStatus(status) {
			// status = 1: like
			if (status == 1) {
				StatusItem.like({id : vm.feedItem.id}, onSuccess, onError);
	            function onSuccess(data, headers) {
	            	vm.isLike = true;
	            	loadListUser()
	            }
	            function onError(error) {
	                AlertService.error(error.data.message);
	            }
			} else {
				StatusItem.hate({id : vm.feedItem.id}, onSuccess, onError);
	            function onSuccess(data, headers) {
	            	vm.isHate = true;
	            	loadListUser()
	            }
	            function onError(error) {
	                AlertService.error(error.data.message);
	            }
			}
		}
		
		function checkStatusLikeAndHate() {
			StatusItem.checkStatus({id : vm.feedItem.id}, onSuccess, onError);
            function onSuccess(data, headers) {
            	vm.isLike = data.like;
                vm.isHate = data.hate;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                var isAuth = Principal.isAuthenticated();
                if (vm.isAuthenticated == null || vm.isAuthenticated() == false) {
                	$state.go('signin');    	
                }
            });
        }
		
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.feedItem.id !== null) {
                FeedItem.update(vm.feedItem, onSaveSuccess, onSaveError);
            } else {
                FeedItem.save(vm.feedItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('blastApp:feedItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
