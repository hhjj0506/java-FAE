package edu.handong.csee.plt.ast;

public class FAEVal extends AST {
    public String getASTCode(){
		String astCode="";
		
		if(this instanceof NumV) {
			astCode = ((NumV)this).getASTCode();
		}
		
		if(this instanceof ClosureV) {
			astCode = ((ClosureV)this).getASTCode();	
		}
				
		return astCode;
	}
}
