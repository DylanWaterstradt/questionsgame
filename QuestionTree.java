//Name: Dylan Waterstradt
//Class: CS &145
//Date: June 02, 2023
//Class: QuestionTree
//Details:
//This is our tree manager class.
//Basically it processes nodes
//starting at root and as the
//computer starts developing responses
//it will store them in files to remember
//for next time.

import java.util.*;
import java.io.*;

public class QuestionTree {

   //how many times the computer has won
   private int computerWon = 0;
   
   //how many times the user has won
   private int userWon = 0;
   
   //How many games have been played
   private int gamesPlayed = 0;

   //null for an empty tree
   private QuestionNode root;
   
   //initialized by constructor
   private QuestionMain ui;
   
   //Constructor for 20 questions (question tree)
   public QuestionTree(QuestionMain ui) {
      root = new QuestionNode("Computer");
      this.ui = ui;
   }
   
   //returns the number of times the user has won.
   public int gamesWon() {
      return userWon;
   }
   
   //returns the total number of games played
   public int totalGames() {
      return gamesPlayed;
   }
   
   /*
    * Method type: void
    * Returns: N/A
    * Parameters:
    * Scanner input - for reading from file.
    * Details:
    * While the scanner file has next line
    * it will load into root the details
    * of the file.
    */
   public void load(Scanner input) {
      while(input.hasNextLine()) {
         root = loadHelper(input);
      }
   }
   
   /*
    * Method type: QuestionNode
    * Returns branch - the processed node
    * Parameters: Scanner input - for reading from file.
    * Details:
    * Reads a line of input and sets it to type, then
    * reads another line and sets it to data.
    * if the type contains a question or answer
    * it will set the appropriate node to that.
    */
   public QuestionNode loadHelper(Scanner input) {
      String type = input.nextLine();
      String data = input.nextLine();
      
      QuestionNode branch = new QuestionNode(data);
      
      if(type.contains("Q:")) {
         branch.left = loadHelper(input);
         branch.right = loadHelper(input);
      }
      
      return branch;
   }
   
   /*
    * Method type: Void
    * Returns: N/A
    * Parameters: Printstream output - the file to write to.
    * Details:
    * If there is a problem with file, throw an exception,
    * otherwise called the save(QuestionNode, Printstream)
    * method.
    */
   public void save(PrintStream output) {
      if(output == null) {
         throw new IllegalArgumentException();
      }
      
      save(root, output);
   }
   
   /*
    * Method type: void
    * Returns: N/A
    * Parameters:
    * QuestionNode root - the node containing data
    * PrintStream output - the file to write to.
    * Details:
    * Checks to see if the node is a leaf node.
    * If so it saves it as an answer, otherwise
    * it saves it as a question and then reprocesses
    * the sub-roots.
    */
   public void save(QuestionNode root, PrintStream output) {
      if(isLeaf(root)) {
         output.println("A:");
         output.println(root.answer);
      } else {
         output.println("Q:");
         output.println(root.answer);
         save(root.left, output);
         save(root.right, output);
      }
   }
   
   //check to see if we are at a leaf node.
   public boolean isLeaf(QuestionNode branch) {
      if(branch.left == null || branch.right == null) {
         return true;
      }
      return false;
   }
   
   /*
    * Method type: Void
    * Returns: N/A
    * Details:
    * sets root to the return value of playe(QuestionNode).
    */
   public void play() {
      root = play(root);
   }
   
   /*
    * Method type: QuestionNode
    * Returns: currentBranch - the current node
    * Parameters: QuestionNode currentBranch - current node
    * Details:
    * Checks to see if the current branch is a leaf
    * if so it will try to answer the question.  If its not
    * It will begin to ask questions until it gets to an
    * leaf node.  If its wrong it will ask the user for
    * the correct answer, and ask a question to decipher it.
    */
   private QuestionNode play(QuestionNode currentBranch) {
      String object = currentBranch.answer;
      String response;
      if(isLeaf(currentBranch)) {
         ui.println("Does your object happen to be a " + object + "?");
         ui.print("Y/N?: ");
         
         //save their response in a string
         response = ui.nextLine().toLowerCase();
         
         //while the user hasn't given us a valid response
         while(!response.startsWith("y") && !response.startsWith("n")) {
            ui.println("Please enter a valid response.");
            ui.println("Does your object happen to be a " + object + "?");
            ui.print("Y/N?: ");
            response = ui.nextLine().toLowerCase();
         }
         
         //if the user answered yes
         if(response.startsWith("y")) {
            ui.println("I win.");
            computerWon++;
            gamesPlayed++;
            
         //if the user answered no   
         } else {
            ui.println("You win.");
            userWon++;
            gamesPlayed++;
         
            ui.print("What is the name of your object?: ");
            QuestionNode answer = new QuestionNode(ui.nextLine());
         
            ui.print("Now give me a (yes/no) question that will help me find the answer: ");
            String question = ui.nextLine();
         
            ui.print("and what is the answer for your question? (yes/no): ");
            response = ui.nextLine().toLowerCase();
         
            while(!response.startsWith("y") && !response.startsWith("n")) {
               ui.println("Please enter a valid response.");
               ui.println("What is the answer for your question? (yes/no): ");
               response = ui.nextLine().toLowerCase();
            }
         
            if(response.startsWith("y")) {
               currentBranch = new QuestionNode(question, answer, currentBranch);
            } else {
               currentBranch = new QuestionNode(question, currentBranch, answer);
            }
         }
      } else {
            ui.println(currentBranch.answer);
            
            response = ui.nextLine().toLowerCase();
            
            while(!response.startsWith("y") && !response.startsWith("no")) {
               ui.println("Please enter a valid response.");
               ui.println("What is your answer for the question? (yes/no): ");
               response = ui.nextLine().toLowerCase();
            }
            
            if(response.startsWith("y")) {
               currentBranch.left = play(currentBranch.left);
            } else {
               currentBranch.right = play(currentBranch.right);
            }
         }
      return currentBranch;
   }
   
   public interface UserInterface {
      public void print(String message); //displays an output message to the user
      public void println(String message); //displays an output message and new-line
      public String nextLine(); //waits for a user to type a string; returns it
      public boolean nextBoolean(); //waits for user to choose yes (true) / no (false)
   }
}