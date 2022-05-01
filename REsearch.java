/**
 * REsearch
 */
public class REsearch {
    public static void main(String[] args) {
        
    }
    public REsearch() {
    }
    /**
     * Deque
     */
    class Deque {
        Dnode head;
        Dnode tail;
        class Dnode{//Deque node constructor
             Dnode next;
             Dnode prev;
             String state;

        }

        Dnode pop(){
            if(isEmpty()) return null;
            Dnode temp=head; 
            head=head.next;
            head.prev=null;
            return temp;
        }


        void push(String state_){
            Dnode temp= new Dnode();
            temp.state=state_;

            if (head == null){
                head = tail = temp;
                temp.next = temp.prev = null;
            }

            else{
                head.prev = temp;
                temp.next = head;
                temp.prev = null;
                head = temp;
                }
            }


        void put(String state_){
            Dnode temp= new Dnode();
            temp.state=state_;

            if (head == null){
                head = tail = temp;
                temp.next = temp.prev = null;
            }

            else{
                tail.next = temp;
                temp.next = null;
                temp.prev = tail;
                tail = temp;
            }
    
        }
        
        
        boolean isEmpty(){
            if(head==null) return true;
            return false;
        }
        
    }
}