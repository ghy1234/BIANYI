package lexer;

import java.util.List;
import java.util.Stack;

/**
 * Created by 42982 on 2017/4/22.
 */
public class SyntaxAnalyzer {
    private AnalysisTable analysisTable;
    private Stack<String> stack = new Stack<String>();

    public AnalysisTable getAnalysisTable() {
        return analysisTable;
    }

    public void setAnalysisTable(AnalysisTable analysisTable) {
        this.analysisTable = analysisTable;
    }

    public Stack<String> getStack() {
        return stack;
    }

    public void setStack(Stack<String> stack) {
        this.stack = stack;
    }

    public void Analyzer(AnalysisTable A,String[] X,Stack<String> S){
        String start = A.getProduction().getBegin();
        List<TableItem> table = A.getAnalysisTable();
        S.push("#");
        S.push(start);
        int index = 0;
        int i,j,judge;
        while(!(S.peek().equals("#"))){
            String top = S.peek();
            if((A.is_Ter(top,A.getProduction()))||top.equals("#")){
                if(X[index].equals(top)){
                    if(!(top.equals("#"))){
                        S.pop();
                        index++;
                    }
                }
                else{
                    System.out.println("error");
                }
            }
            else{
                judge = 0;
                for(i = 0;i < table.size();i++){
                    if(top.equals(table.get(i).getNonTerminatingSymbol()) && (X[index].equals(table.get(i).getTerminatingSymbol()))){
                        S.pop();
                        String[] list=table.get(i).getGrammar().getRights().get(0);
                        if(!(list[0].equals("ε"))){
                            for(j = list.length-1;j >= 0;j--){
                               S.push(list[j]);
                            }
                        }
                        System.out.print(table.get(i).getGrammar().getLeft());
                        System.out.print("->");
                        System.out.print(list);
                        System.out.print("\n");
                        judge = 1;
                        break;
                    }
                }
                if(judge == 0){
                    System.out.print("error");
                }
            }

        }

    }

}
