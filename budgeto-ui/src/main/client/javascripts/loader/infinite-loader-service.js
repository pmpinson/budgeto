/**
 * define provider for manage infinite loader globaly
 * available config : setMessage to define message show with infinite loader
 * config : method to get the config : return {getMessage()}
 * show / hide : method to show and hide the infinite loader. Add a div with loader add the end of the body
 */
var message = 'Wait';

var InfiniteLoaderProvider = {

    setMessage: function (value) {
        message = value;
    },

    $get: ['$document', function ($document) {

        var cpt = 0;
        var InfiniteLoader = {};
        var body = $document.find('body').eq(0);
        var element = angular.element('<div class="infinite-loader infinite-loader-default hidden"></div>');
        body.append(element);

        InfiniteLoader.config = function () {
            return {
                getMessage: function () {
                    return message;
                }
            };
        };

        InfiniteLoader.show = function (newMessage) {
            cpt = cpt + 1;
            element.removeClass('hidden');
            var msg = newMessage ? newMessage : message;
            element.empty();
            element.append('<p><strong>' + msg + '</strong></p>');
        };

        InfiniteLoader.hide = function () {
            cpt = cpt - 1 ;
            if (cpt < 0) {
                cpt = 0;
            }
            if (cpt === 0) {
                element.addClass('hidden');
            }
        };

        InfiniteLoader.visible = function () {
            return cpt !== 0;
        };

        return InfiniteLoader;
    }]
};