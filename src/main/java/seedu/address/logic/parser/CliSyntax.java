package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NUS_ID = new Prefix("id/");
    public static final Prefix PREFIX_ROLE = new Prefix("role/");
    public static final Prefix PREFIX_SOC_USERNAME = new Prefix("soc/");
    public static final Prefix PREFIX_GITHUB_USERNAME = new Prefix("gh/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    // TODO: Remove after all commands/tests are migrated away from address field.
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TUTORIAL_GROUP = new Prefix("t/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");

}
