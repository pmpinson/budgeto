package org.pmp.budgeto.domain.budget;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.controller.ControllerError;
import org.pmp.budgeto.common.controller.DefaultControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controler for list and other action on budget.
 */
@RestController
@RequestMapping(value = "budget")
@Api(value = "Budget", description = "Work with budgets")
public class BudgetController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    private BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = Validate.notNull(budgetService);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all budgets", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.OK_CODE, message = DefaultControllerAdvice.OK_MSG),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public List<Budget> findAll() {
        LOGGER.info("get all account");
        return budgetService.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new budget", notes = "No")
    @ApiResponses({
            @ApiResponse(code = DefaultControllerAdvice.CREATED_CODE, message = DefaultControllerAdvice.CREATED_MSG),
            @ApiResponse(code = DefaultControllerAdvice.BAD_REQUEST_CODE, message = DefaultControllerAdvice.BAD_REQUEST_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.CONFLICT_CODE, message = DefaultControllerAdvice.CONFLICT_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.NOT_FOUND_CODE, message = DefaultControllerAdvice.NOT_FOUND_MSG, response = ControllerError.class),
            @ApiResponse(code = DefaultControllerAdvice.INTER_ERR_CODE, message = DefaultControllerAdvice.INTER_ERR_MSG, response = ControllerError.class)
    })
    public void add(@RequestBody Budget budget) throws Exception {
        LOGGER.info("post add for new budget");

        Budget newObject = new Budget().setName(budget.getName()).setNote(budget.getNote());

        budgetService.add(newObject);
    }

}
