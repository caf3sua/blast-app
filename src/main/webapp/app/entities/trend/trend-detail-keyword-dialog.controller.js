(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('TrendDetailKeywordDialogController', TrendDetailKeywordDialogController);

    TrendDetailKeywordDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'feed', 'Trend', 'FeedItem', 'Keyword'];

    function TrendDetailKeywordDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, feed, Trend, FeedItem, Keyword ) {
        var vm = this;

        vm.trend = entity;
        vm.keyword = entity;
        vm.feedItem = feed;
        vm.clear = clear;
        vm.wikiInfo;
        vm.amazonItem = [];
        vm.searchItem = {};
        
        vm.itemUserPost = [];
        
        // settings
        vm.socials = [{
        	name: 'Facebook'
        	, enabled: false
        }
        , {
        	name: 'Twitter'
        	, enabled: true
        }
        , {
        	name: 'Google'
        	, enabled: true
        }];
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        // Init controller
		(function initController() {
			vm.searchItem.keyword = vm.keyword.name;
			console.log(vm.feedItem);

            loadKeywordInfo();
            loadItemByUserPost();
            
            loadWikiInfo();
            loadAmazonInfo();
	    })();
		
		function loadWikiInfo() {
			FeedItem.wikiInfo(vm.searchItem, onSuccessInfo, onErrorInfo);
            function onSuccessInfo(data, headers) {
            	angular.forEach(data, function(value, key){
            		vm.wikiInfo = value.extract;
   			 	});
            }
            function onErrorInfo(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function loadAmazonInfo() {
			FeedItem.amazonInfo(vm.searchItem, onSuccessInfo, onErrorInfo);
            function onSuccessInfo(data, headers) {
            	console.log(data);
            	angular.forEach(data, function(value, key){
            		vm.amazonItem = data;
   			 	});
            }
            function onErrorInfo(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function loadKeywordInfo() {
			Keyword.info({name: vm.keyword.name}, onSuccessInfo, onErrorInfo);
            function onSuccessInfo(data, headers) {
            	vm.feedItem.keywordInfo = data;
            }
            function onErrorInfo(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function loadItemByUserPost() {
			FeedItem.queryByUserPost({
                page: 0,
                size: 8,
                sort: 'id,desc'
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.itemUserPost = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
    }
})();
