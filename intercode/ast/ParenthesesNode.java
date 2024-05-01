package intercode.ast ;

import intercode.visitor.* ;

public class ParenthesesNode extends ExprNode {

    public ExprNode expr;
    public boolean isTrueNode = false;
    public ParenthesesNode () {
        
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
