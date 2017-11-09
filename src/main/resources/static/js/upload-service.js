(function(){
    var service = function($http, $log) {
        var productExcel = null;
        var productExcelType = null;
        var productCsv = null;
        var productCsvType = null;


        var hasProductExcel = function() {
            return productExcel != null;
        };

        var storeProductExcel = function(obj) {

            var name = obj.name;

            var fileExtension = name.toLowerCase().split('.').pop();

            $log.debug(fileExtension);

            if(fileExtension != 'xlsx') {
                toastr.alert('Only xlsx file is supported', 'Excel Upload Error');
                return;
            }

            productExcel = obj;
            productExcelType = fileExtension;

        };

        var uploadProductExcel =  function(token, callback) {
             var fd = new FormData();

             fd.append('file', productExcel);
             fd.append('token', token);

             var url = '/erp/upload-excel';

             $log.debug(url);

             $http({
                 method: 'POST',
                 url: url,
                 headers: {
                     'Content-Type': undefined
                 },
                 transformRequest: angular.identity,
                 data: fd
             }).success(function(response) {
                $log.debug(response);
                productExcel = null;
                productExcelType = null;
                if(callback) callback(response);
             }).error(function(response) {
                 $log.debug(response.exception);
                 $log.debug(response.message);
             });
        };


        var hasProductCsv = function() {
            return productCsv != null;
        };

        var storeProductCsv = function(obj) {

            var name = obj.name;

            var fileExtension = name.toLowerCase().split('.').pop();

            $log.debug(fileExtension);

            if(fileExtension != 'csv') {
                toastr.alert('Only csv file is supported', 'Csv Upload Error');
                return;
            }

            productCsv = obj;
            productCsvType = fileExtension;

        };

        var uploadProductCsv =  function(token, callback) {
             var fd = new FormData();

             fd.append('file', productCsv);
             fd.append('token', token);

             var url = '/erp/upload-csv';

             $log.debug(url);

             $http({
                 method: 'POST',
                 url: url,
                 headers: {
                     'Content-Type': undefined
                 },
                 transformRequest: angular.identity,
                 data: fd
             }).success(function(response) {
                $log.debug(response);
                productCsv = null;
                productCsvType = null;
                if(callback) callback(response);
             }).error(function(response) {
                 $log.debug(response.exception);
                 $log.debug(response.message);
             });
        };
        
        return {
            uploadProductExcel: uploadProductExcel,
            storeProductExcel: storeProductExcel,
            hasProductExcel: hasProductExcel,
            uploadProductCsv: uploadProductCsv,
            storeProductCsv: storeProductCsv,
            hasProductCsv: hasProductCsv
        };
    };

    var module = angular.module('client');
    module.factory('uploadService', ['$http', '$log', service]);

    module.directive('productExcelUpload', ['uploadService', function (uploadService) {
        return {
            restrict: 'A',
            scope : {
                productDataSet : "=ngModel"
            },
            link: function (scope, element, attr) {

                element.bind('change', function () {
                    uploadService.storeProductExcel(element[0].files[0]);
                });

            }
        };
    }]);
    

    module.directive('productCsvUpload', ['uploadService', function (uploadService) {
        return {
            restrict: 'A',
            scope : {
                productDataSet : "=ngModel"
            },
            link: function (scope, element, attr) {

                element.bind('change', function () {
                    uploadService.storeProductCsv(element[0].files[0]);
                });

            }
        };
    }]);
})();
