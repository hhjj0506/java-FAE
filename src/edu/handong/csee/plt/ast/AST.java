package edu.handong.csee.plt.ast;

public class AST {
	
	public String getASTCode() {
		String astCode = "";
		
		if(this instanceof Num)
			astCode = ((Num)this).getASTCode();
		
		if(this instanceof Add)
			astCode = ((Add)this).getASTCode();
		
		if(this instanceof Sub)
			astCode = ((Sub)this).getASTCode();
		
		if(this instanceof Symbol)
			astCode = ((Symbol)this).getASTCode();

		if(this instanceof Fun)
			astCode = ((Fun)this).getASTCode();
		
		if(this instanceof App)
			astCode = ((App)this).getASTCode();
		
		return astCode;
	}
}

