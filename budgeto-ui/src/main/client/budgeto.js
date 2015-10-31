import '../../../node_modules/moment/moment.js';

import './budgeto.less';

import angular from 'angular';
import angularMoment from '../../../node_modules/angular-moment/angular-moment.js';

import messageService from './message-service.js';
import loading from './common/loading/loading.js';
import apis from './apis/apis.js';
import home from './home/home.js';
import account from './account/account.js';

/**
 * BudgetoRun : call to the init app page
 */
function RunBudgeto($state, $rootScope, $log, loadingService) {
    $log.debug('budgeto', 'init application');

    $rootScope.MessageService = messageService;
    loadingService.add('apisService');
    $state.transitionTo('home');
}

angular.module('budgeto', [loading, apis, angularMoment.name, home, account])
    .constant('budgetoRestApiURL', '/mock')
    //.constant('budgetoRestApiURL', 'http://localhost:9001/budgeto-api')
    .constant('MessageService', messageService)
    //.constant('angularMomentConfig', {timezone: 'UTC'})
    .run(['$state', '$rootScope', '$log', 'loadingService', RunBudgeto]);
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
//    InfiniteLoaderConfig.$inject = ['$infiniteLoaderProvider', 'MessageService'];
//    moduleDefinition.module.config(InfiniteLoaderConfig);
//    ModalErrorConfig.$inject = ['$modalErrorProvider', 'MessageService'];
//    moduleDefinition.module.config(ModalErrorConfig);