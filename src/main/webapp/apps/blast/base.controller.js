// code style: https://github.com/johnpapa/angular-styleguide 
// Create by: Nam, Nguyen Hoai - ITSOL.vn

(function() {
    'use strict';
    angular
      .module('app')
      .controller('BlastBaseController', BlastBaseController);
    
    //KpiBaseController.$inject = ['$scope'];

    function BlastBaseController(vm, $uibModal, ProfileService, $alert, Keyword, FeedItem){
    		vm.message = { name: 'default entry from BlastBaseController' };

    		
    	    // add any other shared functionality here.
    		vm.openDetail = openDetail;
    		vm.showMessage = showMessage;
    		
    		function openDetail(feedId, keywordName) {
		    var modalInstance = $uibModal.open({
		      backdrop: true,
		      animation: true,
		      ariaLabelledBy: 'modal-title',
		      ariaDescribedBy: 'modal-body',
		      templateUrl: 'apps/blast/trend/trend-detail-dialog.html',
		      controller: 'TrendDetailDialogController',
		      controllerAs: 'vm',
		      size: 'lg',
		      resolve: {
		    	  	entity: ['Keyword', function(Keyword) {
                      return Keyword.getByName({name : keywordName}).$promise;
                  }]
          		, feed: ['FeedItem', function(FeedItem) {
                      return FeedItem.get({id : feedId}).$promise;
                  }]
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		    		vm.selected = selectedItem;
		    }, function () {
		      
		    });
    		}
    		
    		
    		function showMessage(title, content, type) {
    			var myAlert = $alert({title: title, content: content, placement: 'top-right', type: type, show: true, duration: 3000});
    		}
    		
    }
})();
