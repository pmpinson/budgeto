import express from 'express';
import account from './account.js';
import helper from './helper.js';

var router = express.Router();

/* GET mocks apis. */
router.get('/', function (req, res) {
    var base = helper.baseUrl(req);
    res.json({
        links: [
            {rel: 'self', href: base},
            {rel: 'account', href: base + 'account'},
            {rel: 'budget', href: base + 'budget'}
        ]
    });
});

account(router);

export default router;
