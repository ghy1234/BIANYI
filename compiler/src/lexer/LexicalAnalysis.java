package lexer;

/**
 * Created by 42982 on 2017/4/15.
 */
import com.sun.org.apache.bcel.internal.generic.EmptyVisitor;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.util.*;

public class LexicalAnalysis {
    String filename;
    File fr = new File("c:\\Test1.txt");
    int count = 0;
    char buffer[]=new char[100];
    public LexicalAnalysis(String filename)
    {
        this.filename=filename;
    }

    public void readFile()throws FileNotFoundException
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fr));
            do {
                count = br.read(buffer);
            } while (count != -1);
            br.close();
        } catch (Exception e) {
            System.out.println("文件操作错误：" + e.toString());
        }
    }

    boolean IsLetters(char ch) //字母判断
    {
        if(ch>='a' && ch<='z' || ch>='A' && ch<='Z')
            return true;
        else
            return false;
    }

    boolean IsBlank(char ch) //空格判断
    {
        if(ch==' ')
            return true;
        else
            return false;
    }

    boolean IsNumber(char ch) //数字判断
    {
        if(ch>='0' && ch<='9')
            return true;
        else
            return false;
    }

    boolean IsOperator(char ch) //运算符判断
    {
        char a[]={'+','-','*','/','=','<','>','!','&','|'};
        int j=0;
        for(j=0;j<a.length;j++)
        {
            if(ch==a[j])
                return true;
        }
        return false;
    }

    boolean IsSeparator(char ch) //分隔符判断
    {
        int k;
        char b[]={',',';','{','}','(',')'};
        for(k=0;k<b.length;k++)
        {
            if(ch==b[k])
                return true;
        }
        return false;
    }

    public void Analysis(List list, List<Object[]> list1)
    {
        int i=0;
        char ch;
        while(i<buffer.length)
        {
            ch=buffer[i];
            if(IsLetters(ch)) //保留字和标示符判断
            {
                String c[]={"if","int","for","while","do","return","break","continue"};
                String word="";
                boolean flag=true; //是否是保留字或标示符的标志
                while(IsLetters(ch)||IsNumber(ch))
                {
                    word+=ch;
                    i++;
                    ch=buffer[i];
                    if(IsNumber(ch)) //若单词中有数字，则被认为是标示符
                        flag=false;
                }
                if(flag==true)
                {
                    int m=0;
                    for(m=0;m<c.length;m++)
                    {
                        if(word.equals(c[m]))
                        {
                            list1.add(new Object[]{list.indexOf(c[m]),word});
                            System.out.println("("+list.indexOf(c[m])+",'"+word+"')");
                            break;
                        }
                    }
                    if(m>=c.length) {
                        list1.add(new Object[]{1, word});
                        System.out.println("(1,'" + word + "')");
                    }
                }
                else {
                    list1.add(new Object[]{1, word});
                    System.out.println("(1,'" + word + "')");
                }
            }
            else if(IsNumber(ch)) //数字处理
            {
                String num="";
                int judge = 0;
                while((IsNumber(ch)|| ch=='.') && i<buffer.length)
                {
                    if(ch == '.')
                    {
                        judge = 1;
                    }
                    num+=ch;
                    i++; //下一个字符
                    ch=buffer[i];
                }
                try {
                    if (judge == 0) {
                        int p = Integer.parseInt(num);
                        list1.add(new Object[]{2,p});
                        System.out.println("(2,'" + p + "')"); //异常处理
                    } else {
                        double p = Double.parseDouble(num);
                        list1.add(new Object[]{3,p});
                        System.out.println("(3,'" + p + "')"); //异常处理
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(num + "非法字符，无法转换！");
                }
            }
            while(IsBlank(ch)&& i<buffer.length) //空格跳过
            {
                i++; //下一个字符
                ch=buffer[i];
            }
            if(IsOperator(ch)&& i<buffer.length) //运算符处理
            {
                char x=ch;
                String y=""+x;
                if(x=='<' || x=='>' || x=='!')
                {
                    ch=buffer[i+1];
                    if(ch=='=') {
                        y = y + ch;
                        i++;
                    }
                }
                else if(x == '=')
                {
                    ch=buffer[i+1];
                    if(ch =='=') {
                        y = y + ch;
                        i++;
                    }
                }
                else if(x == '&')
                {
                    ch=buffer[i+1];
                    if(ch =='&') {
                        y = y + ch;
                        i++;
                    }
                }
                else if(x == '|')
                {
                    ch=buffer[i+1];
                    if(ch =='|') {
                        y = y + ch;
                        i++;
                    }
                }
                i++; //下一个字符
                list1.add(new Object[]{list.indexOf(y),y});
                System.out.println("("+list.indexOf(y)+",'"+y+"')");
            }
            else if(IsSeparator(ch)&& i<buffer.length) //分隔符处理
            {
                i++; //下一个字符
                list1.add(new Object[]{list.indexOf(ch),ch});
                System.out.println("("+list.indexOf(ch)+",'"+ch+"')");
            }
            if(!IsSeparator(ch)&&!IsBlank(ch)&&!IsOperator(ch)&&!IsNumber(ch)&&!IsLetters(ch)) //非法字符处理
            {
                if(IsBlank(ch))
                    System.out.println("Error!="+ch);
                i++;
            }
        }
    }

    public static void main(String args[])throws IOException
    {
        int i,j,k;
        List list = new ArrayList();
        List<Object[]> list1= new ArrayList();
        list.add("标识符");
        list.add("整数");
        list.add("浮点数");
        list.add("+");
        list.add("-");
        list.add("*");
        list.add("/");
        list.add("=");
        list.add("<");
        list.add(">");
        list.add("!");
        list.add("==");
        list.add(">=");
        list.add("<=");
        list.add("!=");
        list.add("&&");
        list.add("||");
        list.add("if");
        list.add("int");
        list.add("for");
        list.add("while");
        list.add("do");
        list.add("return");
        list.add("break");
        list.add("continue");
        list.add("float");
        list.add("short");
        list.add("long");
        list.add("double");
        list.add("char");
        list.add("struct");
        list.add("typedef");
        list.add("unsigned");
        list.add("signed");
        list.add("static");
        list.add("void");
        list.add("else");
        list.add("switch");
        list.add("case");
        list.add("while");
        list.add("goto");
        list.add(',');
        list.add(';');
        list.add('{');
        list.add('}');
        list.add('(');
        list.add(')');
        LexicalAnalysis fr=new LexicalAnalysis("C:\\Test1.txt");
        fr.readFile();
        fr.Analysis(list,list1);
        System.out.println("\n");
        for(i=0;i<list1.size();i++)
        {
            System.out.println("("+list1.get(i)[0]+",'"+list1.get(i)[1]+"')");
        }

        ///////////////////////////////////////////

        AnalysisTable Analyse = new AnalysisTable();
        Production PRO = new Production();
        PRO.addproductions();
        PRO.getProductions().get(0).setLeft("E");
        PRO.setBegin("E");
        PRO.SymbolTest(list);
        List<String> Ter = PRO.getTerminatingSymbol();
        List<String> No_Ter = PRO.getNonTerminatingSymbol();
        for(i = 0;i < Ter.size();i++){
            System.out.println(Ter.get(i));
        }
        for(i = 0;i < No_Ter.size();i++){
            System.out.println(No_Ter.get(i));
        }
        Analyse.getfirstMap(PRO);
        Map<String,List<String>> FIR = Analyse.getFirstMap();
        Analyse.getfollowMap(PRO,"E");
        Map<String,List<String>> FOL = Analyse.getFollowMap();
        Analyse.GetTable(PRO);
        Analyse.setProduction(PRO);

        String[] test = new String[]{"0","+","0","*","0","#"};
        Stack<String> S = new Stack<String>();
        SyntaxAnalyzer ANA = new SyntaxAnalyzer();
        int table_length = ANA.Analyzer(Analyse,test,S);
        for(i = 0;i < table_length+1;i++){
            System.out.print(ANA.getResult()[i][0]+"\t");
            System.out.print(ANA.getResult()[i][1]+"\t");
            System.out.print(ANA.getResult()[i][2]+"\t");
            System.out.print(ANA.getResult()[i][3]+"\t");
            System.out.println();
        }

    }
}