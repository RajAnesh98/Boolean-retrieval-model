/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.assignment.pkg1;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




/**
 *
 * @author Raj Anesh
 */
public class QueryHandling {
    ArrayList<String> queries;
    Lexicon lex;

    QueryHandling(Lexicon l) {
        queries = new ArrayList();
        lex =l ;

    }

    String runQuery(String FolderName) {
        FileInputStream input = null;
        Scanner scan;
        String ResultS="";
        try {
            String FileName = FolderName + "\\QueryList.txt";
            input = new FileInputStream(FileName);
            scan = new Scanner(input);
            
            while (scan.hasNext()) {
                queries.add(scan.nextLine());
            }
            
        
            for (int i = 0; i < queries.size(); i++) {
                ResultS += findResult(queries.get(i));
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QueryHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResultS;
    }

    void printQueries() {
       
        for (int i = 0; i < queries.size(); i++) {
            System.out.println(queries.get(i));
        }
    }

    String findResult(String q) {
        
        String ResultS= "";
        
        ArrayList<String> Operations = new ArrayList();
        ArrayList<String> Terms = new ArrayList();
         ArrayList<Integer> result;
         
        if(q.indexOf("'/") != -1)
        {
            result=lex.Proximity(q);
        }
        else
        {
        String[] ParsedQuery = q.split(" ");
        for (int i = 0; i < ParsedQuery.length; i++) {
            if (ParsedQuery[i].equals("AND") || ParsedQuery[i].equals("OR")) {
                Operations.add(ParsedQuery[i]);
            } else if (ParsedQuery[i].equals("NOT")) {
                ParsedQuery[i]=ParsedQuery[i];
                ParsedQuery[i+1]=ParsedQuery[i+1].replaceAll("[^\\w\\s]", "");
                ParsedQuery[i+1]=ParsedQuery[i+1].toLowerCase();
                Terms.add(ParsedQuery[i] + " " + ParsedQuery[i + 1]);
                i++;
            } else {
                ParsedQuery[i]=ParsedQuery[i].replaceAll("[^\\w\\s]", "");
                ParsedQuery[i]=ParsedQuery[i].toLowerCase();
                Terms.add(ParsedQuery[i]);
            }
        }
            
            result = lex.Termdocuments(Terms.get(0));
            ArrayList<Integer> result2;
            for (int i = 0; i < Operations.size(); i++) {
                result2 = lex.Termdocuments(Terms.get(i + 1));

                if (Operations.get(i).equals("AND")) {
                    result = AND(result, result2);
                } else {
                    result = OR(result, result2);
                }
            }
        
        ResultS += "Query Result ->\"" + q + "\" : ";
        if (result.size() > 0) {
            ResultS += result + "\n";
        } else {
            ResultS += "No Relevent Document\n";
        }
        ResultS += "\n";
        }
        return ResultS;
    }

    ArrayList<Integer> AND(ArrayList<Integer> First, ArrayList<Integer> Second) {
        
        ArrayList<Integer> result = new ArrayList();
        for (int i = 0; i < First.size() || i < Second.size(); i++) {
            if(i>=First.size())
            {
                if(First.contains(Second.get(i))&& !result.contains(Second.get(i)))
                {
                    result.add(Second.get(i));
                }
            }
            else
                if(i>=Second.size())
                {
                    if(Second.contains(First.get(i))&& !result.contains(First.get(i)))
                {
                    result.add(First.get(i));
                }
                    
                }
            else
            {
                if (Second.contains(First.get(i))) {
                     result.add(First.get(i));
              }
            }
        }
        return result;

    }

    ArrayList<Integer> OR(ArrayList<Integer> First, ArrayList<Integer> Second) {
       
        // *** Intersecting The Two Array Lists ***
        for (int i = 0; i < Second.size(); i++) {
            if (!First.contains(Second.get(i))) {
                First.add(Second.get(i));
            }
        }
        for (int i = 0; i < First.size(); i++) {
            for (int j = 0; j < First.size() - 1; j++) {
                if (First.get(j) > First.get(j + 1)) {
                    int temp = First.get(j);

                    
                    First.remove(j);
                    First.add(j, First.get(j));

                    First.remove(j + 1);
                    First.add(j+1, temp);

                }
            }
        }
        return First;
    }
}
