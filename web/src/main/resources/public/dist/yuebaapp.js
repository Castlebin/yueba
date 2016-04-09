var globalDefines = {
    trGender: {
        MALE: '男',
        FAMALE: '女'
    }
};

// 应用定义
var yuebaApp = angular.module('yuebaApp', []);

// 常量定义
yuebaApp.value('globalDefines', globalDefines);

//在底层做一次过滤预处理，劫持重定向页面
yuebaApp.factory('authInterceptor', ['$location', '$rootScope', '$q', function($location, $rootScope, $q) {
    return {
        'request': function (config) {
            config.headers = config.headers || {};
            //if ($window.sessionStorage.token) {
            //    config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
            //}
            return config;
        },
        'response': function (response) {
            if( angular.isString(response.data) ) {
                if (response.data.indexOf instanceof Function &&
                    response.data.indexOf('id="this-is-login-page"') != -1) {
                    $rootScope.$broadcast('unauthorized');
                    return {};
                }
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
        user:{
            isLogon: false,
            userName: '',
            nickName: '',
            phone: ''
        }
    };
    userDo.login = function () {
        this.user.isLogon = true;
        this.save();
    };

    userDo.logout = function () {
        this.user.isLogon = false;
        this.save();
    };
    userDo.setUserName = function (value) {
        this.user.userName = value;
    };
    userDo.setNickName = function (value) {
        this.user.nickName = value;
    };
    userDo.setPhone = function (value) {
        this.user.phone = value;
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
    userDo.getUserName = function () {
        this.load();
        return this.user.userName;
    };
    userDo.getNickName = function () {
        this.load();
        return this.user.nickName;
    };
    userDo.getPhone = function () {
        this.load();
        return this.user.phone;
    };
    userDo.getLoginState = function () {
        this.load();
        return this.user.isLogon;
    };

    userDo.getUserMenu = function() {
        this.load();
        return this.user.menu;
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
yuebaApp.controller('NavBarController', ['$scope','$http', '$location', 'UserService', '$rootScope',function($scope, $http, $location, UserService, $rootScope) {
    // 登录账号的Name
    $scope.userName = UserService.getUserName();
    $scope.isLogon = UserService.getLoginState();

    //如果发现未登录，那么直接页面跳转，将转到登录页
    $rootScope.$on('unauthorized', function () {
        console.log("!!! user unauthorized, session timeout!!! ");
        window.location = restApiRootUrl + "/login.html";
    });

    // 登录账号处于已登录状态
    //if(!$scope.operator || !$scope.isLogon){
    //    window.location = restApiRootUrl + "/login.html";
    //    return;
    //}

    $scope.activePage = 'home';

}]);

