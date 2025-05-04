import org.ggrun.dsl_highlighter.DslHighlighter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DslHighlighterTest {

    @Test
    public void testColorifyUpperCase() {
        String input = "ENTER \"UPPERCASE\" INTO \"SOME EDIT\"\nCLICK \"A BUTTON\"\nCLICK \"some other button\"";
        String expected = "<span color=\"command\">ENTER</span> <span color=\"parameter\">\"UPPERCASE\"</span> <span color=\"keyword\">INTO</span> <span color=\"parameter\">\"SOME EDIT\"</span>\n" +
                "<span color=\"command\">CLICK</span> <span color=\"parameter\">\"A BUTTON\"</span>\n" +
                "<span color=\"command\">CLICK</span> <span color=\"parameter\">\"some other button\"</span>";

        String result = DslHighlighter.colorify(input);
        assertEquals(expected, result);
    }

    @Test
    void testClickCommand() {
        String input = "click \"Button name\"";
        String expected = "<span color=\"command\">click</span> <span color=\"parameter\">\"Button name\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testEnterCommand() {
        String input = "enter \"some data\" into \"Some edit\"";
        String expected = "<span color=\"command\">enter</span> <span color=\"parameter\">\"some data\"</span> <span color=\"keyword\">into</span> <span color=\"parameter\">\"Some edit\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testInvalidSyntax() {
        String input = "enter \"some data\"";
        assertTrue(DslHighlighter.colorify(input).contains("Failed to parse:"));
    }

    @Test
    void testMultipleLinesWithMixedValidity() {
        String input = String.join("\n",
                "click \"Button name\"",
                "enter \"data\" into \"field\"",
                "enter \"wrong\"",
                "CLICK \"Should be invalid due to case\""
        );

        String output = DslHighlighter.colorify(input);
        assertTrue(output.contains("click"));
        assertTrue(output.contains("enter"));
        assertTrue(output.contains("Failed to parse:"));
    }

    @Test
    void testSpecialCharacters() {
        String input = "click \"@#$%¨&*()_+\"";
        String expected = "<span color=\"command\">click</span> <span color=\"parameter\">\"@#$%¨&*()_+\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testExtraSpacesAroundCommands() {
        String input = "  click   \"Button\"   \n  enter   \"value\"    into   \"field\"  ";
        String expected = "<span color=\"command\">click</span> <span color=\"parameter\">\"Button\"</span>\n" +
                "<span color=\"command\">enter</span> <span color=\"parameter\">\"value\"</span> <span color=\"keyword\">into</span> <span color=\"parameter\">\"field\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testEmptyAndWhitespaceLines() {
        String input = "\n   \nclick \"Button\"\n   \n";
        String expected = "<span color=\"command\">click</span> <span color=\"parameter\">\"Button\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testCompletelyInvalidCommand() {
        String input = "this is not a command";
        assertTrue(DslHighlighter.colorify(input).contains("Failed to parse:"));
    }

    @Test
    void testMixedCaseCommands() {
        String input = "cLiCk \"Mix Button\"\neNtEr \"data\" iNtO \"Field\"";
        String expected = "<span color=\"command\">cLiCk</span> <span color=\"parameter\">\"Mix Button\"</span>\n" +
                "<span color=\"command\">eNtEr</span> <span color=\"parameter\">\"data\"</span> <span color=\"keyword\">iNtO</span> <span color=\"parameter\">\"Field\"</span>";
        assertEquals(expected, DslHighlighter.colorify(input));
    }

    @Test
    void testValidAndInvalidSequence() {
        String input = "click \"Valid\"\nbadcommand \"Oops\"";
        String output = DslHighlighter.colorify(input);
        assertTrue(output.contains("<span color=\"command\">click</span>"));
        assertTrue(output.contains("Failed to parse:"));
    }

    @Test
    void testMissingQuotes() {
        String input = "click Button";
        assertTrue(DslHighlighter.colorify(input).contains("Failed to parse:"));
    }
}
