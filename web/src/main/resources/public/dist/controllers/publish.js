/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('PublishController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    $scope.activity = {
        price: 0,
        pic:"/assets/img/sample.jpg"
    };

    $scope.publishActivity = function () {
        var ageRange = $("#age-range").attr("value");
        console.log("ageRange" + ageRange);
        $scope.activity['minAge'] = ageRange.split(' ')[0];
        $scope.activity['maxAge'] = ageRange.split(' ')[1];

        $scope.activity.activityBeginTime = Date.parse($scope.activity.activityBeginTime);
        $scope.activity.activityEndTime = Date.parse($scope.activity.activityEndTime);
        $scope.activity.applyEndTime = Date.parse($scope.activity.applyEndTime);

        if(requiredFieldIsNull($scope.activity.activityBeginTime, '活动开始时间')) return;
        if(requiredFieldIsNull($scope.activity.activityEndTime, '活动结束时间')) return;
        if(requiredFieldIsNull($scope.activity.applyEndTime, '报名截止时间')) return;
        if(requiredFieldIsNull($scope.activity.assemblingAddress, '集合地点')) return;
        if(requiredFieldIsNull($scope.activity.activityAddress, '活动地点')) return;
        if(requiredFieldIsNull($scope.activity.title, '标题')) return;
        if(requiredFieldIsNull($scope.activity.description, '活动内容说明')) return;

        $http({
            method: 'POST',
            url: '/api/activity',
            data: $scope.activity
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (serverResponse.status == 0) {
                    var resActivity = serverResponse.data;
                    if(angular.isObject(resActivity)){
                        $.toast('发起活动成功');
                        $timeout(function () {
                            $window.location.href = 'detail.html?aid='+resActivity.activityId;
                        }, 1000);
                    }
                }
                else {
                    $.alert('发起活动失败了');
                }
            },
            function (response) {
                $.alert('发起活动失败了');
            }
        );
    };
}]);