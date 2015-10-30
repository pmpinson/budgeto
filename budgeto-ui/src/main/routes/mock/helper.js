import _ from 'lodash';

function baseUrl(req) {
    var url = req.protocol + '://' + req.get('host') + req.baseUrl;

    if (!_.endsWith(url, '/')) {
        return url + '/';
    } else {
        return url;
    }
}

export default {baseUrl: baseUrl};