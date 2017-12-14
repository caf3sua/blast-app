(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('TrendController', TrendController);

    TrendController.$inject = ['$state', 'Trend', 'FeedItem', 'Keyword', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'FeedItemDetailDialogService'];

    function TrendController($state, Trend, FeedItem, Keyword, ParseLinks, AlertService, paginationConstants, pagingParams, FeedItemDetailDialogService) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        
        vm.itemTrendFriendLike = [];
        vm.itemTrendFriendHate = [];
        vm.itemTrendAllLike = [];
        vm.itemTrendAllHate = [];
        
        vm.itemTopKeywordFriendLike = [];
        vm.itemTopKeywordFriendHate = [];
        vm.itemTopKeywordAllLike = [];
        vm.itemTopKeywordAllHate = [];
        
        vm.openDetail = openDetail;

        // Init controller
		(function initController() {
	        // Like
			topKeywordFriend(1);
	        topKeywordAll(1);
	        // Hate
	        topKeywordFriend(0);
	        topKeywordAll(0);
	    })();
		
		function openDetail(feedId) {
			FeedItemDetailDialogService.open(feedId);
		}
		
		function topKeywordAll(status) {
			FeedItem.topKeywordByAll({
                page: pagingParams.page - 1,
                size: 9, //
                sort: sort(),
                status: status
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
            	if (status == 0) {
            		vm.itemTopKeywordAllHate = data;
            	} else {
            		vm.itemTopKeywordAllLike = data;
            	}
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function topKeywordFriend(status) {
			FeedItem.topKeywordByFriend({
                page: pagingParams.page - 1,
                size: 2, //
                sort: sort(),
                status: status
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
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
                AlertService.error(error.data.message);
            }
		}
		
		function updateKeywordInfo(data) {
			 angular.forEach(data, function(value, key){
				 Keyword.info({name: value.name}, onSuccessInfo, onErrorInfo);
		            function onSuccessInfo(data, headers) {
		            	value.keywordInfo = data;
		            }
		            function onErrorInfo(error) {
		                AlertService.error(error.data.message);
		            }
			 });
		}
		
		function trendFriend(status) {
			FeedItem.trendFriend({
                page: pagingParams.page - 1,
                size: 2, //
                sort: sort(),
                status: status
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
            	if (status == 0) {
            		vm.itemTrendFriendHate = data;
            	} else {
            		vm.itemTrendFriendLike = data;
            	}
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
		
		function trendAll(status) {
			FeedItem.trendAll({
                page: pagingParams.page - 1,
                size: 10,
                sort: sort(),
                status: status
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
            	if (status == 0) {
            		vm.itemTrendAllHate = data;
            	} else {
            		vm.itemTrendAllLike = data;
            	}
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
		}
		

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
