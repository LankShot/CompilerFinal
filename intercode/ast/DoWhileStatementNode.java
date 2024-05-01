package intercode.ast ;

import intercode.visitor.* ;
import intercode.inter.*;
import java.util.*;

public class DoWhileStatementNode extends StatementNode {

    public StatementNode stmt;
    public ParenthesesNode cond;

    public List<StatementNode> assigns = new ArrayList<StatementNode>();
    
    public LabelNode startLabel;
    public LabelNode stopLabel;

    public DoWhileStatementNode () {
        
    }
    public DoWhileStatementNode (StatementNode stmt, ParenthesesNode cond ) {

        this.cond  = cond  ;
        this.stmt = stmt ;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
