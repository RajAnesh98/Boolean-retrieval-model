/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.assignment.pkg1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Raj Anesh
 */
public class Lexicon {
    PostingList PostList;
    int No_of_Documents;
    ArrayList<String> StopWordslist;

    Lexicon() {
        PostList = new PostingList();
    }

    void createDictionary(String FolderName ,int n) {

        No_of_Documents = n;
        FileInputStream input = null;
        StopWordslist = new ArrayList();
        String Word;
        Scanner scan;
        try {

            //  STOP WORD LIST //
            String StopWordsFileName = FolderName + "\\Stopword-List.txt";
            input = new FileInputStream(StopWordsFileName);
            scan = new Scanner(input);
            while (scan.hasNext()) {
                Word = scan.next();
                StopWordslist.add(Word);
            }

            String FileName = FolderName + "\\Short Stories" + '\\'; //For the Documents
            for (int i = 1; i <= No_of_Documents; i++) {
                String tempFilename =FileName+i + ".txt";
                input = new FileInputStream(tempFilename);
                scan = new Scanner(input);
                int Position = 0;
                while (scan.hasNext()) {
                    Word = scan.next();
                    Word = Word.toLowerCase();
                    Word = Word.replaceAll("[^\\w\\s]", ""); // Removing Special Characters and Whitespaces

                    if (!StopWordslist.contains(Word) && !Word.equals("")) {
                        PostList.addToPostingList(Word, i, Position);

                    }
                    Position++;
                }
            }
            PostList.SortList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    ArrayList<Integer> Proximity(String t) {
        
        // *** Finding Documents of the Phrase ***
        t.replaceAll("/", "");
        
        String[] ParsedQuery = t.split(" ");
        int k = Integer.parseInt(ParsedQuery[2]);
        
        ArrayList<Integer> result =Termdocuments(ParsedQuery[0]);
   //     ArrayList<Integer> result2 =Termdocuments(ParsedQuery[1]);
        ArrayList<Integer> resultd =new ArrayList();
        int DocumentNo=0;
        ArrayList<Integer> result2=new ArrayList();
        
        
        for (int i = 1; i < ParsedQuery.length; i++) {
            if (!StopWordslist.contains(ParsedQuery[i])) {
            result2 = Termdocuments(ParsedQuery[i]);
                result = Intersection(result, result2);
            }
        }
        result = Intersection(result, result2);
        System.out.println("Result-->"+result);
        ArrayList<ArrayList<Integer>> CurrentPostList = new ArrayList();
        ArrayList<Integer> ResultingSet = new ArrayList();

         
        for (int i = 0; i < result.size(); i++) {
            DocumentNo = result.get(i);
            for (int j = 0; j <= 1 ; j++) {
                if (!StopWordslist.contains(ParsedQuery[j])) {
                    int indexofWord = PostList.getPosition(ParsedQuery[j]);
                    if (indexofWord == -1) {

                        return new ArrayList();
                    }
                    ArrayList<Integer> a = PostList.PostListing.get(indexofWord).returnArrayListOfTermInPositionalList(DocumentNo);
                    CurrentPostList.add(a);
                }
            }
            System.out.println("Doc NO--->"+DocumentNo);
        }
            int f=0;
        //    int k=2;
            
        
            for(int j=0;j<CurrentPostList.get(f).size();j++)
            {
                if(CurrentPostList.get(f).contains(CurrentPostList.get(f+1).get(j)-k))
                {
                   
                   resultd.add(DocumentNo);
                }
            }
         
        return resultd;
    }

    ArrayList<Integer> Termdocuments(String t) {
        
        String temp[] = t.split(" ");
        int position;
        
        if (temp[0].equals("NOT")) {
            position = PostList.getPosition(temp[1]);
            ArrayList<Integer> tempArray = new ArrayList();
            ArrayList<Integer> tempArray2;
            if (position != -1) {
                tempArray2 = PostList.PostListing.get(position).Documents;
            } else {
                for (int i = 1; i <= No_of_Documents; i++) {
                    tempArray.add(i);
                }
                return tempArray;
            }
            for (int i = 1; i <= No_of_Documents; i++) {
                if (!tempArray2.contains(i)) {
                    tempArray.add(i);
                }
            }
            return tempArray;
        } else {
            position = PostList.getPosition(t);
            if (position == -1) {
                return new ArrayList();
            } else {
                return PostList.PostListing.get(position).Documents;
            }
        }
    }

    ArrayList<Integer> Intersection(ArrayList<Integer> ListOFFIRSTTERM, ArrayList<Integer> ListOFSecondTERM) {
        
        ArrayList<Integer> Result = new ArrayList();
        for (int i = 0; i < ListOFFIRSTTERM.size() && i < ListOFSecondTERM.size(); i++) {
            if (ListOFFIRSTTERM.contains(ListOFSecondTERM.get(i))) {
                Result.add(ListOFSecondTERM.get(i));
            }
        }
        return Result;
    }

}
