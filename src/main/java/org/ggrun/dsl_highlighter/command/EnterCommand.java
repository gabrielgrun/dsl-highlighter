package org.ggrun.dsl_highlighter.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ggrun.dsl_highlighter.DslHighlighter.quote;
import static org.ggrun.dsl_highlighter.DslHighlighter.span;

public class EnterCommand implements DslCommand {
    private static final Pattern PATTERN = Pattern.compile("^(enter)\\s+\"([^\"]+)\"\\s+(into)\\s+\"([^\"]+)\"$", Pattern.CASE_INSENSITIVE);

    public Matcher matcher(String line) {
        return PATTERN.matcher(line);
    }

    public String format(Matcher matcher) {
        String command = matcher.group(1);
        String value = matcher.group(2);
        String keyword = matcher.group(3);
        String field = matcher.group(4);
        return span("command", command) + " " +
                span("parameter", quote(value)) + " " +
                span("keyword", keyword) + " " +
                span("parameter", quote(field));
    }
}