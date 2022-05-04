
/**
 * REcompile
 */
public class REcompile {
    
    String p; //the regex to be compiled to an FSM
    int j=0;
    int state=1;
    char[] ch= new char[10000];
    int[] next1= new int[10000];
    int[] next2= new int[10000];
    char empty='\u2205';
    //special chars 

    public REcompile(String p) {
        this.p=p;
        parse();
    }


    public static void main(String[] args) {    
        String regex="a[]";
        if(args.length<1){
            System.out.println("Correct inputs eg. java REcompile \"a*ab\"");
            System.exit(1);
        }
        regex=args[0];
        REcompile compiler=new REcompile(regex);
        for (int i = 0; i < compiler.state+1; i++) {
            System.out.println(i+" "+compiler.ch[i]+" "+compiler.next1[i]+" "+compiler.next2[i]);
        }
    }


    void parse(){
        int initial;
        initial= expression();
        if(p.isEmpty()) throw new Error("no regex");
        state=set_state(state, '_', 0, 0);
        set_state(0, empty, 1, 1);
    }

    int factor(){
        int start = 0;

        if(!outOfBounds() && Character.isLetterOrDigit(p.charAt(j)) || p.charAt(j)=='.'){//saves literals as states
            set_state(state, p.charAt(j), state+1, state+1);
            j++;
            start=state;
            state++;
        }

        
        if(!outOfBounds() && p.charAt(j)=='('){
            j++;
            start=expression();
            if (!outOfBounds() && p.charAt(j)==')') {
                j++;
            }
            else{
                throw new Error("Improper parentheses");
            }
            
          
        }
        if (p.charAt(j) == '['){ 
            
            StringBuilder sb = new StringBuilder(); 
            
            j++; 
            
            if (p.charAt(j) == ']') {
                sb.append(p.charAt(j));
                j++;
            }
            
            if (outOfBounds()) throw new Error("Unfinished range");
            
            while (p.charAt(j) != ']'){
                sb.append(p.charAt(j));
                j++;
                if (outOfBounds()) throw new Error("Unfinished range");
            }
            
            j++; 
            
            int endState = (sb.length() * 2) - 1 + state;
            
            if (sb.length() == 1) {
                set_state(state++, sb.charAt(0), state, state);
            }
            else if (sb.length() == 2) {
                set_state(state++, empty, state, state + 1);
                set_state(state++, sb.charAt(0), endState, endState);
                set_state(state++, sb.charAt(1), endState, endState);
                set_state(state++, empty, state, state);
            }
            else {
                
                for (int i = 0; i < sb.length() - 2; i++) {
                    set_state(state++, empty, state, state + 1);
                    set_state(state++, sb.charAt(i), endState, endState);
                }
                
                set_state(state++, empty, state, state + 1);
                set_state(state++, sb.charAt(sb.length()-1), endState, endState);
                set_state(state++, sb.charAt(sb.length()-2), endState, endState);
                set_state(state++, empty, state, state);
            }
        }
        return start;
    }


    int term(){
        int start, t1,t2,f;
        f=state-1;
        start=t1=factor();

        if(!outOfBounds() && p.charAt(j)=='\\'){//Escape characters
            j++;
            if(!outOfBounds()){
                set_state(state++, p.charAt(j), state, state);
                j++;
                state++;
                term();
            }
        }

        if (!outOfBounds() && p.charAt(j) == '|') {
            return start;
        }

        if (!outOfBounds() && p.charAt(j)=='*') {//closure
            set_state(state, empty, state+1, t1);
            j++;
            start=state;
            state++;
        }
        if (!outOfBounds() && p.charAt(j)=='?') {
            f=state-1;
            set_state(state, ch[f], next1[f]+2, next1[f]+2);
            next2[f]=state;
            ch[f]=' ';
            state++;
            set_state(state, empty, state+1, state+1);
            next1[f]=state;
            j++;
            start=state;
            state++;
        }

        if (!outOfBounds() && p.charAt(j)=='+') {//Branch state 
            if(next1[f]==next2[f]) next2[f]=state;
            next1[f]=state;
            f=state-1;
            j++; start=state; state++;
            t2=term();
            set_state(start,empty,t1,t2);
            if(next1[f]==next2[f]) next2[f]=state;
            next1[f]=state;
        }

        return start;
    }
    int expression(){
        int start;
        start=term();
        if(p.charAt(j-1)=='(' && p.charAt(j)==')') return start;
        
        if (!outOfBounds() && p.charAt(j) == ('|')) {
            
            j++;
            set_state(state, empty, 0, 0);
            int pipe =	state++;
            int t = expression();
            set_state(pipe, empty, t, start);
            set_state(0, empty, pipe, pipe);
            int index = pipe - 4;

            if (index >= 0 && ch[j] == empty && next1[j]  == (index+1) && next2[j]  == index+2) {
                set_state(pipe-1, ch[j-1] ,state, state);
            }
            
            if (ch[pipe-1] != empty) set_state(pipe-1, ch[pipe-1], state, state);
            
            else set_state(pipe-1, ch[pipe - 1], next1[pipe-1], state);
            
            start = pipe;
            
        }

        if (!outOfBounds() && (Character.isLetterOrDigit(p.charAt(j)) || p.charAt(j)==')')) {
            expression();
        }

        return start;
    }
    int set_state(int s, char c, int n1, int n2){  
        ch[s]=c;next1[s]=n1;next2[s]=n2;
        return s;    
    }

    int set_state(int s, char c, int n2){  
        ch[s]=c;next2[s]=n2;
        return s;    
    }
    
    boolean outOfBounds(){
        if (j > p.length()-1) {
            return true;
        } else {
            return false;
        }
    }
    
}