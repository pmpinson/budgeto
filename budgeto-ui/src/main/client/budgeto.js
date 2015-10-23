import '../../../node_modules/bootstrap/dist/css/bootstrap.css';

import angular from 'angular';

import apiService from './apis/apis';

import home from './home/index';

/**
  * config of apis provider
  */
function ApisServiceConfig(ApisServiceProvider, budgetoRestApiURL) {
    ApisServiceProvider.setUrl(budgetoRestApiURL);
}
ApisServiceConfig.$inject = ['apisServiceProvider', 'budgetoRestApiURL'];

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
 * BudgetoRun : call to the init app page
 */
function RunBudgeto($state, $rootScope, $log, MessageService) {
    $log.debug('budgeto', 'init application');
    $rootScope.MessageService = MessageService;
    $state.transitionTo('home');
}
RunBudgeto.$inject = ['$state', '$rootScope', '$log', 'MessageService'];

angular.module('budgeto', [apiService, home])
    .constant('budgetoRestApiURL', '/mock')
    //.constant('budgetoRestApiURL', 'http://localhost:9001/budgeto-api')
    .constant('MessageService', messages)
    .config(ApisServiceConfig)
    .run(RunBudgeto);
//
//    /**
//     * config service to have been loaded
//     */
//    function LoadingServiceConfig(LoadingServiceProvider) {
//        LoadingServiceProvider.add('ApiService');
//    }
//    LoadingServiceConfig.$inject = ['LoadingServiceProvider'];
//    moduleDefinition.module.config(LoadingServiceConfig);

//    // module definition
//    var moduleDefinition = {
//        name: 'budgeto',
//        dependencies: [
//            infiniteLoader.name,
//            modalError.name,
//            account.name,
//            accountRoute.name
//        ],
//        module: undefined
//    };
//
//    /**
//     * configuration of moment timezone
//     */
//    var angularMomentConfig = {timezone: 'UTC'};
//
//    /**
//     * config message for infinite loader
//     */
//    function InfiniteLoaderConfig($infiniteLoaderProvider, MessageService) {
//        $infiniteLoaderProvider.setMessage(MessageService.infiniteLoaderMsg);
//    }
//
//    /**
//     * config modal error message
//     */
//    function ModalErrorConfig($modalErrorProvider, MessageService) {
//        $modalErrorProvider.setDefaultOptions(MessageService.modalError);
//    }
//
//    moduleDefinition.module = angular.module(moduleDefinition.name, moduleDefinition.dependencies);
//
//    moduleDefinition.module.constant('angularMomentConfig', angularMomentConfig);
//    InfiniteLoaderConfig.$inject = ['$infiniteLoaderProvider', 'MessageService'];
//    moduleDefinition.module.config(InfiniteLoaderConfig);
//    ModalErrorConfig.$inject = ['$modalErrorProvider', 'MessageService'];
//    moduleDefinition.module.config(ModalErrorConfig);