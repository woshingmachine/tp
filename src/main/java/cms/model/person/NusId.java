package cms.model.person;

import static cms.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's NUS ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNusId(String)}
 */
public class NusId {

    public static final String MESSAGE_CONSTRAINTS =
            "NUS ID must be in the format A#######X where # is a digit and X is an uppercase letter (e.g., A0234567B).";
    public static final String VALIDATION_REGEX = "A\\d{7}[A-Z]";
    public final String value;

    /**
     * Constructs a {@code NusId}.
     *
     * @param nusId A valid NUS ID.
     */
    public NusId(String nusId) {
        requireNonNull(nusId);
        checkArgument(isValidNusId(nusId), MESSAGE_CONSTRAINTS);
        value = nusId;
    }

    /**
     * Returns true if a given string is a valid NUS ID.
     */
    public static boolean isValidNusId(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof NusId)) {
            return false;
        }

        NusId otherNusId = (NusId) other;
        return value.equals(otherNusId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
