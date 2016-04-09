/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('LoginController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    $scope.loginUser = {};

    $scope.login = function () {
        $http({
            method: 'POST',
            url: '/api/user/login',
            params: $scope.loginUser
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (serverResponse.status == 0) {
                    var userVo = serverResponse.data;
                    UserService.setUser(userVo);
                    $window.location.href = 'home.html';
                }
                else {
                    $.alert('登录失败了');
                }
            },
            function (response) {
                $.alert('登录失败了');
            }
        );
    };

    $scope.currentActivityNum = 0;

    $scope.getCurrentActivityNum = function () {
        $http({
            method: 'GET',
            url: '/api/activity/curractnum'
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (angular.isObject(serverResponse) && serverResponse.status == 0) {
                    $scope.currentActivityNum = serverResponse.data;
                }
                else {
                    $.alert('得到用户当前活动数量失败了');
                }
            },
            function (response) {
                $.alert('得到用户当前活动数量失败了');
            }
        );
    }

    $scope.getCurrentActivityNum();
}]);