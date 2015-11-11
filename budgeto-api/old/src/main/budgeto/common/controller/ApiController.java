package org.pmp.budgeto.common.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controler for list and other action on account.
 */
@RestController
@RequestMapping(value = "", produces = DefaultControllerAdvice.JSON_CONTENT_TYPE)
@Api(value = "Api", description = "Budgeto base resource api")
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve api", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.OK_CODE, message = DefaultControllerAdvice.OK_MSG),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public org.pmp.budgeto.common.controller.Api get() {
        LOGGER.info("get");

        return new org.pmp.budgeto.common.controller.Api();
    }

}
