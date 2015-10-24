import '../../../node_modules/bootstrap/dist/css/bootstrap.css';

import angular from 'angular';

import messageService from './message-service';
import apis from './apis/apis';
import home from './home/home';

/**
 * BudgetoRun : call to the init app page
 */
function RunBudgeto($state, $rootScope, $log) {
    $log.debug('budgeto', 'init application');
    $rootScope.MessageService = messageService;
    $state.transitionTo('home');
}
RunBudgeto.$inject = ['$state', '$rootScope', '$log', 'MessageService'];

angular.module('budgeto', [apis, home])
    .constant('budgetoRestApiURL', '/mock')
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