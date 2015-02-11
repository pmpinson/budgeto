package org.pmp.budgeto.tools;

import org.joda.time.DateTime;
import org.pmp.budgeto.common.PropertiesConfig;
import org.pmp.budgeto.common.domain.DomainConfig;
import org.pmp.budgeto.common.repository.RepositoryConfig;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.pmp.budgeto.domain.account.Account;
import org.pmp.budgeto.domain.account.AccountConfig;
import org.pmp.budgeto.domain.account.AccountRepository;
import org.pmp.budgeto.domain.account.Operation;
import org.pmp.budgeto.domain.budget.Budget;
import org.pmp.budgeto.domain.budget.BudgetConfig;
import org.pmp.budgeto.domain.budget.BudgetRepository;
import org.pmp.budgeto.test.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * to configure the web app context with all component architecture
 */
@Configuration
public class DbInit {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PropertiesConfig.class, ToolsConfig.class, RepositoryConfig.class, DomainConfig.class, AccountConfig.class, BudgetConfig.class, DbInit.class);

        DbInit dbInit = ctx.getBean(DbInit.class);
        dbInit.init();
    }

    DbInit dbInit() {
        return new DbInit();
    }

    public void init() throws Exception {
        initBudget();
        initAccount();
    }

    public void initBudget() throws Exception {

        budgetRepository.deleteAll();

        Budget object = new Budget().setName("prev");
        budgetRepository.save(object);
    }

    public void initAccount() throws Exception {

        accountRepository.deleteAll();

        Account object = new Account().setName("compte courant").setNote("le compte principal");
        accountRepository.save(object);

        object = new Account().setName("livret A").setNote("livret d'épargne")
                .addOperations(new Operation(TestConfig.dateTools).setLabel("loyer").setDate(DateTime.now().minusDays(3)));
        accountRepository.save(object);
    }

}
