
package lexer;

import javax.print.DocFlavor;
import java.util.*;

/**
 * Created by 42982 on 2017/4/22.
 */
public class AnalysisTable {
    private Production production;
    private Map<String,List<String>> firstMap = new HashMap<>();
    private Map<String,List<String>> followMap = new HashMap<>();
    private List<TableItem> analysisTable;

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Map<String, List<String>> getFirstMap() {
        return firstMap;
    }

    public void setFirstMap(Map<String, List<String>> firstMap) {
        this.firstMap = firstMap;
    }

    public Map<String, List<String>> getFollowMap() {
        return followMap;
    }

    public void setFollowMap(Map<String, List<String>> followMap) {
        this.followMap = followMap;
    }


    public List<TableItem> getAnalysisTable() {
        return analysisTable;
    }

    public void setAnalysisTable(List<TableItem> analysisTable) {
        this.analysisTable = analysisTable;
    }

    public void getfirstMap(Production P){
        int i,j,z,k,m,n,s,t,judge;
        List<String>[] list = new List[100];
        for(i = 0;i < 100;i++){
            list[i] = new ArrayList<String>();
        }
        List<String>[] list_right = new List[100];
        for(i = 0;i < 100;i++){
            list_right[i] = new ArrayList<String>();
        }
        List<String> No_ter_list = P.getNonTerminatingSymbol();
        List<Grammar> PRO = P.getProductions();
        for(i = 0;i < P.getTerminatingSymbol().size();i++){
            list[i].add(P.getTerminatingSymbol().get(i));
            this.firstMap.put(P.getTerminatingSymbol().get(i),list[i]);
        }
        for(i = 0;i < PRO.size();i++){
            j = No_ter_list.indexOf(PRO.get(i).getLeft());
            for(z = 0;z < PRO.get(i).getRights().size();z++){
                String x1[] = PRO.get(i).getRights().get(z);
                char first = x1[0].charAt(0);
                String str = String.valueOf(first);
                if(is_Ter(str,P)||x1[0].equals("ε")){
                    if(first == 'i'||first == 'm'||first == 'f'||first == 'c'||first == 'v'){
                        str = SpecialTer(x1[0]);
                        if(str == null) {
                            str = String.valueOf(first);
                            list_right[j].add(str);
                        }
                        else{
                            list_right[j].add(str);
                        }
                    }
                    else if(x1[0].equals("ε")){
                        list_right[j].add("ε");
                    }
                    else{
                        list_right[j].add(str);
                    }
                }
            }
        }
        judge = 1;
        while(judge == 1)
        {
            judge = 0;
            for(i = 0;i < PRO.size();i++){
                j = No_ter_list.indexOf(PRO.get(i).getLeft());
                for(z = 0;z < PRO.get(i).getRights().size();z++) {
                    String x2[] = PRO.get(i).getRights().get(z);
                    if(is_non_Ter(x2[0],P)){
                        k= No_ter_list.indexOf(x2[0]);
                        if(list_right[k].size() != 0){
                            for(m = 0;m < list_right[k].size();m++) {
                                int ex = 0;
                                for(n = 0;n < list_right[j].size();n++){
                                    if(((list_right[k].get(m).equals(list_right[j].get(n)))|| list_right[k].get(m).equals("ε")))
                                    {
                                        ex = 1;
                                    }
                                }
                                if(ex == 0){
                                    list_right[j].add(list_right[k].get(m));
                                    judge = 1;
                                }
                            }
                            t = 0;
                            for(s = 0;s < x2.length;s++) {
                                if(is_non_Ter(x2[s],P)) {
                                    k = No_ter_list.indexOf(x2[s]);
                                    int havenull = 0;
                                    for(i = 0;i < list_right[k].size();i++){
                                        if (list_right[k].get(i).equals("ε") ) {
                                            t++;
                                            havenull = 1;
                                            break;
                                        }
                                    }
                                    if(havenull == 0){
                                        break;
                                    }
                                }
                                else if(is_Ter(x2[s],P)){
                                    t++;
                                    break;
                                }
                                else{
                                    break;
                                }
                            }
                            if(t == list_right[k].size() && (!(is_Ter(x2[t],P)))){
                                list_right[j].add("ε");
                                judge = 1;
                            }
                            else if(t!=0) {
                                for (s = 0; s < t; s++) {
                                    if(is_non_Ter(x2[s],P)) {
                                        k = No_ter_list.indexOf(x2[s]);
                                        for (m = 0; m < list_right[k].size(); m++) {
                                            int ex1 = 0;
                                            for (n = 0; n < list_right[j].size(); n++) {
                                                if (((list_right[k].get(m).equals(list_right[j].get(n))) || list_right[s].get(m).equals("ε"))) {
                                                    ex1 = 1;
                                                }
                                            }
                                            if (ex1 == 0) {
                                                list_right[j].add(list_right[k].get(m));
                                                judge = 1;
                                            }
                                        }
                                    }
                                    else if(is_Ter(x2[s],P)){
                                        int EX = 0;
                                        for (n = 0; n < list_right[j].size(); n++) {
                                            if (((x2[s].equals(list_right[j].get(n))) || x2[s].equals("ε"))) {
                                                EX = 1;
                                            }
                                        }
                                        if (EX == 0) {
                                            list_right[j].add(x2[s]);
                                            judge = 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(i = 0;i < PRO.size();i++){
            j = No_ter_list.indexOf(PRO.get(i).getLeft());
            this.firstMap.put(P.getNonTerminatingSymbol().get(j),list_right[j]);
        }
    }


    public void getfollowMap(Production P,String start){
        int set_fir,i,j,z,k,m,n,s,t,judge;
        List<String> list = new ArrayList<>();
        List<String>[] list_right = new List[100];
        for(i = 0;i < 100;i++){
            list_right[i] = new ArrayList<String>();
        }
        List<String> No_ter_list = P.getNonTerminatingSymbol();
        List<Grammar> PRO = P.getProductions();
        set_fir = No_ter_list.indexOf(start);
        list_right[set_fir].add("#");
        judge = 1;
        while(judge == 1){
            judge = 0;
            for(i = 0;i < PRO.size();i++){
                for(z = 0;z < PRO.get(i).getRights().size();z++){
                    String x3[] = PRO.get(i).getRights().get(z);
                    if(x3.length > 1) {
                        for (m = 0; m < x3.length - 1; m++) {
                            if (is_non_Ter(x3[m], P) && is_Ter(x3[m + 1], P) && (!(x3[m + 1].equals("ε")))) {
                                j = No_ter_list.indexOf(x3[m]);
                                int xz = 0;
                                for(n = 0;n < list_right[j].size();n++) {
                                    if (x3[m+1].equals(list_right[j].get(n))){
                                        xz = 1;
                                        break;
                                    }
                                }
                                if(xz == 0){
                                    list_right[j].add(x3[m+1]);
                                    judge = 1;
                                }
                            }
                            if (is_non_Ter(x3[m], P) && is_non_Ter(x3[m + 1], P) && (!(x3[m + 1].equals("ε")))) {
                                list = this.firstMap.get(x3[m + 1]);
                                j = No_ter_list.indexOf(x3[m]);
                                for (k = 0; k < list.size(); k++) {
                                    int ex2 = 0;
                                    for (n = 0; n < list_right[j].size(); n++) {
                                        if (((list.get(k).equals(list_right[j].get(n))) || list.get(k).equals("ε"))) {
                                            ex2 = 1;
                                        }
                                    }
                                    if (ex2 == 0) {
                                        list_right[j].add(list.get(k));
                                        judge = 1;
                                    }
                                }
                                int num = 0;
                                for (n = m + 1; n < x3.length; n++) {
                                    list = this.firstMap.get(x3[n]);
                                    for (k = 0; k < list.size(); k++) {
                                        if (list.get(k).equals("ε")) {
                                            num++;
                                            break;
                                        }
                                    }
                                }
                                int sum;
                                if (num + 1 + m < x3.length) {
                                    sum = num + m + 1 + 1;
                                } else {
                                    sum = num + m + 1;
                                }
                                for (s = m + 1; s < sum; s++) {
                                    list = this.firstMap.get(x3[s]);
                                    for (k = 0; k < list.size(); k++) {
                                        int xy = 0;
                                        for (n = 0; n < list_right[j].size(); n++) {
                                            if (((list.get(k).equals(list_right[j].get(n))) || list.get(k).equals("ε"))) {
                                                xy = 1;
                                            }
                                        }
                                        if (xy == 0) {
                                            list_right[j].add(list.get(k));
                                            judge = 1;
                                        }
                                    }
                                }
                            }
                            if (is_non_Ter(x3[m + 1], P) && m == x3.length - 2) {
                                if (!(x3[m + 1].equals(PRO.get(i).getLeft()))) {
                                    list = list_right[No_ter_list.indexOf(PRO.get(i).getLeft())];
                                    j = No_ter_list.indexOf(x3[m + 1]);
                                    for (k = 0; k < list.size(); k++) {
                                        int ex3 = 0;
                                        for (n = 0; n < list_right[j].size(); n++) {
                                            if (list.get(k).equals(list_right[j].get(n))) {
                                                ex3 = 1;
                                            }
                                        }
                                        if (ex3 == 0) {
                                            list_right[j].add(list.get(k));
                                            judge = 1;
                                        }
                                    }
                                }
                            }
                            if (is_non_Ter(x3[m], P) && is_non_Ter(x3[m + 1], P) && m < x3.length - 1) {
                                if (!(x3[m + 1].equals(PRO.get(i).getLeft()))) {
                                    j = No_ter_list.indexOf(x3[m]);
                                    int ex5 = 0;
                                    for (s = m + 1; s < x3.length; s++) {
                                        int ex4 = 0;
                                        list = this.firstMap.get(x3[s]);
                                        for (t = 0; t < list.size(); t++) {
                                            if (list.get(t).equals("ε")) {
                                                ex4 = 1;
                                                break;
                                            }
                                        }
                                        if (ex4 == 1) {
                                            ex5++;
                                        } else {
                                            break;
                                        }
                                    }
                                    if (ex5 == x3.length - m - 1) {
                                        list = list_right[No_ter_list.indexOf(PRO.get(i).getLeft())];
                                        for (k = 0; k < list.size(); k++) {
                                            int ex6 = 0;
                                            for (n = 0; n < list_right[j].size(); n++) {
                                                if (list.get(k).equals(list_right[j].get(n))) {
                                                    ex6 = 1;
                                                }
                                            }
                                            if (ex6 == 0) {
                                                list_right[j].add(list.get(k));
                                                judge = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(i = 0;i < PRO.size();i++){
            j = No_ter_list.indexOf(PRO.get(i).getLeft());
            this.followMap.put(PRO.get(i).getLeft(),list_right[j]);
        }
    }

    public void GetTable(Production P){
        int i,j,z,k,m,n,s,t,judge;
        List<String> list = new ArrayList<>();
        List<String> list_right[] = new List[100];
        List<String[]> list_rights = null;
        List<String> No_ter_list = P.getNonTerminatingSymbol();
        List<Grammar> PRO = P.getProductions();
        TableItem cell = new TableItem();
        Grammar unit = new Grammar();
        for(i = 0;i < PRO.size();i++){
            String left = PRO.get(i).getLeft();
            for(j = 0;j < PRO.get(i).getRights().size();j++)
            {
                String right[] = PRO.get(i).getRights().get(j);
                if(is_Ter(right[0],P )){
                    cell.setNonTerminatingSymbol(left);
                    cell.setTerminatingSymbol(right[0]);
                    unit.setLeft(left);
                    list_rights.add(right);
                    unit.setRights(list_rights);
                    cell.setGrammar(unit);
                    this.analysisTable.add(cell);
                }
                if(is_non_Ter(right[0],P)){
                    t = 0;
                    for(k = 0;k <right.length;k++)
                    {
                        if(is_Ter(right[k],P)){
                            break;
                        }
                        else{
                            list = this.firstMap.get(right[k]);
                            int ex7 = 0;
                            for(z = 0;z < list.size();z++) {
                                if (list.get(z).equals("ε")){
                                    ex7 = 1;
                                }
                            }
                            if(ex7 == 1)
                            {
                                t++;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    if(t == right.length){
                        list = this.followMap.get(left);
                        for(z = 0;z < list.size();z++) {
                            cell.setNonTerminatingSymbol(left);
                            cell.setTerminatingSymbol(list.get(z));
                            unit.setLeft(left);
                            list_rights.add(right);
                            unit.setRights(list_rights);
                            cell.setGrammar(unit);
                            this.analysisTable.add(cell);
                        }
                    }
                    if(t < right.length){
                        for(s = 0;s < t;s++)
                        {
                            list = this.firstMap.get(right[s]);
                            for(z = 0;z < list.size();z++){
                                cell.setNonTerminatingSymbol(left);
                                cell.setTerminatingSymbol(list.get(z));
                                unit.setLeft(left);
                                list_rights.add(right);
                                unit.setRights(list_rights);
                                cell.setGrammar(unit);
                                this.analysisTable.add(cell);
                            }
                        }
                    }
                }
            }
        }
    }



    public String SpecialTer(String x){
        if(x.contains("if")){
            String y = "if";
            return y;
        }
        else if (x.contains("int")){
            String y = "int";
            return y;
        }
        else if (x.contains("void")) {
            String y = "void";
            return y;
        }
        else if (x.contains("char")) {
            String y = "char";
            return y;
        }
        else if (x.contains("float")) {
            String y = "float";
            return y;
        }
        else if (x.contains("main")) {
            String y = "main";
            return y;
        }
        else{
            return null;
        }
    }

    public boolean is_Ter(String x,Production P){
        int i;
        boolean judge = false;
        for(i = 0;i < P.getTerminatingSymbol().size();i++){
            if (x.equals(P.getTerminatingSymbol().get(i)))
            {
                judge = true;
            }
        }
        return judge;
    }

    public boolean is_non_Ter(String x,Production P){
        int i;
        boolean judge = false;
        for(i = 0;i < P.getNonTerminatingSymbol().size();i++){
            if (x.equals(P.getNonTerminatingSymbol().get(i)))
            {
                judge = true;
            }
        }
        return judge;
    }


}

