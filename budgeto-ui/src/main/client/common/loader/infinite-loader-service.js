import angular from 'angular';
import _ from 'lodash';

/**
 * define provider for manage infinite loader globaly
 * available config : setMessage to define message show with infinite loader
 * config : method to get the config : return {getMessage()}
 * show / hide : method to show and hide the infinite loader. Add a div with loader add the end of the body
 */
class InfiniteLoaderService {

    constructor($document) {
        this.$document = $document;

        this.message = 'Wait';
        this.cpt = 0;
        var body = this.$document.find('body').eq(0);
        this.element = angular.element('<div class="infinite-loader infinite-loader-default hidden"></div>');
        body.append(this.element);
    }

    setMessage(value) {
        this.message = value;
    }

    getMessage() {
        return this.message;
    }

    show(newMessage) {
        this.cpt = this.cpt + 1;
        this.element.removeClass('hidden');
        var msg = _.isUndefined(newMessage) ? this.message : newMessage;
        this.element.empty();
        this.element.append('<p><strong>' + msg + '</strong></p>');
    }

    hide() {
        this.cpt = this.cpt - 1 ;

        if (this.cpt < 0) {
            this.cpt = 0;
        }

        if (this.cpt === 0) {
            this.element.addClass('hidden');
        }
    }

    visible () {
        return this.cpt !== 0;
    }
}

export default InfiniteLoaderService;
