package intercode.ast ;

import intercode.visitor.* ;
import intercode.lexer.*;

public class BinExprNode extends ExprNode {

    public ExprNode left ;
    //public BinExprNode right;
    public ExprNode right ;
    public Token op;

    public BinExprNode () {

    }
    
    public BinExprNode (Token op) {
        this.op = op;
    }

    public BinExprNode (Token op, ExprNode left, ExprNode right){
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public BinExprNode (ExprNode left, BinExprNode right) {

        this.left  = left  ;
        this.right = right ;
    }
    public BinExprNode (BinExprNode n) {

        this.left  = n.left  ;
        this.right = n.right ;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
