package intercode.ast;

import intercode.visitor.*;
import intercode.lexer.*;

public class TrueNode extends ExprNode{

    
    public TrueNode(){
        type = Type.Bool;
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
