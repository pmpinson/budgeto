function Loader() {
    var isVisible = true;

    this.visible = function () {
        return isVisible;
    };

    this.show = function () {
        isVisible = true;
    };

    this.hide = function () {
        isVisible = false;
    };
}

var loaders = {};

var loaderProvider = {

    $get: ['$rootScope', function ($rootScope) {

        var loader = {};

        loader.getLoader = function (name) {
            if (!_.has(loaders, name)) {
                loaders[name] = new Loader();
            }
            return loaders[name];
        };

        $rootScope.getLoader = function (name) {
            return loader.getLoader(name);
        };

        return loader;
    }]
};