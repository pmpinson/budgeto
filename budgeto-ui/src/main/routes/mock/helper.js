var _ = require('lodash');

function baseUrl(req) {
    var url = req.protocol + '://' + req.get('host') + req.baseUrl;
    if (!_.endsWith(url, '/')) return url + '/'; else url;
}

module.exports = {baseUrl: baseUrl};
