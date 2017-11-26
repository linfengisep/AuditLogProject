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
   public List<String> eventList=new ArrayList<String>();


   //function contructor for leaf
   public MerkleTree(String event){
   if (currentTree.leftSubTree==null && currentTree.rightSubTree==null)
   isLeaf = true;
    //h(i)=SHA256(0 x00|utf8(e_i));
    makeEventHashed(event);
   }

   //function contructor for internal node;
   public MerkleTree(MerkleTree right,MerkleTree left){
      if (currentTree.leftSubTree=null || currentTree.rightSubTree !=null)
      {isLeaf = false;
      this.hash = this.leftSubTree.hash+this.rightSubTree.hash;
      }
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
/*
          System.out.println("the byteTree[] length is:"+byteTree.length);
          for(byte b:byteTree){
            System.out.println("byte is:"+b);
          }
*/
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
   /*import file function
   *@param file path in my ordi;
   */
   public void readFile(String path){
      File file=new File(path);
      FileInputStream fis=null;
      BufferedInputStream fileBS=null;
      DataInputStream dis=null;

      try{
         fis=new FileInputStream(file);
         fileBS=new BufferedInputStream(fis);
         dis=new DataInputStream(fileBS);
            while(dis.available() !=0){
                 //System.out.println("we r here:"+dis.readLine());
                  list.add(dis.readLine());
                 }
                     fis.close();
                     fileBS.close();
                     dis.close();
            }catch(FileNotFoundException e){
            e.printStackTrace();
         }catch(IOException e){
         e.printStackTrace();
         }
   }

   /**
   * Adds two child subtrees to this Merkle Tree.
   */
/*
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
*/

//test function;
   public void showListContent(List<String> list){
   for(int i=0;i<eventList.size();i++)
         System.out.println("Voilà les hashs des évènements:"+eventList.get(i).toString()+": "+makeEventHashed(eventList.get(i)));
   }

   //main function take the input text file; compute its merkleTree; training each line as new event;
   public static void main(String[]args){
   String path="/Users/linfengwang/Desktop/II.3502_DistributedArchitecture/TP4/src/traces/DS1-trace.txt";
   MerkleTree aTree=new MerkleTree();
   aTree.readFile(path);
   aTree.showListContent(aTree.eventList);
   }
}
