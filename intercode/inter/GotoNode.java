package intercode.inter;

import intercode.ast.*;
import intercode.visitor.*;

public class GotoNode extends IdentifierNode{

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
