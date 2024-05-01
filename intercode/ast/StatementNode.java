package intercode.ast ;

import intercode.visitor.ASTVisitor;

public class StatementNode extends Node{

    public StatementNode () {
        
    }
    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
