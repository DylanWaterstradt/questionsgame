//name: Dylan Waterstradt
//Class: CS &145
//Date: May 30, 2023
//Class: QuestionNode
//Details:
//A simple node class that holds
//specific values for each node
//and can tree out into other nodes

public class QuestionNode {
   
   //answer stored at this node
   public String answer;
   //question stored at this node
   public String question;
   //reference to left sub-tree
   public QuestionNode left;
   //reference to right sub-tree
   public QuestionNode right;
   
   //Constructs a leaf node with the given answer
   public QuestionNode(String answer) {
      this(answer, null, null);
   }
   
   //Constructs a branch node with the given answer and links
   public QuestionNode(String answer, QuestionNode left,
                                      QuestionNode right) {
      this.answer = answer;
      this.left = left;
      this.right = right;
   }
}