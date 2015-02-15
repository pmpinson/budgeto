package org.pmp.budgeto.test;

import org.apache.commons.lang3.Validate;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;
import org.assertj.core.internal.Failures;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * helper for test
 */
public class AssertTools {

    private AssertTools() {
        // nothing
    }

    public static ConstructorAssert assertThat(Constructor<?> actual) {
        return new ConstructorAssert(actual);
    }

    public static class ConstructorAssert extends ObjectAssert<Constructor<?>> {
        Failures failures = Failures.instance();

        public ConstructorAssert(Constructor<?> actual) {
            super(actual);
        }

        public ConstructorAssert isPrivate() {
            Validate.notNull(info);
            Validate.notNull(actual);
            if (!Modifier.isPrivate(actual.getModifiers())) {
                throw this.failures.failure(info, ShouldBePrivate.shouldBePrivate(actual));
            }
            return (ConstructorAssert) this.myself;
        }

        public ConstructorAssert isPublic() {
            Validate.notNull(info);
            Validate.notNull(actual);
            if (!Modifier.isPublic(actual.getModifiers())) {
                throw this.failures.failure(info, ShouldBePrivate.shouldBePrivate(actual));
            }
            return (ConstructorAssert) this.myself;
        }
    }

    public static class ShouldBePrivate extends BasicErrorMessageFactory {
        public static ErrorMessageFactory shouldBePrivate(Constructor<?> actual) {
            return new ShouldBePrivate(actual, true);
        }

        private ShouldBePrivate(Constructor<?> actual, boolean toBeOrNotToBe) {
            super("\nExpecting\n  <%s>\n" + (toBeOrNotToBe ? "" : " not ") + "to be private", new Object[]{actual});
        }
    }

    public static class ShouldBePublic extends BasicErrorMessageFactory {
        public static ErrorMessageFactory shouldBePublic(Constructor<?> actual) {
            return new ShouldBePublic(actual, true);
        }

        private ShouldBePublic(Constructor<?> actual, boolean toBeOrNotToBe) {
            super("\nExpecting\n  <%s>\n" + (toBeOrNotToBe ? "" : " not ") + "to be public", new Object[]{actual});
        }
    }

    public static ClassAssert assertThat(Class<?> actual) {
        return new ClassAssert(actual);
    }

    public static class ClassAssert extends org.assertj.core.api.ClassAssert {
        Failures failures = Failures.instance();

        public ClassAssert(Class<?> actual) {
            super(actual);
        }

        public ClassAssert hasConstructors(int size) {
            Validate.notNull(info);
            Validate.notNull(actual);
            int nb = actual.getDeclaredConstructors().length;
            if (nb != size) {
                throw this.failures.failure(info, ShouldHaveConstructors.shouldHaveConstructors(actual, size, nb));
            }
            return (ClassAssert) this.myself;
        }
    }

    public static class ShouldHaveConstructors extends BasicErrorMessageFactory {

        public static ShouldHaveConstructors shouldHaveConstructors(Class<?> actual, int expected, int received) {
            return new ShouldHaveConstructors(actual, expected, received);
        }

        private ShouldHaveConstructors(Class<?> actual, int expected, int received) {
            super("\nExpecting\n  <%s>\n to have <%s> constructors and have <%s>", new Object[]{actual, expected, received});
        }
    }

}
