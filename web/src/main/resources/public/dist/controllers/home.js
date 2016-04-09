/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('DetailController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    $scope.activityList = [];

    $scope.getActivityList = function () {
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
                if(angular.isObject(serverResponse) && serverResponse.status == 0){
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
}]);