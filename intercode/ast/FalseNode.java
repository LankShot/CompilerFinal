package intercode.ast;

import intercode.lexer.Type;
import intercode.visitor.*;


public class FalseNode extends ExprNode{

    public FalseNode(){
        type = Type.Bool;
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
