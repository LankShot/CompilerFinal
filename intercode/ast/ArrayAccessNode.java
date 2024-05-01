package intercode.ast;

import intercode.visitor.* ;
import intercode.inter.*;

public class ArrayAccessNode extends ExprNode {
    public IdentifierNode id;
    public ExprNode index;

    public ArrayAccessNode (){

    }

    public ArrayAccessNode(IdentifierNode id, ExprNode index){

        this.id = id;
        this.index = index;
    }
    public ArrayAccessNode(IdentifierNode id, ExprNode index, int lineNum){

        this.id = id;
        this.index = index;
        this.lineNum = lineNum;
    }

    public void accept(ASTVisitor v){

        v.visit(this);
    }
}
