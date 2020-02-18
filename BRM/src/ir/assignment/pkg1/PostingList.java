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
public class PostingList {
    ArrayList<Term> PostListing;
    PostingList() {
        PostListing = new ArrayList();
    }

    void addToPostingList(String Word, int DocID, int Position) {
        
        int n = getPosition(Word);
        if (n != -1) {
            
            //ismein hm duplicate word check krte hain document mein or agr duplicate hai to hm srf doc id or position add krte hain///
            if (PostListing.get(n).Documents.get(PostListing.get(n).Documents.size() - 1) != DocID) {
                PostListing.get(n).addDocumentId(DocID,Position);
//                System.out.println("1 wala--->"+DocID);
//                System.out.println("1 wala--->"+Position);
            }
            PostListing.get(n).addPosition(DocID, Position);
//            System.out.println("1 wala--->"+DocID);
//            System.out.println("1 wala--->"+Position);
            
        } else {
            Term t = new Term(Word, DocID,Position);
            PostListing.add(t);
//            System.out.println("-1 wala--->"+Word);
//            System.out.println("-1 wala--->"+DocID);
//            System.out.println("-1 wala--->"+Position);
        }
    }
    
    int getPosition(String Word) {
        
//        System.out.println(TermList.size());
        for (int i = 0; i < PostListing.size(); i++) {
              
//            System.out.println();
//            System.out.println("TermList wala name---->"+TermList.get(i).Name);
//            System.out.println("word-->"+Word);
//            System.out.println("Loop wala i---->"+i);
//            System.out.println();
//            
            if (PostListing.get(i).name.equals(Word)) {
           //     System.out.println("If wala i---->"+i);
                return i;
                
            }
        }
        return -1;
    }
    int getPositiron(String Word,int docid)
    {
        for (int i = 0; i <PostListing.size(); i++) {
              
//            System.out.println();
//            System.out.println("TermList wala name---->"+TermList.get(i).Name);
//            System.out.println("word-->"+Word);
//            System.out.println("Loop wala i---->"+i);
            System.out.println();
            if(PostListing.get(docid).equals(i))
            {
                    if (PostListing.get(i).name.equals(Word))
                    {
  //              System.out.println("If wala i---->"+i);
                        return i;
                
                    }
            }
        }
        return -1;
    }
        

    void printPostingList() {
        System.out.println(PostListing.size());
        
        
        for (int i = 0; i < PostListing.size(); i++) {
            System.out.println(PostListing.get(i).name + " -> ");
                for(int j=0;j<PostListing.get(i).Documents.size();j++){
                    System.out.print("Document No. ---> " + PostListing.get(i).Documents.get(j) + " Position Index --->");
                    
                    System.out.println( PostListing.get(i).PositionIndex.get(j));
                    
                }
            System.out.println("");
        }
    }

    void SortList() {
        for (int i = 0; i < PostListing.size(); i++) {
            for (int j = 0; j < PostListing.size() - 1; j++) {
                if (PostListing.get(j).name.compareTo(PostListing.get(j + 1).name) > 0) {

                    Term temp = new Term("", 1,1);

                    temp.name = PostListing.get(j).name;
                    temp.Documents = PostListing.get(j).Documents;
                    temp.PositionIndex = PostListing.get(j).PositionIndex;

                    PostListing.get(j).name = PostListing.get(j + 1).name;
                    PostListing.get(j).Documents = PostListing.get(j + 1).Documents;
                    PostListing.get(j).PositionIndex = PostListing.get(j + 1).PositionIndex;

                    PostListing.get(j + 1).name = temp.name;
                    PostListing.get(j + 1).Documents = temp.Documents;
                    PostListing.get(j + 1).PositionIndex = temp.PositionIndex;
                    

                }
            }
        }
    }
   
    
    
}
