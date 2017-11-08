(function(){
    var controller = function($timeout, $log, $scope, $route, socketService, uploadService, messageService) {
        var vm = this;

        vm.activate = function() {
            messageService.subscribe('event', 'clientController', function(channel, message){
                var eventMessage = JSON.parse(message);
                $timeout((function(_event){
                    toastr.info(_event.name);
                    return function() {
                        $scope.lastEvent = _event;
                    };
                })(eventMessage), 100);

            });

            socketService.connect(function(state, message){
                messageService.route(state, message);
            });
        };

        $scope.vendor = 'mocked-token';

        function loadProducts() {

        }

        $scope.uploadProductExcel = function() {
            var hasProductExcel = uploadService.hasProductExcel();
            if(hasProductExcel) {
                uploadService.uploadProductExcel($scope.token, function(result) {
                    if(result.success) {
                        toastr.info('Your Excel has been uploaded.' + result.id, 'Excel Uploaded');
                        $timeout(function(){
                            loadProducts();
                        }, 1000);
                    } else {
                        toastr.error(reason, 'Upload Error');
                    }
                });
            } else {
                toastr.error('Please select your image to upload First!', 'Invalid Upload');
            }
        };

        vm.activate();
    };

    var app = angular.module('client');
    app.controller("clientController", ['$timeout', '$log', '$scope', '$route', 'socketService', 'uploadService', 'messageService', controller]);

})();
