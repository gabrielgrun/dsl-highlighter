package org.ggrun.dsl_highlighter.command;

import java.util.regex.Matcher;

public interface DslCommand {
    Matcher matcher(String line);
    String format(Matcher matcher);
}
