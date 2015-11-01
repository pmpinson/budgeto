import _ from "lodash";

class Loader {

    constructor() {
        this.isVisible = true;
    }

    visible() {
        return this.isVisible;
    }

    show() {
        this.isVisible = true;
    }

    hide() {
        this.isVisible = false;
    }
}

/**
 * Loader to show during content loading
 */
class LoaderService {

    constructor() {
        this.loaders = [];
    }

    get(name) {
        if (!_.has(this.loaders, name)) {
            this.loaders[name] = new Loader();
        }

        return this.loaders[name];
    }

}

export default LoaderService;
