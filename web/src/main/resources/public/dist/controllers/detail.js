/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('DetailController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    $scope.activityId = $location.search().activityId;
    $scope.activity = {};

    $scope.getActivityDetail = function () {
        if (!$scope.activityId) {
            $.alert('找不到相应的活动');
        }
        $http({
            method: 'GET',
            url: '/api/activity',
            params: {activityId: $scope.activityId}
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (angular.isObject(serverResponse) && serverResponse.status == 0) {
                    var activityVo = serverResponse.data;
                    $scope.activity = activityVo;
                    $scope.countTimer();
                }
                else {
                    $.alert('找不到相应的活动');
                    $.confirm('刷新重新获取?', '获取活动详情失败了', function () {
                        $window.location.href = $location.absUrl();
                    });
                }
            },
            function (response) {
                $.alert('请求失败了');
            }
        );
    };

    $scope.getAvatar = function(avatar, gender) {
        url = 'assets/img/male.png';
        if(!avatar) {
            if(gender == 'FEMALE') url = 'assets/img/female.png'
        }
        else {
            url = avatar;
        }
        return url;
    };

    $scope.getGenderMark = function(gender) {
        url = 'assets/img/maleMark.png';
        if(gender == 'FEMALE') url = 'assets/img/femaleMark.png';
        return url;
    };

    $scope.countTimer = function () {
        var currentTime = (new Date()).getTime();
        if(currentTime >= $scope.activity.applyEndTime){
            //已结束
            $scope.readableCounter = '';
            return;
        }
        var diff = ($scope.activity.applyEndTime - currentTime)/1000;
        if(diff <= 0){
            $scope.readableCounter = '';
            return;
        }
        var day = Math.floor(diff/(24*3600));
        var hour = Math.floor(diff%(24*3600)/3600);
        var minute = Math.floor(diff%3600/60);
        var second = Math.floor(diff%60);
        $scope.readableCounter = '';
        if(day) $scope.readableCounter = day + "天";
        $scope.readableCounter += hour + "小时";
        $scope.readableCounter += minute + "分钟";
        $scope.readableCounter += second + "秒";

        $timeout($scope.countTimer, 1000);
    };

    $scope.apply = function () {
        if (!$scope.activityId) {
            $.alert('找不到相应的活动');
            return;
        }
        var user = UserService.getUser();
        if (!angular.isObject(user) || !user.username) {
            $.alert('用户参数错误');
            return;
        }

        var requestParams = {
            activityId: $scope.activityId,
            username: UserService.getUser().username
        };

        $http({
            method: 'POST',
            url: '/api/activity/apply',
            params: requestParams
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (serverResponse.status == 0) {
                    var activityVo = serverResponse.data;
                    $scope.activity = activityVo;
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