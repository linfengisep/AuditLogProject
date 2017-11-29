import java.util.Scanner;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;

public class AuditLogServer {
   long treeDepth=0;
   MerkleTree currentNode=null;
   MerkleTree root=null;
   int idLastLeaf=0;
   int odd=0xFFFFFFFF;
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

   public void buildTree(Queue<MerkleTree> merkleTree){
      while(!merkleTree.isEmpty()){
      MerkleTree newNode=merkleTree.poll();//remove one element out;
      getTreeDepth();
      addLeaf(currentNode,newNode,null);
      idLastLeaf++;
      //if(idLastLeaf)root=newNode;
      }
   }

   public void addLeaf(MerkleTree currentNode, MerkleTree newLeaf,MerkleTree parent){
      if(currentNode==null){
      currentNode=newLeaf;
      idLastLeaf = 1;
      treeDepth = 1;
      }
      break;

      MerkleNode newNode;
      //binary operations to retrieve the last bit, if it is 1, then the idLastLeaf is odd;
      int impair= odd & idLastLeaf;
      if(impair == 1){
            //build a parent node, and define the parent's left and right node at the same time;
            newNode = new MerkleNode(currentNode, newLeaf);
            newNode.parent=currentNode.parent//maybe ??
            //reach the bottom;
            if(treeDepth<getCurrentTreeCapacity() && treeDepth >getCurrentTreeCapacity()-2){
              this.currentNode = root;
            }else{//not reach bottom
                  addLeaf(currentNode.setParentToLeftChild(), newLeaf, newNode);
                 }
                  //even,and full tree,then go up
            }else if(idLastLeaf==getCurrentTreeCapacity()){
            // System.out.println("On monte");
            newNode = new MerkleNode(currentNode, newLeaf);
            root=newNode;
            }else {
            //even, but not full tree;
            }
      }
      public void updateTree(){

      }

      // a call back to calculate the tree depth;
      private final Thread getTreeDepth = new Thread(() -> {
         int n=1;
         int time=1;
         while(n< idLastLeaf){
            n * =2;
            time++;
         }
         treeDepth=time;
      });

      public int getCurrentTreeCapacity(){
         int capacity=1;
         for(int i=1;i<treeDepth-1;i++){
            capacity*=2;
         }
         return capacity;
      }

}
