package intercode.ast ;
import java.util.*;

import intercode.inter.*;
import intercode.visitor.* ;

public class IfStatementNode extends StatementNode {

    public ParenthesesNode cond;
    public StatementNode stmt;
    public StatementNode else_stmt;

    public List<StatementNode> assigns = new ArrayList<StatementNode>();
    
    public LabelNode falseLabel;

    public IfStatementNode () {
        
    }
    
    public IfStatementNode (ParenthesesNode cond, StatementNode stmt, StatementNode else_stmt) {

        this.cond  = cond  ;
        this.stmt = stmt ;
        this.else_stmt = else_stmt;
    }

    public void accept(ASTVisitor v) {

        v.visit(this);
    }
}
