import java.io.*;
import java.util.ArrayList;
/**
 * REsearch
 */
public class REsearch {
    ArrayList<Character> ch= new ArrayList<Character>();
    ArrayList<Integer> next1=new ArrayList<Integer>();
    ArrayList<Integer> next2=new ArrayList<Integer>();
    public static void main(String[] args) {
        REsearch RES=new REsearch();  
        String line;
        File file=new File("testing std input.txt");
        
        try {
            FileWriter fWriter=new FileWriter(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (br!=null) {
                line=br.readLine();
                fWriter.append(line+'\n');
                System.err.println("looping");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        
        Dnode pop(){//Removes first item in que and returns it
            if(isEmpty()) return null;
            Dnode temp=head; 
            head=head.next;
            head.prev=null;
            return temp;
        }


        void push(String state_){//Puts a new node at the front of the que
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


        void put(String state_){//Puts a new node at the back of the que
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
        
        
        boolean isEmpty(){//Checks if the Deque is empty
            if(head==null) return true;
            return false;
        }
        
    }
}