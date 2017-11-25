import java.util.*;
import java.security.MessageDigest;

public class TestMerkleTree{

  List<String> merkleTreeList;
  // Merkle Root
  String root;

  //constructor
  public TestMerkleTree(List<String> txList) {
    this.merkleTreeList = txList;
    root = "";
  }

  //execute merkle_tree and set root.
  public void merkle_tree() {

    List<String> tempList = new ArrayList<String>();

    for (int i = 0; i < this.merkleTreeList.size(); i++) {
      tempList.add(this.merkleTreeList.get(i));
    }

    List<String> newTxList = getNewTxList(tempList);
    while (newTxList.size() != 1) {
      newTxList = getNewTxList(newTxList);
    }

    this.root = newTxList.get(0);
  }

  /*
   *return Node Hash List(inside,it contains every hash value of every node.).
   *
  */
  private List<String> getNewTxList(List<String> inputList) {

    List<String> newTxList = new ArrayList<String>();
    int index = 0;
    while (index < inputList.size()) {
      // left child node
      String left = inputList.get(index);
      index++;

      // right child node
      String right = "";
      if (index != inputList.size()) {
        right = inputList.get(index);
      }

      // parent node hex value
      String shaValue = getSHAValue(left + right);
      newTxList.add(shaValue);
      index++;
    }
    return newTxList;
  }

  //Return hex string
  public String getSHAValue(String str) {
        byte[] byteTree;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byteTree = md.digest();
            StringBuilder sb = new StringBuilder(2 * byteTree.length);
            for(byte b: byteTree) {
              sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (Exception e) {
                e.printStackTrace();
        }

        return "";
  }

  //Get Root
  public String getRoot() {
    return this.root;
  }

  //Test TestMerkleTree;
  public static void main(String [] args) {
      List<String> testList = new ArrayList<String>();
      testList.add("a");
      testList.add("b");
      testList.add("c");
      testList.add("d");
      testList.add("e");

      TestMerkleTree merkleTree = new TestMerkleTree(testList);
      merkleTree.merkle_tree();
      System.out.println("root : " + merkleTree.getRoot());
    }

}
