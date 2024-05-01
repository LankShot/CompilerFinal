package intercode.ast ;

import intercode.visitor.* ;

import java.util.*;

import intercode.lexer.*;


public class BlockStatementNode extends StatementNode {

    public List<DeclarationNode> decls = new ArrayList<DeclarationNode>();
    public List<StatementNode> stmts = new ArrayList<StatementNode>();

    public List<StatementNode> assigns = new ArrayList<StatementNode>();

    public BlockStatementNode parent;

    public Env sTable;

    public BlockStatementNode (BlockStatementNode parent) {
        this.decls = new ArrayList<DeclarationNode>();
        this.stmts = new ArrayList<StatementNode>();
        
    }

    public BlockStatementNode (List<DeclarationNode> decls, List<StatementNode> stmts, BlockStatementNode parent){
        this.stmts = stmts;
        this.decls = decls;
        this.parent = parent;
    }

    public void accept(ASTVisitor v){
        v.visit(this);
    }
}
