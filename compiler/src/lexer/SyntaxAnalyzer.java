package lexer;

import java.util.List;
import java.util.Stack;

/**
 * Created by 42982 on 2017/4/22.
 */
public class SyntaxAnalyzer {
    private AnalysisTable analysisTable;
    private Stack<String> stack = new Stack<String>();
    private String[][] result = new String[10000][4];


    public String[][] getResult() {
        return result;
    }

    public void setResult(String[][] result) {
        this.result = result;
    }

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

    public int Analyzer(AnalysisTable A,String[] X,Stack<String> S){
        String start = A.getProduction().getBegin();
        List<TableItem> table = A.getAnalysisTable();
        S.push("#");
        S.push(start);
        int index = 0;
        int i,j,judge,process;
        ///////////////////////////
        this.result[0][0] = "步骤";
        this.result[0][1] ="栈";
        this.result[0][2] ="输入缓冲区";
        this.result[0][3] ="输出";
        process = 1;
        /////////////////////////
        while(!(S.peek().equals("#"))){
            /////////////////////////////////////////
            String x = "";
            String z = "";
            this.result[process][0] = String.valueOf(process);
            for(String y:S){
                x = x + y;
            }
            this.result[process][1] = x;
            for(i = index;i < X.length;i++)
            {
                z = z + X[i];
            }
            this.result[process][2] = z;
            ///////////////////////////////////////
            String top = S.peek();
            if((A.is_Ter(top,A.getProduction()))||top.equals("#")){
                if(X[index].equals(top)){
                    if(!(top.equals("#"))){
                        S.pop();
                        index++;
                        process++;
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
                        process++;
                        this.result[process][3] = table.get(i).getGrammar().getLeft() + "->";
                        for(j = 0;j < list.length;j++){
                            this.result[process][3] = this.result[process][3] + list[j];
                        }
                        /*
                        System.out.print(table.get(i).getGrammar().getLeft());
                        System.out.print("->");
                        System.out.print(list);
                        System.out.print("\n");
                        */
                        judge = 1;
                        break;
                    }
                }
                if(judge == 0){
                    System.out.print("error");
                }
            }

        }
        this.result[process][0] = String.valueOf(process);
        this.result[process][1] = "#";
        this.result[process][2] = "#";
        return process;

    }

}
