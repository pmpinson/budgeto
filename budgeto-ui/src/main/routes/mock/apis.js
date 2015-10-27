var express = require('express');
var account = require('./account');
var helper = require('./helper');
var router = express.Router();

/* GET mocks apis. */
router.get('/', function(req, res, next) {
    var base = helper.baseUrl(req);
    res.json({links: [
        {rel: 'self', href: base},
        {rel: 'account', href: base + 'account'},
        {rel: 'budget', href: base + 'budget'}
    ]});
});

account(router);

module.exports = router;
