package cz.cvut.fit.gorgomat.client;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShellUI implements PromptProvider {


    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Command:>", AttributedStyle.BOLD.foreground(AttributedStyle.MAGENTA));
    }
}
