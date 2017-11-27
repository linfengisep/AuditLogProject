import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.*;
import java.security.MessageDigest;



public class MerkleTree {

   public MerkleTree parentTree=null;
   public MerkleTree currentTree=null;
   //pointer? to the left and right subtree;
   public MerkleTree leftSubTree=null;
   public MerkleTree rightSubTree=null;

   //begining and ending index of the log;
   public int beginIndex=0,endIndex=0;//how to assign the index to tree?
   // The hash value of this node
   public byte[] hash;//change to name hash later;
   public Boolean isLeaf=false;/*how to tell a current node is leaf or internal node?*/
   public long maxID=0;

   /*Others
   *public int level=0;
   *The digest algorithm
   *public final MessageDigest md;
   */
   //function contructor for leaf
   public MerkleTree(String event){
    //h(i)=SHA256(0 x00|utf8(e_i));
    makeEventHashed(event);
   }

   //function contructor for internal node;
   public MerkleTree(MerkleTree right,MerkleTree left){}

   //function contructor for adding node;
   public MerkleTree(String event,int idEvent){

   }

   /*Hash function
   *@param single event;
   *return hashcode of this event
   */
   public String makeEventHashed(String event){
      try{
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          md.update(event.getBytes());
          hash = md.digest();
          StringBuilder sb = new StringBuilder(2 * hash.length);
          for(byte b: hash) {
            sb.append(String.format("%x", b&0xff) );
            //sb.append(String.format("%x", b&0xff) );
          }
          return sb.toString();
      } catch (Exception e) {
              e.printStackTrace();
      }
      return "";
   }

   /**
   * Adds two child subtrees to this Merkle Tree.
   */
   public void add(final MerkleTree leftTree, final MerkleTree rightTree)
   {
      this.leftTree = leftTree;
      this.rightTree = rightTree;

      // Calculate the message digest using the
      // specified digest algorithm and the
      // contents of the two child nodes
      md.update(leftTree.digest());
      digest = md.digest(rightTree.digest());
   }

   public int getValMax(){
      return this.begin;
   }
   public int getValMim(){
      return this.end;
   }
//test function;
   public void showEventMap(Map<Integer,String> eventMap){
      System.out.println("the size of this map is :"+eventMap.size());
   }
/*
   //main function take the input text file; compute its merkleTree; training each line as new event;
   public static void main(String[]args){
   String path="/Users/linfengwang/Desktop/II.3502_DistributedArchitecture/TP4/src/trace1.txt";
   MerkleTree aTree=new MerkleTree();
   }
*/
}
