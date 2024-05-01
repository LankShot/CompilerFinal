package intercode.ast ;

import intercode.lexer.*;
import intercode.visitor.* ;

public class RealNode extends ExprNode {

    public float value ;
    public Real v;

    public RealNode () {

    }
    
    public RealNode (Real v) {

        this.value = v.value ;
        this.v = v;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
     public void printNode () {

        System.out.println("RealNode: " + value) ;
    }
}
