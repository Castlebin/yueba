/**
 * Created by wainguo on 16/4/8.
 */
yuebaApp.controller('PublishController', ['$scope', '$http', '$q', 'UserService', '$location','$window', '$timeout', '$document','globalDefines', function ($scope, $http, $q, UserService, $location, $window, $timeout, $document, globalDefines) {

    //$scope.globalDefines = globalDefines;
    //
    //console.log($scope.globalDefines);
    //$scope.loginUser = {};
    //
    //$scope.login = function () {
    //    $http({
    //        method: 'POST',
    //        url: '/register',
    //        data: $scope.registerUser
    //    }).then(
    //        function (response) {
    //
    //        },
    //        function (response) {
    //            $window.location.href='home.html';
    //            $.alert('登录失败了');
    //        }
    //    );
    //};

    $("#age-range").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">请选择年龄段</h1>\
  </header>',
        cols: [
            {
                textAlign: 'center',
                values: ['16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50']
                //如果你希望显示文案和实际值不同，可以在这里加一个displayValues: [.....]
            },
            {
                textAlign: 'center',
                values: ['16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50']
            }
        ]
    });
}]);