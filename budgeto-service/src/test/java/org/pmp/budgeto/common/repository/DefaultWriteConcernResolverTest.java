package org.pmp.budgeto.common.repository;

import com.mongodb.WriteConcern;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.repository.DefaultWriteConcernResolver;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.stereotype.Component;


@RunWith(MockitoJUnitRunner.class)
public class DefaultWriteConcernResolverTest {

    @InjectMocks
    private DefaultWriteConcernResolver defaultWriteConcernResolver;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(DefaultWriteConcernResolver.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(DefaultWriteConcernResolver.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void resolveNullAction() throws Exception {

        Assertions.assertThat(defaultWriteConcernResolver.resolve(null)).isEqualTo(WriteConcern.JOURNALED);
    }

    @Test
    public void resolveNotNullAction() throws Exception {

        MongoAction action = Mockito.mock(MongoAction.class);

        Assertions.assertThat(defaultWriteConcernResolver.resolve(action)).isEqualTo(WriteConcern.JOURNALED);
    }

}
