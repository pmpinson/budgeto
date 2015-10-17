'use strict';

define(['angular', 'components/apis/apis', 'components/tools/infinite-loader', 'components/tools/modal-error',
    'components/home/loading', 'components/home/home', 'components/home/home-route', 'components/account/account', 'components/account/account-route'
    ], function(angular, apis, infiniteLoader, modalError, loading, home, homeRoute, account, accountRoute) {

    // module definition
    var moduleDefinition = {
        name: 'budgeto',
        dependencies: [
            apis.name,
            infiniteLoader.name,
            modalError.name,
            loading.name,
            home.name,
            homeRoute.name,
            account.name,
            accountRoute.name
        ],
        module: undefined
    };

    /**
     * rest api url
     */
    var BudgetoRestApiURL = 'http://localhost:9001/budgeto-api';

    /**
     * configuration of moment timezone
     */
    var angularMomentConfig = {timezone: 'UTC'};

    /**
     * messages
     */
    var messages = {
        applicationInitFail: 'Erreur during initialisation. Come back later. So Sorry...',
        infiniteLoaderMsg: 'Work in progress. Pleas wait...',
        apisLinks: {
            account: 'Go manage your accounts',
            budget: 'Go to prepare your budget'
        },
        apisTitles: {
            account: 'Manage your accounts',
            budget: 'Prepare your budget'
        },
        homeTitle: 'Welcome to budgeto',
        homeLink: 'Go back to home',
        modalError: {
           title: 'An error occured, please advice us.',
           detail: 'Error detail',
           close: 'Close'
       }
    };

    /**
     * config of apis provider
     */
    function ApiServiceConfig(ApiServiceProvider, BudgetoRestApiURL) {
        ApiServiceProvider.setUrl(BudgetoRestApiURL);
    }

    /**
     * config message for infinite loader
     */
    function InfiniteLoaderConfig($infiniteLoaderProvider, MessageService) {
        $infiniteLoaderProvider.setMessage(MessageService.infiniteLoaderMsg);
    }

    /**
     * config service to have been loaded
     */
    function LoadingServiceConfig(LoadingServiceProvider) {
        LoadingServiceProvider.add('ApiService');
    }

    /**
     * config modal error message
     */
    function ModalErrorConfig($modalErrorProvider, MessageService) {
        $modalErrorProvider.setDefaultOptions(MessageService.modalError);
    }

    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);

    moduleDefinition.module.constant('BudgetoRestApiURL', BudgetoRestApiURL);
    moduleDefinition.module.constant('angularMomentConfig', angularMomentConfig);
    moduleDefinition.module.constant('MessageService', messages);
    ApiServiceConfig.$inject = ['ApiServiceProvider', 'BudgetoRestApiURL'];
    moduleDefinition.module.config(ApiServiceConfig);
    InfiniteLoaderConfig.$inject = ['$infiniteLoaderProvider', 'MessageService'];
    moduleDefinition.module.config(InfiniteLoaderConfig);
    LoadingServiceConfig.$inject = ['LoadingServiceProvider'];
    moduleDefinition.module.config(LoadingServiceConfig);
    ModalErrorConfig.$inject = ['$modalErrorProvider', 'MessageService'];
    moduleDefinition.module.config(ModalErrorConfig);

    /**
     * BudgetoRun : call to the init app page
     */
    moduleDefinition.module.run(['$state', '$rootScope', '$log', 'MessageService', function ($state, $rootScope, $log, MessageService) {
        $log.debug('budgeto : run');

        $rootScope.MessageService = MessageService;

        $state.transitionTo('home');
    }]);

    return moduleDefinition;
});