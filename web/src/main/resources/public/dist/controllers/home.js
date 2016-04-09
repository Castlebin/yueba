/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('HomeController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {
    $scope.globalDefines = globalDefines;

    // 最多可加载的页数
    $scope.maxPagess = 100;

    $scope.pageSize = 5;
    $scope.pageNum = 0;
    $scope.totalPages = 0;
    $scope.totalElements = 0;

    // home所有的活动列表
    $scope.activityList = [];
    // infinite 加载，每页加载的list存储
    $scope.pageList = [];

    //一页的活动列表
    $scope.pageList = [];

    $scope.getActivityList = function () {
        $http({
            method: 'GET',
            url: '/api/activity/recommend',
            params: {pageSize: $scope.pageSize, pageNum: $scope.pageNum}
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(angular.isObject(serverResponse) && serverResponse.status == 0){
                    if(angular.isObject(serverResponse.data)) {
                        $scope.totalPages = serverResponse.data.totalPages;
                        $scope.totalElements = serverResponse.data.totalElements;
                        if(angular.isArray(serverResponse.data.content)){
                            $scope.activityList = serverResponse.data.content;
                        }
                    }
                }
                else {
                    $.alert('没有找到推荐的活动');
                }
            },
            function (response) {
                $.alert('请求失败了');
            }
        );
    };

    $scope.loadMoreActivityList = function () {
        $scope.pageNum += 1;

        $http({
            method: 'GET',
            url: '/api/activity/recommend',
            params: {pageSize: $scope.pageSize, pageNum: $scope.pageNum}
        }).then(
            function (response) {
                var serverResponse = response.data;
                if(angular.isObject(serverResponse) && serverResponse.status == 0){
                    if(angular.isObject(serverResponse.data)) {
                        $scope.totalPages = serverResponse.data.totalPages;
                        $scope.totalElements = serverResponse.data.totalElements;
                        if(angular.isArray(serverResponse.data.content)){
                            $scope.pageList = serverResponse.data.content;
                            angular.forEach($scope.pageList, function (activity) {
                                $scope.activityList.push(activity);
                            });
                        }
                    }
                }
                else {
                    $.alert('没有找到推荐的活动');
                }
            },
            function (response) {
                $.alert('请求失败了');
            }
        );
    };

    $scope.getActivityList();


    // 加载flag
    $scope.loading = false;
    // 注册'infinite'事件处理函数
    $(document).on('infinite', '.infinite-scroll-bottom',function() {
        // 如果正在加载，则退出
        if ($scope.loading) return;
        // 设置flag
        $scope.loading = true;

        // 模拟1s的加载过程
        setTimeout(function() {
            $scope.loadMoreActivityList();

            // 重置加载flag
            $scope.loading = false;

            if (($scope.pageNum+1)*$scope.pageSize >= $scope.totalElements ||
                $scope.pageNum > $scope.maxPagess) {
                // 加载完毕，则注销无限加载事件，以防不必要的加载
                $.detachInfiniteScroll($('.infinite-scroll'));
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
                return;
            }

            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });

}]);