(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('TrendDialogController', TrendDialogController);

    TrendDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trend', 'FeedItem'];

    function TrendDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trend, FeedItem) {
        var vm = this;

        vm.trend = entity;
        vm.clear = clear;
        vm.save = save;
        vm.imageData;
        vm.visionData;
        vm.noShare = false;
        vm.keywords = [];
        vm.storeCloud = storeCloud;
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        $scope.$watch('vm.imageData', function(newVal, oldVal){
            console.log('changed');
            if (vm.imageData) {
            	googleVison();
            }
        });
        
        function storeCloud(status) {
        	var index = vm.imageData.data.indexOf(";base64,");
        	var content = vm.imageData.data.substring(index + 8);
        	
        	vm.item = {
				  contentType: vm.imageData.type,
				  data: content,
				  filename: vm.imageData.name,
				  keywords: vm.keywords.join(','),
				  share: !vm.noShare,
				  status: status
			};
        	
        	FeedItem.storeCloud(vm.item, onSuccess, onError);
        	function onSuccess(data, headers) {
        		console.log(data);
        		$uibModalInstance.close(true);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        function googleVison() {
        	console.log('googleVison');
        	console.log(vm.imageData);
        	
        	var index = vm.imageData.data.indexOf(";base64,");
        	var content = vm.imageData.data.substring(index + 8);
        	
        	vm.visionVM = {
        			content: content,
        			maxResults: 10
    		};
        	
        	FeedItem.visionDetect(vm.visionVM, onSuccess, onError);
        	function onSuccess(data, headers) {
        		console.log(data);
        		vm.visionData = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        function save () {
            vm.isSaving = true;
            if (vm.trend.id !== null) {
                Trend.update(vm.trend, onSaveSuccess, onSaveError);
            } else {
                Trend.save(vm.trend, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('blastApp:trendUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
