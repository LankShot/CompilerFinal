package intercode;
import intercode.inter.*;
import intercode.lexer.*;
import intercode.parser.*;
import intercode.unparser.*;
import intercode.typechecker.*;
    
public class Main {

    public static void main (String[] args) {

        Lexer lexer = new Lexer() ;
        Parser parser = new Parser(lexer) ;
        TreePrinter tree = new TreePrinter(parser);
        TypeChecker checker = new TypeChecker(parser);
        InterCode inter = new InterCode(checker);
        Unparser pretty = new Unparser(inter) ;
        
        
    }
}
