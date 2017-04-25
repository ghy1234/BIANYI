package lexer;

import java.util.List;

/**
 * Created by 42982 on 2017/4/23.
 */
public class Grammar {
        private String left;
        private List<String[]> rights;
        private int id;

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public List<String[]> getRights() {
        return rights;
    }

    public void setRights(List<String[]> rights) {
        this.rights = rights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void add_grammar(String left,List<String[]> rights){
        this.setLeft(left);
        this.setRights(rights);
    }
}

