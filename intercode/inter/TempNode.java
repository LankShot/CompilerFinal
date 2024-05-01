package intercode.inter;


import intercode.ast.*;
import intercode.lexer.*;
import intercode.visitor.*;

public class TempNode extends IdentifierNode {
    public static int num = 0;
    
    public TempNode(){
        
    }

    public static LabelNode newTemp(){

        num ++;
        return new LabelNode(new Word("t"+ num,Tag.ID), null);
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
