/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('PublishController', ['$scope', '$http', '$q', 'UserService', '$location', '$window', '$timeout', '$document', 'globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    $scope.globalDefines = globalDefines;

    $scope.activity = {};

    $scope.save = function () {
        var ageRange = $("#age-range").attr("value");
        console.log("ageRange" + ageRange);
        $scope.activity['minAge'] = ageRange.split(' ')[0];
        $scope.activity['maxAge'] = ageRange.split(' ')[1];
        $http({
            method: 'POST',
            url: '/api/activity',
            params: $scope.activity
        }).then(
            function (response) {
                var serverResponse = response.data;
                if (serverResponse.status == 0) {
                    var userVo = serverResponse.data;
                    UserService.setUser(userVo);
                    $window.location.href = 'home.html';
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