/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('UserCenterController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    // 最多可加载的页数
    $scope.maxPagess = 100;

    $scope.pageSize = 5;
    $scope.pageNum = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;

    // 活动列表
    $scope.activityList = [];
    // infinite 加载，每页加载的list存储
    $scope.pageList = [];
    //一页的活动列表
    $scope.pageList = [];

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

    $scope.list = function () {
        var params = {pageSize: $scope.pageSize, pageNum: $scope.pageNum};
        $http({
            method: 'GET',
            url: '/api/activity/list',
            params: params
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (angular.isObject(serverResponse) && serverResponse.status == 0) {
                    if (angular.isObject(serverResponse.data)) {
                        $scope.totalPages = serverResponse.data.totalPages;
                        $scope.totalElements = serverResponse.data.totalElements;
                        if (angular.isArray(serverResponse.data.content)) {
                            $scope.activityList = serverResponse.data.content;
                        }
                    }
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

    $scope.list();
}]);