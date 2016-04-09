/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('LoginController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    $scope.activity = {};

    $scope.login = function () {
        $http({
            method: 'POST',
            url: '/api/user/login',
            params: $scope.activity
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(serverResponse.status == 0){
                    var userVo = serverResponse.data;
                    UserService.setUser(userVo);
                    $window.location.href='home.html';
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

}]);