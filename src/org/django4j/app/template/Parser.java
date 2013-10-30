package org.django4j.app.template;

import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.token.Token;
import org.django4j.util.DjangoUtils;

import java.io.File;
import java.util.List;

public class Parser {
    private final File file;
    private int index = 0;
    private int len;
    private List<Token> tokenList;

    public Parser(final File _file) {
        file = _file;
    }

    public boolean hasNext() {
        return index < len;
    }

    public Token next() {
        return tokenList.get(index++);
    }

    public RootNode parse(final ITemplateEngine tEngine) throws Exception {
        final String fileContent = DjangoUtils.loadFile(file,
                tEngine.getCharSet());
        final Tokenizer tk = new Tokenizer(fileContent);
        tokenList = tk.getAllToken();
        len = tokenList.size();
        final RootNode root = new RootNode();
        root.setTempleteFile(file);
        while (hasNext()) {
            next().parseNode(tEngine, root, root, this);
        }
        return root;
    }

    public boolean tryMatch(final String[] matchTokens) {
        final Token token = tokenList.get(index);
        for (final String matchToken : matchTokens) {
            if (matchToken.equals(token.getTokenName())) {
                return true;
            }
        }
        return false;
    }

}
