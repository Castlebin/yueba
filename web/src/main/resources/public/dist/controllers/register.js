/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('RegisterController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    console.log($scope.globalDefines);
    $scope.loginUser = {};

    $scope.login = function () {
        $http({
            method: 'POST',
            url: '/register',
            data: $scope.registerUser
        }).then(
            function (response) {

            },
            function (response) {
                $window.location.href='home.html';
                $.alert('登录失败了');
            }
        );
    };

}]);