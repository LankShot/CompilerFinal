package intercode.ast;

import intercode.visitor.*;
import intercode.inter.*;


public class BreakStatementNode extends StatementNode{
    public LabelNode stopLabel;
    public BreakStatementNode(){

    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
