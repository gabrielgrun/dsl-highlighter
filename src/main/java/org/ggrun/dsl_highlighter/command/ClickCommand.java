package org.ggrun.dsl_highlighter.command;

import org.ggrun.dsl_highlighter.DslHighlighter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ggrun.dsl_highlighter.DslHighlighter.quote;
import static org.ggrun.dsl_highlighter.DslHighlighter.span;

public class ClickCommand implements DslCommand {
    private static final Pattern PATTERN = Pattern.compile("^(click)\\s+\"([^\"]+)\"$", Pattern.CASE_INSENSITIVE);

    public Matcher matcher(String line) {
        return PATTERN.matcher(line);
    }

    public String format(Matcher matcher) {
        DslHighlighter dslHighlighter = new DslHighlighter();
        String command = matcher.group(1);
        String param = matcher.group(2);
        return span("command", command) + " " + span("parameter", quote(param));
    }
}
