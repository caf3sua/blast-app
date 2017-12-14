(function() {
  'use strict';
  angular
    .module('app')
    .directive('itsAsideMenu', itsAsideMenu);

  	itsAsideMenu.$inject = [];
    function itsAsideMenu() {
    		var directive = {};
        directive.restrict = 'E';
        directive.template = '<input ng-model="vm.checked" type="checkbox"/>';

        return directive;
    }
    
    
})();
