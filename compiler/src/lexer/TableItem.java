package lexer;

/**
 * Created by 42982 on 2017/4/23.
 */
public class TableItem {
        private String nonTerminatingSymbol;
        private String terminatingSymbol;
        private Grammar grammar;

    public String getNonTerminatingSymbol() {
        return nonTerminatingSymbol;
    }

    public void setNonTerminatingSymbol(String nonTerminatingSymbol) {
        this.nonTerminatingSymbol = nonTerminatingSymbol;
    }

    public String getTerminatingSymbol() {
        return terminatingSymbol;
    }

    public void setTerminatingSymbol(String terminatingSymbol) {
        this.terminatingSymbol = terminatingSymbol;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }
}
