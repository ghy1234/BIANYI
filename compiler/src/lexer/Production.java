package lexer;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by 42982 on 2017/4/22.
 */
public class Production {
    private String begin="";                                      //开始符号
    private List<Grammar> productions = new LinkedList<Grammar>(); // 产生式
    private List<String> nonTerminatingSymbol = new ArrayList<String>(); // LL(1)文法非终结符
    private List<String> terminatingSymbol = new ArrayList<String>(); // LL(1)文法终结符

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public List<Grammar> getProductions() {
        return productions;
    }

    public void setProductions(List<Grammar> productions) {
        this.productions = productions;
    }

    public List<String> getNonTerminatingSymbol() {
        return nonTerminatingSymbol;
    }

    public void setNonTerminatingSymbol(List<String> nonTerminatingSymbol) {
        this.nonTerminatingSymbol = nonTerminatingSymbol;
    }

    public List<String> getTerminatingSymbol() {
        return terminatingSymbol;
    }

    public void setTerminatingSymbol(List<String> terminatingSymbol) {
        this.terminatingSymbol = terminatingSymbol;
    }

    public void addproduction(Grammar list){
        this.productions.add(list);
    }


    public void SymbolTest(List<String> list){
        int i;
        String str;
        for(i = 0;i < this.productions.size();i++){
            this.nonTerminatingSymbol.add(this.productions.get(i).getLeft());
        }
        this.terminatingSymbol.add(")");
        this.terminatingSymbol.add("(");
        this.terminatingSymbol.add("*");
        this.terminatingSymbol.add("+");
        this.terminatingSymbol.add("0");
        /*for(i = 3;i < 41;i++){
            this.terminatingSymbol.add(list.get(i));
        }
        for(i = 0;i < 10;i++){
            str = String.valueOf(i);
            this.terminatingSymbol.add(str);
        }
        for(char a = 'a';a <= 'z';a++){
            str = String.valueOf(a);
            this.terminatingSymbol.add(str);
        }
        this.terminatingSymbol.add(",");
        this.terminatingSymbol.add(";");
        this.terminatingSymbol.add("{");
        this.terminatingSymbol.add("}");
        this.terminatingSymbol.add("(");
        this.terminatingSymbol.add(")");*/
    }

    public void addproductions(){
        String[] STR = new String[100];
        int count = 0;
        int i,j;
        try {
            // read file content from file

            FileReader reader = new FileReader("c://test3.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {
                STR[count] = str;
                System.out.println(str);
                count++;
            }

            br.close();
            reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        for(i = 0;i < count;i++){
            Grammar gra = new Grammar();
            List<String[]> right = new ArrayList<String[]>();
            String left;
            String[] x,y;
            String buffer;
            buffer = STR[i].replace(" ","");
            x = buffer.split("->");
            left = x[0];
            y = x[1].split("\\|");
            for(j = 0;j < y.length;j++){
                right.add(y[j].split(","));
            }
            gra.add_grammar(left,right);
            this.productions.add(gra);
        }

    }


}



