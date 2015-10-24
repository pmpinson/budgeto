import '../../../node_modules/bootstrap/dist/css/bootstrap.css';

import angular from 'angular';

import messageService from './message-service';
import loading from './loading/loading';
import apis from './apis/apis';
import home from './home/home';

/**
 * BudgetoRun : call to the init app page
 */
function RunBudgeto($state, $rootScope, $log, loadingService) {
    $log.debug('budgeto', 'init application');
    $rootScope.MessageService = messageService;
    loadingService.add('apisService');
    $state.transitionTo('home');
}
RunBudgeto.$inject = ['$state', '$rootScope', '$log', 'loadingService'];

angular.module('budgeto', [loading, apis, home])
    .constant('budgetoRestApiURL', require('./mock/apis.json'))
    //.constant('budgetoRestApiURL', 'http://localhost:9001/budgeto-api')
    .constant('MessageService', messageService)
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