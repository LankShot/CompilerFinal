package intercode.lexer ;

import java.io.* ;
import java.util.* ;

public class Lexer {

    public int line = 1 ;
    private char peek = ' ' ;
    private Hashtable<String, Word> words = new Hashtable<String, Word>() ;
    //private Hashtable words = new Hashtable() ;

    
    private FileInputStream in;
    private BufferedInputStream bin;

    public Lexer () {

        reserve (new Word("if", Tag.IF));
        reserve (new Word("else", Tag.ELSE));
        reserve (new Word("while", Tag.WHILE));
        reserve (new Word("do", Tag.DO));
        reserve (new Word("break", Tag.BREAK));


        reserve(Word.False) ;
        reserve(Word.True) ;

        reserve(Word.eof);

        reserve(Type.Int);
        reserve(Type.Char);
        reserve(Type.Bool);
        reserve(Type.Float);

        setupIOStream();
    }

    void reserve (Word w) {

        words.put(w.lexeme, w) ;
    }

    void setupIOStream(){
        try{
            in = new FileInputStream("intercode/input.txt");
            bin = new BufferedInputStream(in);
        }
        catch(IOException e){
            
            System.out.println("IOException");
        }
    }

    void readch() throws IOException { 
        peek = (char)bin.read() ; 
        
    }

    boolean readch(char c) throws IOException { 
        readch();

        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {

        //System.out.println("scan() in Lexer") ;

        for ( ; ; readch()) {

            if (peek == ' ' || peek == '\t') 
                continue ;
            else if (peek == '\n'|| peek==(char)13){
                line = line + 1 ;
            } 
                
            else 
                break ;
        }
        
        switch(peek){

            case '&':
                if(readch('&'))
                    return Word.and;
                else return new Token('&');
            case '|':
                if(readch('|'))
                    return Word.or;
                else return new Token('|');
            case '=':
                if(readch('='))
                    return Word.eq;
                else return new Token('=');
            case '!':
                if(readch('='))
                    return Word.ne;
                else return new Token('!');
            case '<':
                if(readch('='))
                    return Word.le;
                else return new Token('<');
            case '>':
                if(readch('='))
                    return Word.ge;
                else return new Token('>');
        }

        if (Character.isDigit(peek)) {

            int v = 0 ;

            do {

                v = 10 * v + Character.digit(peek, 10) ;
                readch() ;

            } while (Character.isDigit(peek)) ;

            //System.out.println("v: " + v) ;
            if (peek != '.')
                return new Num(v);
            
            float x = v; float d = 10;

            for(;;){
                readch();

                if(! Character.isDigit(peek)) break;

                x = x+ Character.digit(peek, 10) / d; d = d*10;
            }
            return new Real(x);
        }

        if (Character.isLetter(peek)) {

            StringBuffer b = new StringBuffer() ;

            do {

                b.append(peek) ;
                readch();

            } while (Character.isLetterOrDigit(peek)) ;

            String s = b.toString() ;
            //System.out.println("s: " + s) ;
            Word w = (Word) words.get(s) ;

            if (w != null)
                return w ;
            
            w = new Word(s, Tag.ID) ;
            words.put(s, w) ;

            //System.out.println("w: " + w.toString()) ;

            return w ;
        }

        if((int)peek == 65535){

            System.out.println("@@@@@@@@@ EOF reached...");

            return Word.eof;
        }

        Token t = new Token(peek) ; 
        //System.out.println("t: " + t.toString()) ;
        peek = ' ' ;

        return t ;
    }
}
