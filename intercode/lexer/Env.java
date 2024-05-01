package intercode.lexer;

import java.util.*;
import intercode.ast.*;

public class Env {

    private Hashtable table;
    protected Env prev;

    public Env(Env n) { table = new Hashtable(); prev = n; }

    public void put(Token w, IdentifierNode i) { table.put(w, i); }

    public IdentifierNode get(Token w) {
        for( Env e = this; e != null; e = e.prev ) {
            IdentifierNode found = (IdentifierNode)(e.table.get(w));
            if( found != null ) return found;
        }
        return null;
    }
}