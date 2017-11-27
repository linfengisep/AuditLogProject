import java.util.Scanner;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;

public class AuditLogServer {
   long treeDepth=-1;
   MerkleTree currentNodeOfMainTree;
   int idLastLeaf=0;

   //Constructor 1 AuditLogServer;
  public AuditLogServer(String inputFilePath) {
    Queue<MerkleTree> merkleQueue = new LinkedList<MerkleTree>();

    File file=new File(inputFilePath);
    FileInputStream fis=null;
    BufferedInputStream fileBS=null;
    DataInputStream dis=null;

    try {
      fis=new FileInputStream(file);
      fileBS=new BufferedInputStream(fis);
      dis=new DataInputStream(fileBS);
      int eventId=0;
      while(dis.available()!=0){
            String line=dis.readLine();
            MerkleTree merkle = new MerkleTree(line,eventId);
               try {
                     merkleQueue.add(merkle);
                   } catch(Exception e) {
                     System.out.println("No more space available.");
                   }
                   eventId++;
            }
            fis.close();
            fileBS.close();
            dis.close();
        }catch(FileNotFoundException e){
        e.printStackTrace();
   }catch(IOException e){
   e.printStackTrace();
   }
    buildTree(merkleQueue);
  }

   public MerkleTree buildTree(Queue<MerkleTree> merkleTree){
      while(!merkleTree.isEmpty()){
      MerkleTree newNode=merkleTree.poll();
      addLeaf(currentNodeOfMainTree,newNode,null);
      idLastLeaf++;
      }
      return currentNodeOfMainTree;
   }

   public void addLeaf(MerkleTree currentNodeOfMainTree, MerkleTree newNode,MerkleTree parent){
      if(currentNodeOfMainTree==null){
      currentNodeOfMainTree=newNode;
      idLastLeaf = 1;
      treeDepth = 1;
      }
      break;

      MerkleNode newNode;
      int nbEventsOfMainTree = 1 + currentNodeOfMainTree.getValMax() - currentNodeOfMainTree.getValMin();
      while (nbEventsOfMainTree != 1 && nbEventsOfMainTree%2 == 0) {
             nbEventsOfMainTree /= 2;
      }
      // SI 1 : plein
      // SINON A COMPLETER

      if(nbEventsOfMainTree == 1) {
         // System.out.println("On set");
         // Notre arbre est plein
         newNode = new MerkleNode(currentNodeOfMainTree, newLeaf);
         if (parent != null) {
              parent.setFilsDroit(newNode);
         }
         else {
              // System.out.println("Nouvelle currentNode");
              this.currentNode = newNode;
              this.treeLevel += 1;
         }
         return;
      }
      else {
         // System.out.println("On descend");
         addLeaf(currentNodeOfMainTree.getFilsDroit(), newLeaf, currentNodeOfMainTree);
         currentNodeOfMainTree.updateNode();
      }
   }

   public void updateTree(){

   }
}
