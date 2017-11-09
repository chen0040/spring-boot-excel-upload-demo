(function(){
    var controller = function($timeout, $log, $scope, $route, socketService, uploadService, productService, messageService) {
        var vm = this;
        $scope.products = [];

        vm.activate = function() {
            messageService.subscribe('event', 'clientController', function(channel, message){
                var eventMessage = JSON.parse(message);
                $timeout((function(_event){
                    return function() {
                        if(_event.eventType == 'progress'){
                            toastr.info(_event.state.name);
                            loadProducts();
                        } else if(_event.eventType == 'start') {
                            toastr.info(_event.state);
                        } else if(_event.eventType == 'end') {
                            toastr.info(_event.state);
                            loadProducts();
                        }


                    };
                })(eventMessage), 100);

            });

            socketService.connect(function(state, message){
                messageService.route(state, message);
            });
        };

        $scope.token = 'mocked-token';

        function loadProducts() {
            productService.getProducts(function(products){
                $scope.products = products;
            });
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
        
        $scope.uploadProductCsv = function() {
            var hasProductCsv = uploadService.hasProductCsv();
            if(hasProductCsv) {
                uploadService.uploadProductCsv($scope.token, function(result) {
                    if(result.success) {
                        toastr.info('Your Csv has been uploaded.' + result.id, 'Csv Uploaded');
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
    app.controller("clientController", ['$timeout', '$log', '$scope', '$route', 'socketService', 'uploadService', 'productService', 'messageService', controller]);

})();
