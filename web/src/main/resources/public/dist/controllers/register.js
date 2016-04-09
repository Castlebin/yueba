/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('RegisterController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    $scope.registerUser = {gender:'MALE'};

    $scope.register = function () {

        if(requiredFieldIsNull($scope.registerUser.username, '登录名')) return;
        if(requiredFieldIsNull($scope.registerUser.nickname, '昵称')) return;
        if(requiredFieldIsNull($scope.registerUser.mobile, '手机号码')) return;
        if(requiredFieldIsNull($scope.registerUser.password, '密码')) return;
        if(requiredFieldIsNull($scope.registerUser.confirmPassword, '确认密码')) return;
        if(requiredFieldIsNull($scope.registerUser.birthday, '生日')) return;

        if($scope.registerUser.password != $scope.registerUser.confirmPassword){
            $.alert('两次输入的密码不一致');
            return;
        }

        $http({
            method: 'POST',
            url: '/api/user/register',
            params: $scope.registerUser
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(serverResponse.status == 0){
                    $.toast('恭喜你，注册成功');
                    var userVo = serverResponse.data;
                    UserService.setUser(userVo);
                    $window.location.href='home.html';
                }
                else {
                    $.alert('注册失败了');
                }
            },
            function (response) {
                $.alert('注册失败了');
            }
        );
    };

}]);