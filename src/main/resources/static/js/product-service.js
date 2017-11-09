(function(){
    var service = function($http, $log) {
        var getProducts = function(callback) {
            $http.get('/erp/get-products')
            .then(function(response){
                callback(response.data);
            }, function(response){
                console.log(response);
            });
        };

        return {
            getProducts: getProducts
        };
    };


    var module = angular.module('client');
    module.factory('productService', ['$http', '$log', service]);
})();
