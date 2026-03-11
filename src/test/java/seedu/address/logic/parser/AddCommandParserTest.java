package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOC_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_NUS_ID = "A0234567B";
    private static final String VALID_ROLE = "student";
    private static final String VALID_SOC = "johndoe";
    private static final String VALID_GITHUB = "john-doe";
    private static final String VALID_EMAIL = "john@u.nus.edu";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_TUTORIAL = "T01";
    private static final String VALID_TAG_ONE = "struggling";
        private static final String VALID_TAG_TWO = "pythonexperienced";

    private static final String NAME_DESC = " " + PREFIX_NAME + VALID_NAME;
    private static final String NUS_ID_DESC = " " + PREFIX_NUS_ID + VALID_NUS_ID;
    private static final String ROLE_DESC = " " + PREFIX_ROLE + VALID_ROLE;
    private static final String SOC_DESC = " " + PREFIX_SOC_USERNAME + VALID_SOC;
    private static final String GITHUB_DESC = " " + PREFIX_GITHUB_USERNAME + VALID_GITHUB;
    private static final String EMAIL_DESC = " " + PREFIX_EMAIL + VALID_EMAIL;
    private static final String PHONE_DESC = " " + PREFIX_PHONE + VALID_PHONE;
    private static final String TUTORIAL_DESC = " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL;
    private static final String TAG_ONE_DESC = " " + PREFIX_TAG + VALID_TAG_ONE;
    private static final String TAG_TWO_DESC = " " + PREFIX_TAG + VALID_TAG_TWO;

    private static final String VALID_ADD_INPUT = NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC
            + GITHUB_DESC + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC;

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new Person(
                new Name(VALID_NAME),
                new Phone(VALID_PHONE),
                new Email(VALID_EMAIL),
                new Address("-"),
                Set.of(new Tag(VALID_TAG_ONE), new Tag(VALID_TAG_TWO)));

        assertParseSuccess(parser, "   " + VALID_ADD_INPUT + TAG_ONE_DESC + TAG_TWO_DESC,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        assertParseFailure(parser, VALID_ADD_INPUT + " " + PREFIX_PHONE + "81234567",
                getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, VALID_ADD_INPUT + " " + PREFIX_NUS_ID + "A1234567C",
                getErrorMessageForDuplicatePrefixes(PREFIX_NUS_ID));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new Person(
                new Name(VALID_NAME),
                new Phone(VALID_PHONE),
                new Email(VALID_EMAIL),
                new Address("-"),
                Set.of());
        assertParseSuccess(parser, VALID_ADD_INPUT, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC,
                expectedMessage);

        // missing tutorial group prefix
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + " T01",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME + VALID_NUS_ID + VALID_ROLE + VALID_SOC + VALID_GITHUB
                + VALID_EMAIL + VALID_PHONE + VALID_TUTORIAL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " " + PREFIX_NAME + "@John" + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid NUS ID
        assertParseFailure(parser, NAME_DESC + " " + PREFIX_NUS_ID + "Z123" + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC, ParserUtil.MESSAGE_NUS_ID_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + " " + PREFIX_ROLE + "ta" + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC, ParserUtil.MESSAGE_ROLE_CONSTRAINTS);

        // invalid SOC username
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + " " + PREFIX_SOC_USERNAME + "Abcde"
                + GITHUB_DESC + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC,
                ParserUtil.MESSAGE_SOC_USERNAME_CONSTRAINTS);

        // invalid GitHub username
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC
                + " " + PREFIX_GITHUB_USERNAME + "ab--cd" + EMAIL_DESC + PHONE_DESC + TUTORIAL_DESC,
                ParserUtil.MESSAGE_GITHUB_USERNAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC + EMAIL_DESC
                + " " + PREFIX_PHONE + "12ab" + TUTORIAL_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + " " + PREFIX_EMAIL + "john@" + PHONE_DESC + TUTORIAL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid tutorial group
        assertParseFailure(parser, NAME_DESC + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + " " + PREFIX_TUTORIAL_GROUP + "T1",
                ParserUtil.MESSAGE_TUTORIAL_GROUP_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_ADD_INPUT + " " + PREFIX_TAG + "bad tag", Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + PREFIX_NAME + "@John" + NUS_ID_DESC + ROLE_DESC + SOC_DESC + GITHUB_DESC
                + EMAIL_DESC + PHONE_DESC + " " + PREFIX_TUTORIAL_GROUP + "bad",
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, " abc" + VALID_ADD_INPUT + TAG_ONE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
