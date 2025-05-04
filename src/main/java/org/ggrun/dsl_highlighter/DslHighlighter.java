package org.ggrun.dsl_highlighter;

import org.ggrun.dsl_highlighter.command.ClickCommand;
import org.ggrun.dsl_highlighter.command.DslCommand;
import org.ggrun.dsl_highlighter.command.EnterCommand;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class DslHighlighter {

    private static final List<DslCommand> COMMANDS = List.of(
            new ClickCommand(),
            new EnterCommand()
    );

    public static String colorify(String commands) {
        return Arrays.stream(commands.split("\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(DslHighlighter::parseLine)
                .collect(Collectors.joining("\n"));
    }

    private static String parseLine(String line) {
        for (DslCommand command : COMMANDS) {
            Matcher matcher = command.matcher(line);
            if (matcher.matches()) {
                return command.format(matcher);
            }
        }
        return formatError(line);
    }

    public static String span(String color, String content) {
        return String.format("<span color=\"%s\">%s</span>", color, content);
    }

    private static String formatError(String line) {
        return span("error", "Failed to parse:") + " " + span("parameter", quote(line)) + ")";
    }

    public static String quote(String text) {
        return "\"" + text + "\"";
    }
}
