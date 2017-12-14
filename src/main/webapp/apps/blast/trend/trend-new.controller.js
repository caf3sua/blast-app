// code style: https://github.com/johnpapa/angular-styleguide 

(function() {
    'use strict';
    angular
      .module('app')
      .controller('TrendNewController', TrendNewController);
    
    TrendNewController.$inject = ['$controller', '$scope', 'FileUploader', 'FeedItem'];

      function TrendNewController($controller, $scope, FileUploader, FeedItem) {
	    	  var vm = this;
		  		
		        vm.imageData;
		        vm.visionData = [];
		        vm.noShare = false;
		        vm.keywords = [];
		        vm.moreKeyword;
		        vm.addMoreKeyword = addMoreKeyword;
		        vm.showResultGoogleVison = false;
		        vm.storeCloud = storeCloud;
		        
		  		$scope.$watch('vm.imageData', function(newVal, oldVal){
		            console.log('changed');
		            if (vm.imageData) {
		            		googleVison();
		            }
		        });
		  		
				// Init        
				(function initController() {
					// instantiate base controller
					$controller('BlastBaseController', {
						vm : vm
					});
				})();
				
				// Document ready
				angular.element(document).ready(function() {
					
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
		            }
		            function onError(error) {
		                //AlertService.error(error.data.message);
		            }
		        }
				
				function addMoreKeyword() {
					var moreKeyword = vm.moreKeyword;
					vm.keywords.push(moreKeyword);
					vm.moreKeyword = "";
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
		  	        		vm.showResultGoogleVison = true;
			          }
			          function onError(error) {
			              //AlertService.error(error.data.message);
			              vm.showResultGoogleVison = false;
			          }
				}
    			
    			
    			
    			// uploader
    			var uploader = $scope.uploader = new FileUploader({
                    url: 'scripts/controllers/upload.php'
                });

                // FILTERS

                uploader.filters.push({
                    name: 'customFilter',
                    fn: function(item /*{File|FileLikeObject}*/, options) {
                        return this.queue.length < 10;
                    }
                });

                // CALLBACKS

                uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
                    console.info('onWhenAddingFileFailed', item, filter, options);
                };
                uploader.onAfterAddingFile = function(fileItem) {
                    console.info('onAfterAddingFile', fileItem);
                };
                uploader.onAfterAddingAll = function(addedFileItems) {
                    console.info('onAfterAddingAll', addedFileItems);
                };
                uploader.onBeforeUploadItem = function(item) {
                    console.info('onBeforeUploadItem', item);
                };
                uploader.onProgressItem = function(fileItem, progress) {
                    console.info('onProgressItem', fileItem, progress);
                };
                uploader.onProgressAll = function(progress) {
                    console.info('onProgressAll', progress);
                };
                uploader.onSuccessItem = function(fileItem, response, status, headers) {
                    console.info('onSuccessItem', fileItem, response, status, headers);
                };
                uploader.onErrorItem = function(fileItem, response, status, headers) {
                    console.info('onErrorItem', fileItem, response, status, headers);
                };
                uploader.onCancelItem = function(fileItem, response, status, headers) {
                    console.info('onCancelItem', fileItem, response, status, headers);
                };
                uploader.onCompleteItem = function(fileItem, response, status, headers) {
                    console.info('onCompleteItem', fileItem, response, status, headers);
                };
                uploader.onCompleteAll = function() {
                    console.info('onCompleteAll');
                };

                console.info('uploader', uploader);
      }
      
})();
