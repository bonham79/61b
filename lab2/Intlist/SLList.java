public class SLList {

   private class IntNode {
   public int item;
   public IntNode next;
 
   public IntNode(int i, IntNode n) {
         item = i;
         next = n;
   }
}

   private IntNode first;
 
   public SLList(int x) {
      first = new IntNode(x, null);
   }
 
   public void addFirst(int x) {
      first = new IntNode(x, first);
   }
 
   public int getFirst() {
        return first.item;
   }
}

