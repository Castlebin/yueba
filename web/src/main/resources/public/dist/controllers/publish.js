/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.requires.push('angularFileUpload');
yuebaApp.controller('PublishController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines','FileUploader', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines, FileUploader) {

    $scope.globalDefines = globalDefines;

    $scope.activity = {
        open: true,
        price: 0,
        pic:""
        //pic:"/assets/img/sample.jpg"
    };

    $scope.publishActivity = function () {
        var ageRange = $("#age-range").attr("value");
        console.log("ageRange" + ageRange);
        $scope.activity['minAge'] = ageRange.split(' ')[0];
        $scope.activity['maxAge'] = ageRange.split(' ')[1];

        $scope.activity.activityBeginTime = Date.parse($scope.activity.activityBeginTimeAlt);
        $scope.activity.activityEndTime = Date.parse($scope.activity.activityEndTimeAlt);
        $scope.activity.applyEndTime = Date.parse($scope.activity.applyEndTimeAlt);

        if(requiredFieldIsNull($scope.activity.activityBeginTime, '活动开始时间')) return;
        if(requiredFieldIsNull($scope.activity.activityEndTime, '活动结束时间')) return;
        if(requiredFieldIsNull($scope.activity.applyEndTime, '报名截止时间')) return;
        if(requiredFieldIsNull($scope.activity.assemblingAddress, '集合地点')) return;
        if(requiredFieldIsNull($scope.activity.activityAddress, '活动地点')) return;
        if(requiredFieldIsNull($scope.activity.title, '标题')) return;
        if(requiredFieldIsNull($scope.activity.description, '活动内容说明')) return;
        if(requiredFieldIsNull($scope.activity.pic, '活动图片')) return;

        $http({
            method: 'POST',
            url: '/api/activity',
            data: $scope.activity
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (serverResponse.status == 0) {
                    var resActivity = serverResponse.data;
                    console.log(resActivity);
                    if(angular.isObject(resActivity)){
                        $.toast('发起活动成功');
                        $timeout(function () {
                            $window.location.href = 'detail.html?activityId='+resActivity.id;
                        }, 1000);
                    }
                }
                else {
                    $.alert('发起活动失败了:' + serverResponse.message);
                }
            },
            function (response) {
                $.alert('发起活动失败了');
            }
        );
    };

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
        console.info('onWhenAddingFileFailed', item, filter, options);
        $.alert('请选择图片上传');
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        $.alert('图片上传失败了');
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
        if (response.status == 0) {
            uploader.clearQueue();
            $.toast('图片上传成功');
            $scope.activity.pic = response.data;
        }
    };
}]);