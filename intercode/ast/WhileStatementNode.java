package intercode.ast ;

import intercode.visitor.* ;
import java.util.*;
import intercode.inter.*;

public class WhileStatementNode extends StatementNode {

    public ParenthesesNode cond;
    public StatementNode stmt;

    public List<StatementNode> assigns = new ArrayList<StatementNode>();
    
    public LabelNode startLabel;
    public LabelNode stopLabel;

    public WhileStatementNode () {
        
    }
    public WhileStatementNode (ParenthesesNode cond, StatementNode stmt) {

        this.cond  = cond  ;
        this.stmt = stmt ;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
