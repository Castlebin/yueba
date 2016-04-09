var globalDefines = {
    trGender: {
        "MALE": "男",
        "FAMALE": "女"
    }
};

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,               //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
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
            if(angular.isObject(serverResponse) && serverResponse.status == 1 ) {
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

yuebaApp.filter('cut', function () {
    return function (value, wordwise, max, tail) {
        if (!value) return '';

        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max) return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                value = value.substr(0, lastspace);
            }
        }

        return value + (tail || ' …');
    };
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

    var url = $location.url();
    // => "/some/path?foo=bar&baz=xoxo"
    console.log(url);
    if(url){
        $scope.currentUrl = url.split('?')[0];
        console.log($scope.currentUrl);
    }
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

