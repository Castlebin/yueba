/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('DetailController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    $scope.activityId = $location.search().aid;
    $scope.activity = {};

    $scope.getActivityDetail = function () {
        if(!$scope.activityId) {
            $.alert('找不到相应的活动');
        }

        $http({
            method: 'GET',
            url: '/api/activity',
            params: {activityId: $scope.activityId}
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(serverResponse.status == 0){
                    var activityVo = serverResponse.data;
                    $scope.activity = activityVo;
                }
                else {
                    $.alert('找不到相应的活动');
                    $.confirm('刷新重新获取?', '获取活动详情失败了', function () {
                        $window.location.href=$location.absUrl();
                    });
                }
            },
            function (response) {
                $.alert('请求失败了');
            }
        );
    };

    $scope.apply = function () {
        if(!$scope.activityId) {
            $.alert('找不到相应的活动');
            return;
        }
        var user = UserService.getUser();
        if(!angular.isObject(user) || !user.username) {
            $.alert('用户参数错误');
            return;
        }

        var requestParams = {
            activityId: $scope.activityId,
            username: UserService.getUser().username
        };

        $http({
            method: 'GET',
            url: '/api/activity/apply',
            params: requestParams
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(serverResponse.status == 0){
                    $.toast('恭喜你，报名参加活动成功了');
                }
                else {

                    $.alert('报名参加活动失败了：' + serverResponse.message);
                }
            },
            function (response) {
                $.alert('报名参加活动失败，请重试');
            }
        );
    };

    //拉取活动详情
    $scope.getActivityDetail();
}]);