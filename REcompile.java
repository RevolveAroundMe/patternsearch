

/**
 * REcompile
 */
public class REcompile {
    
    String p; //the regex to be compiled to an FSM
    int j=0;
    int state=1;
    char[] ch;
    int[] next1;
    int[] next2;

    public REcompile(String p) {
        this.p=p;
        parse();
    }


    public static void main(String[] args) {
        //String regex=args[0]; //When I can be fucked setting up launch.json
        String regex="a*";
        REcompile compiler=new REcompile(regex);
        System.out.println("State | char | next state? | next state?");
        for (int i = 0; i < compiler.ch.length; i++) {
            System.out.println(i+" | "+compiler.ch[i]+" | "+compiler.next1[i]+" | "+compiler.next2[i]);
        }
    }


    void parse(){
        int initial;
        initial= expression();
        if(p.isEmpty()) throw new Error("no regex");
        state=set_state(state, ' ', 0, 0);
    }

    int factor(){
        int start;

        if(Character.isLetterOrDigit(p.charAt(j))){
            j++;
            start=state;
            state++;
        }

        else{
            if(p.charAt(j)=='('){
                j++;
                start=expression();
                if (p.charAt(j)==')') {
                    j++;
                }
                else{
                    throw new Error("Symbol not ]");
                }
            }
            else{
                throw new Error();
            }
        }
        return start;
    }
    int term(){
        int start, t1,t2,f;
        f=state-1;
        start=t1=factor();
        
        if (p.charAt(j)=='*') {//closure
            set_state(state, ' ', state+1, state+2);
            j++;
            start=state;
            state++;
        }

        if (p.charAt(j)=='+') {
            if(next1[f]==next2[f]) next2[f]=state;
            next1[f]=state;
            f=state-1;
            j++;start=state;state++;
            
            t2=term();
            set_state(start,' ',t1,t2);
            if(next1[f]==next2[f]) next2[f]=state;
            next1[f]=state;
        }

        return start;
    }
    int expression(){
        int start;
        start=term();

        if (Character.isLetterOrDigit(p.charAt(j)) || p.charAt(j)==']') {
            expression();
        }

        return start;
    }
    int set_state(int s, char c, int n1, int n2){  
        ch[s]=c;next1[s]=n1;next2[s]=n2;
        return s;    
    }
}