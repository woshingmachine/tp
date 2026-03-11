package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NUS_ID_CONSTRAINTS = "NUS ID must be in the format "
        + "A#######X where # is a digit and X is an uppercase letter (e.g., A0234567B).";
    public static final String MESSAGE_ROLE_CONSTRAINTS = "Invalid Role. The role must be student / tutor.";
    public static final String MESSAGE_SOC_USERNAME_CONSTRAINTS = "SOC username must be 5-8 characters "
        + "(or a valid NUS-ID form), using only lowercase letters, digits, and hyphens, "
        + "and cannot start or end with a hyphen.";
    public static final String MESSAGE_GITHUB_USERNAME_CONSTRAINTS = "GitHub username must be 1-39 characters, "
        + "containing only alphanumeric characters or hyphens, and cannot start or end with a hyphen.";
    public static final String MESSAGE_TUTORIAL_GROUP_CONSTRAINTS = "Tutorial group must be in the format "
        + "T## where # is a digit (e.g., T01, T12).";

    private static final String NUS_ID_REGEX = "A[0-9]{7}[A-Z]";
    private static final String ROLE_STUDENT = "student";
    private static final String ROLE_TUTOR = "tutor";
    private static final String SOC_ALLOWED_CHARS_REGEX = "[a-z0-9-]+";
    private static final String SOC_NUS_ID_FORM_REGEX = "[a-z][0-9]{7}[a-z]";
    private static final String GITHUB_USERNAME_REGEX = "[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?";
    private static final String TUTORIAL_GROUP_REGEX = "T[0-9]{2}";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String nusId} into a normalized NUS ID.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: Return a NusId domain object after the model class is introduced.
     */
    public static String parseNusId(String nusId) throws ParseException {
        requireNonNull(nusId);
        String trimmedNusId = nusId.trim();
        String normalizedNusId = trimmedNusId.toUpperCase(Locale.ROOT);
        if (containsWhitespace(trimmedNusId) || !normalizedNusId.matches(NUS_ID_REGEX)) {
            throw new ParseException(MESSAGE_NUS_ID_CONSTRAINTS);
        }
        return normalizedNusId;
    }

    /**
     * Parses a {@code String role} into a normalized role.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: Return a Role domain object after the model class is introduced.
     */
    public static String parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (containsWhitespace(trimmedRole)) {
            throw new ParseException(MESSAGE_ROLE_CONSTRAINTS);
        }

        String normalizedRole = trimmedRole.toLowerCase(Locale.ROOT);
        if (!ROLE_STUDENT.equals(normalizedRole) && !ROLE_TUTOR.equals(normalizedRole)) {
            throw new ParseException(MESSAGE_ROLE_CONSTRAINTS);
        }
        return normalizedRole;
    }

    /**
     * Parses a {@code String socUsername} into a validated SOC username.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: Return a SocUsername domain object after the model class is introduced.
     */
    public static String parseSocUsername(String socUsername) throws ParseException {
        requireNonNull(socUsername);
        String trimmedSocUsername = socUsername.trim();

        if (containsWhitespace(trimmedSocUsername)
                || !trimmedSocUsername.matches(SOC_ALLOWED_CHARS_REGEX)
                || trimmedSocUsername.startsWith("-")
                || trimmedSocUsername.endsWith("-")) {
            throw new ParseException(MESSAGE_SOC_USERNAME_CONSTRAINTS);
        }

        if (trimmedSocUsername.matches(SOC_NUS_ID_FORM_REGEX)) {
            return trimmedSocUsername;
        }

        if (trimmedSocUsername.length() < 5 || trimmedSocUsername.length() > 8) {
            throw new ParseException(MESSAGE_SOC_USERNAME_CONSTRAINTS);
        }

        return trimmedSocUsername;
    }

    /**
     * Parses a {@code String githubUsername} into a validated GitHub username.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: Return a GithubUsername domain object after the model class is introduced.
     */
    public static String parseGithubUsername(String githubUsername) throws ParseException {
        requireNonNull(githubUsername);
        String trimmedGithubUsername = githubUsername.trim();
        if (containsWhitespace(trimmedGithubUsername)
                || trimmedGithubUsername.contains("--")
                || !trimmedGithubUsername.matches(GITHUB_USERNAME_REGEX)) {
            throw new ParseException(MESSAGE_GITHUB_USERNAME_CONSTRAINTS);
        }
        return trimmedGithubUsername;
    }

    /**
     * Parses a {@code String tutorialGroup} into a normalized tutorial group.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: Return a TutorialGroup domain object after the model class is introduced.
     */
    public static String parseTutorialGroup(String tutorialGroup) throws ParseException {
        requireNonNull(tutorialGroup);
        String trimmedTutorialGroup = tutorialGroup.trim();
        String normalizedTutorialGroup = trimmedTutorialGroup.toUpperCase(Locale.ROOT);
        if (containsWhitespace(trimmedTutorialGroup) || !normalizedTutorialGroup.matches(TUTORIAL_GROUP_REGEX)) {
            throw new ParseException(MESSAGE_TUTORIAL_GROUP_CONSTRAINTS);
        }
        return normalizedTutorialGroup;
    }

    private static boolean containsWhitespace(String value) {
        return value.chars().anyMatch(Character::isWhitespace);
    }
}
