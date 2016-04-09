/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.requires.push('angularFileUpload');
yuebaApp.controller('UserCenterController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines','FileUploader', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines, FileUploader) {

    $scope.globalDefines = globalDefines;

    // 最多可加载的页数
    $scope.maxPagess = 100;

    $scope.pageSize = 100;
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

    $scope.updateUser = function () {
        $http({
            method: 'POST',
            url: '/api/user/update',
            data: $scope.user
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (angular.isObject(serverResponse) && serverResponse.status == 0) {
                    if (angular.isObject(serverResponse.data)) {
                        $scope.user = serverResponse.data;
                    }
                }
                else {
                    console.log('update user failed');
                }
            },
            function (response) {
                console.log('update user failed');
            }
        );
    };

    $scope.list();

    //图片上传处理逻辑
    var uploader = $scope.uploader = new FileUploader({
        url: '/upload',
        autoUpload: true,
        queueLimit: 1
    });

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });
    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        $.alert('请选择图片上传');
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        $.alert('头像上传失败了');
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        uploader.clearQueue();
        if (response.status == 0) {
            $.toast('头像上传成功');
            $scope.user.avatar = response.data;
            $scope.updateUser();
        }
    };
}]);