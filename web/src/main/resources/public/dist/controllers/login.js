/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('LoginController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    console.log($scope.globalDefines);
    $scope.loginUser = {};

    $scope.login = function () {
        $http({
            method: 'POST',
            url: '/login',
            data: $scope.loginUser
        }).then(
            function (response) {
                $location
            },
            function (response) {
                $window.location.href='home.html';
                $.alert('登录失败了');
            }
        );
    };

}]);