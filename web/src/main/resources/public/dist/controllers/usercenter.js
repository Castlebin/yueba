/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('UserCenterController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    $scope.user = {};

    $scope.search = function () {
        $http({
            method: 'GET',
            url: '/api/user'
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (angular.isObject(serverResponse) && serverResponse.status == 0) {
                    $scope.user = serverResponse.data;
                }
                else {
                    $.alert('得到用户中心失败了');
                }
            },
            function (response) {
                $.alert('得到用户中心失败了');
            }
        );
    };

    $scope.search();
}]);