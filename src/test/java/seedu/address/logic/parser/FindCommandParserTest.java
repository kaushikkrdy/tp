package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FindCommandParser.FindDescriptor;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ArrayList<Predicate<Person>> predicates = new ArrayList<>();
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        predicates.add(namePredicate);

        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/\n Alice \n \t Bob  \t", expectedFindCommand);

        // blank inputs for prefixes
        ArrayList<Predicate<Person>> emptyPredicates = new ArrayList<>();

        FindCommand emptyFindCommand = new FindCommand(emptyPredicates);
        assertParseSuccess(parser, "n/ y/", emptyFindCommand);
    }

    // Find Descriptor tests
    @Test
    public void findDescriptor_emptyPrefixInput_emptyPredicatesList() throws ParseException {
        String emptyNamePrefixInput = " n/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyNamePrefixInput, PREFIX_NAME);
        FindDescriptor findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String emptyEmailPrefixInput = " e/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyEmailPrefixInput, PREFIX_EMAIL);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String emptyPhonePrefixInput = " p/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyPhonePrefixInput, PREFIX_PHONE);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String emptyRolePrefixInput = " r/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyRolePrefixInput, PREFIX_ROLE);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String emptyExpectedSalaryPrefixInput = " s/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyExpectedSalaryPrefixInput, PREFIX_EXPECTED_SALARY);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String emptyTagPrefixInput = " t/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(emptyTagPrefixInput, PREFIX_TAG);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String multipleEmptyPrefixInput = " n/ e/ p/ r/ s/ t/";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(multipleEmptyPrefixInput,
                PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROLE, PREFIX_EXPECTED_SALARY, PREFIX_TAG);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());
    }

    @Test
    public void findDescriptor_blankPrefixInput_emptyPredicatesList() throws ParseException {
        String blankNamePrefixInput = " n/  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankNamePrefixInput, PREFIX_NAME);
        FindDescriptor findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String blankEmailPrefixInput = " e/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankEmailPrefixInput, PREFIX_EMAIL);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String blankPhonePrefixInput = " p/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankPhonePrefixInput, PREFIX_PHONE);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String blankRolePrefixInput = " r/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankRolePrefixInput, PREFIX_ROLE);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String blankExpectedSalaryPrefixInput = " s/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankExpectedSalaryPrefixInput,
                PREFIX_EXPECTED_SALARY);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String blankTagPrefixInput = " t/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(blankTagPrefixInput, PREFIX_TAG);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());

        String multipleBlankPrefixInput = " n/   e/   p/   r/   s/   t/  ";
        argMultimap = ArgumentTokenizer.tokenizeWithoutPreamble(multipleBlankPrefixInput,
                PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROLE, PREFIX_EXPECTED_SALARY, PREFIX_TAG);
        findDescriptor = new FindDescriptor(argMultimap);
        assertTrue(findDescriptor.getPredicates().isEmpty());
    }

    @Test
    public void findDescriptor_invalidPrefixInput_parseExceptionThrown() throws ParseException {
        String invalidNamePrefixInput = " n/@#$"; // non-alphanumeric characters
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidNamePrefixInput, PREFIX_NAME)));

        String invalidEmailPrefixInput = " e/peterjack@"; // missing domain name
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidEmailPrefixInput, PREFIX_EMAIL)));

        String invalidPhonePrefixInput = " p/abc"; // non-numeric
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidPhonePrefixInput, PREFIX_PHONE)));

        String invalidRolePrefixInput = " r/@#$%^&*()"; // only non-alphanumeric
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidRolePrefixInput, PREFIX_ROLE)));

        String invalidExpectedSalaryPrefixInput = " s/-100"; // negative number
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidExpectedSalaryPrefixInput, PREFIX_EXPECTED_SALARY)));

        String invalidTagPrefixInput = " t/old(70)"; // brackets
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(invalidTagPrefixInput, PREFIX_TAG)));

        String multipleInvalidPrefixInput = " n/@#$ e/peterjack@ p/abc r/@#$%^&*() s/-100 t/old(70)";
        assertThrows(ParseException.class, () ->
                new FindDescriptor(ArgumentTokenizer.tokenizeWithoutPreamble(multipleInvalidPrefixInput,
                        PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROLE, PREFIX_EXPECTED_SALARY, PREFIX_TAG)));
    }
}
