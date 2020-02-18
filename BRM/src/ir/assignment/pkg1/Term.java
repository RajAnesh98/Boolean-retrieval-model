/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.assignment.pkg1;

import java.util.ArrayList;

/**
 *
 * @author Raj Anesh
 */
public class Term {
    public String name;
    public ArrayList<Integer> Documents;
    public ArrayList<ArrayList<Integer>> PositionIndex;

    Term(String n, int DocID,int Position) {
        name = n;
        
        Documents = new ArrayList();
        Documents.add(DocID);
        
        PositionIndex = new ArrayList(); 
        PositionIndex.add(new ArrayList<Integer>());
        PositionIndex.get(Documents.indexOf(DocID)).add(Position);
    }

    void addDocumentId(int DocID,int Position) {
        Documents.add(DocID);
        PositionIndex.add(new ArrayList<Integer>());
    }
    
    void addPosition(int DocID,int Pos){   
        PositionIndex.get(Documents.indexOf(DocID)).add(Pos);
    }
     ArrayList<Integer> returnArrayListOfTermInPositionalList(int DocID){
       return PositionIndex.get(Documents.indexOf(DocID));
    }

   
}
