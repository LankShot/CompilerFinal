package intercode.ast ;

import intercode.visitor.* ;

public class AssignmentNode extends StatementNode {

    public ExprNode  left  ; //will have to change to support arrays
    //public ExpressionNode right ;
    public ExprNode right ;
    public boolean inAssigns = false;

    public AssignmentNode () {
        
    }
    public AssignmentNode (ExprNode left, ExprNode right) {

        this.left  = left  ;
        this.right = right ;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
