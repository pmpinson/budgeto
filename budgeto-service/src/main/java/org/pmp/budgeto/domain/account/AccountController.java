package org.pmp.budgeto.domain.account;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.controller.ControllerError;
import org.pmp.budgeto.common.controller.DefaultControllerAdvice;
import org.pmp.budgeto.common.domain.DomainException;
import org.pmp.budgeto.common.domain.DomainNotFoundException;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Controler for list and other action on account.
 */
@RestController
@RequestMapping(value = "account")
@Api(value = "Account", description = "Work with accounts")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    private TranslatorTools translatorTools;

    @Autowired
    public AccountController(AccountService accountService, TranslatorTools translatorTools) {
        this.accountService = Validate.notNull(accountService);
        this.translatorTools = Validate.notNull(translatorTools);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all accounts", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.OK_CODE, message = DefaultControllerAdvice.OK_MSG),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public List<Account> findAll() {
        LOGGER.info("get all account");

        return accountService.findAll();
    }

    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve an account by it name", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.OK_CODE, message = DefaultControllerAdvice.OK_MSG),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public Account find(@ApiParam(name = "name", required = true, value = "name of account to get operations") @PathVariable String name) throws DomainException {
        LOGGER.info("get account {}", name);

        Account account = accountService.find(name);
        if (account == null) {
            throw new DomainNotFoundException(translatorTools.get("account.notexit", name));
        }

        return account;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new account", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.CREATED_CODE, message = DefaultControllerAdvice.CREATED_MSG),
            @ApiResponse(code = DefaultControllerAdvice.BAD_REQUEST_CODE, message = DefaultControllerAdvice.BAD_REQUEST_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.CONFLICT_CODE, message = DefaultControllerAdvice.CONFLICT_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public void add(@RequestBody Account account) throws DomainException {
        LOGGER.info("post add for new account");

        Account newObject = new Account().setName(account.getName()).setNote(account.getNote());

        accountService.add(newObject);
    }

    @RequestMapping(value = "{name}/operations", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all operations of an account", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.OK_CODE, message = DefaultControllerAdvice.OK_MSG),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public Set<Operation> operations(@ApiParam(name = "name", required = true, value = "name of account to get operations") @PathVariable String name) throws DomainException {
        LOGGER.info("get operations {}", name);

        Account account = find(name);

        return account.getOperations();
    }

}
