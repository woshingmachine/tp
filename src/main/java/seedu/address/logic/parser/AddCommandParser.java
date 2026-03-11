package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOC_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    // TODO: Remove stub when Person supports tutorial group and other new add fields.
    private static final String STUB_ADDRESS_VALUE = "-";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NUS_ID, PREFIX_ROLE, PREFIX_SOC_USERNAME,
                PREFIX_GITHUB_USERNAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_TUTORIAL_GROUP, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NUS_ID, PREFIX_ROLE, PREFIX_SOC_USERNAME,
            PREFIX_GITHUB_USERNAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_TUTORIAL_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NUS_ID, PREFIX_ROLE, PREFIX_SOC_USERNAME,
            PREFIX_GITHUB_USERNAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_TUTORIAL_GROUP);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        // TODO: Persist these parsed values when domain/model fields are implemented.
        ParserUtil.parseNusId(argMultimap.getValue(PREFIX_NUS_ID).get());
        ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        ParserUtil.parseSocUsername(argMultimap.getValue(PREFIX_SOC_USERNAME).get());
        ParserUtil.parseGithubUsername(argMultimap.getValue(PREFIX_GITHUB_USERNAME).get());
        ParserUtil.parseTutorialGroup(argMultimap.getValue(PREFIX_TUTORIAL_GROUP).get());

        Address address = new Address(STUB_ADDRESS_VALUE);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
