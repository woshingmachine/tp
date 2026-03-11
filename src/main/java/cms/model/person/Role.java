package cms.model.role;

import static cms.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's role in the address book. It can be either "student" or "tutor".
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public enum Role {
    STUDENT("student"),
    TUTOR("tutor");

    public static final String MESSAGE_CONSTRAINTS =
        "Roles should be either 'student' or 'tutor'";
    public final String value;

    Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.equals(STUDENT.value) || test.equals(TUTOR.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
