import java.util.*;
import java.security.MessageDigest;

public class MerkleTree{

  List<String> txList;
  // Merkle Root
  String root;

  //constructor
  public MerkleTree(List<String> txList) {
    this.txList = txList;
    root = "";
  }

  //execute merkle_tree and set root.
  public void merkle_tree() {

    List<String> tempTxList = new ArrayList<String>();

    for (int i = 0; i < this.txList.size(); i++) {
      tempTxList.add(this.txList.get(i));
    }

    List<String> newTxList = getNewTxList(tempTxList);
    while (newTxList.size() != 1) {
      newTxList = getNewTxList(newTxList);
    }

    this.root = newTxList.get(0);
  }

  //return Node Hash List.
  private List<String> getNewTxList(List<String> tempTxList) {

    List<String> newTxList = new ArrayList<String>();
    int index = 0;
    while (index < tempTxList.size()) {
      // left child node
      String left = tempTxList.get(index);
      index++;

      // right child node
      String right = "";
      if (index != tempTxList.size()) {
        right = tempTxList.get(index);
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

  //Test MerkleTree;
  public static void main(String [] args) {
      List<String> tempTxList = new ArrayList<String>();
      tempTxList.add("a");
      tempTxList.add("b");
      tempTxList.add("c");
      tempTxList.add("d");
      tempTxList.add("e");

      MerkleTree merkleTree = new MerkleTree(tempTxList);
      merkleTree.merkle_tree();
      System.out.println("root : " + merkleTree.getRoot());
    }

}
