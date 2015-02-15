package org.pmp.budgeto.domain.account;

import com.mongodb.DBObject;
import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.tools.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * config the repository components for inject
 * scan on org.pmp.budgeto.common.repository package and load mongo repositories from
 * configure the database from mongo.srv.host, mongo.srv.port (not mandatory), mongo.db.name, mongo.db.user, mongo.db.password from Environment
 */
@Component
public class AccountListener extends AbstractMongoEventListener<Account> {

    private DateTools dateTools;

    @Autowired
    public AccountListener(DateTools dateTools) {
        this.dateTools = Validate.notNull(dateTools);
    }

    public void onAfterConvert(DBObject dbo, Account source) {
        for (Operation ope : source.getOperations()) {
            Field f = ReflectionUtils.findField(ope.getClass(), "dateTools", DateTools.class);
            f.setAccessible(true);
            ReflectionUtils.setField(f, ope, dateTools);
            f.setAccessible(false);
        }

    }

}
