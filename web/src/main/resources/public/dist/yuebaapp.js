var globalDefines = {
    trGender: {
        "MALE": "男",
        "FAMALE": "女"
    }
};

function requiredFieldIsNull(field, fieldName){
    if(!field){
        $.alert(fieldName + '不能为空');
        return true;
    }
    return false;
}

// 应用定义
var yuebaApp = angular.module('yuebaApp', []);

yuebaApp.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false,
        rewriteLinks: false
    });
}]);

// 常量定义
yuebaApp.value('globalDefines', globalDefines);

//在底层做一次过滤预处理，劫持重定向页面
yuebaApp.factory('authInterceptor', ['$location', '$window', '$rootScope','$timeout', '$q', function($location, $window, $rootScope, $timeout, $q) {
    return {
        'request': function (config) {
            config.headers = config.headers || {};
            //if ($window.sessionStorage.token) {
            //    config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
            //}
            return config;
        },
        'response': function (response) {
            var serverResponse = response.data;

            //{message: "用户未登录！", status: -1}
            if(angular.isObject(serverResponse) && serverResponse.status == -1 ) {
                console.log('broadcast: unauthorized');
                $rootScope.$broadcast('unauthorized');
                $.toast('登录超时，请重新登录');
                $timeout(function () {
                    $window.location.href = "login.html";
                }, 1000);
                return {};
            }
            return response || $q.when(response);
        }
        //'responseError': function (response) {
        //    console.log("authInterceptor responseError begin >>>>");
        //    console.log(response);
        //    console.log("authInterceptor responseError:" + response.status);
        //    if (response.status === 401 || response.status === 302 || response.status === 419 ) {
        //        $rootScope.$broadcast('unauthorized');
        //    }
        //    console.log("authInterceptor responseError end >>>>");
        //    return response;
        //}
    };
}]);

yuebaApp.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
});

// 用户数据存储
yuebaApp.factory('UserService', [function() {
    var localStorageKey = 'YuebaUser';
    var userDo = {
        user:{},
        isLogon: false
    };
    userDo.login = function () {
        this.isLogon = true;
        this.save();
    };

    userDo.logout = function () {
        this.isLogon = false;
        this.save();
    };
    userDo.setUser = function (userVo) {
        this.user = userVo;
        this.save();
    };
    userDo.save = function() {
        if( JSON === null ) return;
        if (localStorage !== null) {
            localStorage[localStorageKey] = JSON.stringify(this.user);
        }
        else {
            this.cookieStore.put(localStorageKey, JSON.stringify(this.user));
        }
    };
    userDo.getUser = function () {
        this.load();
        return this.user;
    };
    userDo.getLoginState = function () {
        this.load();
        return this.isLogon;
    };
    userDo.load = function () {
        var jsonstr =  localStorage !== null ? localStorage[localStorageKey] : this.cookieStore.get(localStorageKey);
        if (jsonstr !== null && JSON !== null) {
            try {
                obj = JSON.parse(jsonstr);
                this.user = obj;
            }
            catch (err) {
                // ignore errors while loading...
            }
        }
    };
    return userDo;
}]);

var restApiRootUrl = window.location.origin;
yuebaApp.controller('NavBarController', ['$scope','$http', '$location', '$window', 'UserService', '$rootScope',function($scope, $http, $location, $window, UserService, $rootScope) {
    // 登录账号的Name
    $scope.user = UserService.getUser();
    $scope.isLogon = UserService.getLoginState();

    //如果发现未登录，那么直接页面跳转，将转到登录页
    //$rootScope.$on('unauthorized', function () {
    //    console.log("!!! user unauthorized, session timeout!!! ");
    //    $window.location.href = restApiRootUrl + "/login.html";
    //});

    // 登录账号处于已登录状态
    //if(!$scope.operator || !$scope.isLogon){
    //    window.location = restApiRootUrl + "/login.html";
    //    return;
    //}

    $scope.activePage = 'home';

}]);

