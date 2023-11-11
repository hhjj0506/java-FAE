package edu.handong.csee.plt;

import edu.handong.csee.plt.ast.ASub;
import edu.handong.csee.plt.ast.DefrdSub;
import edu.handong.csee.plt.ast.FAEVal;
import edu.handong.csee.plt.ast.MtSub;

public class Lookup {
    public static FAEVal lookup(String name, DefrdSub ds) {		
		
		if(ds instanceof MtSub) {		// For Free identifier
			System.out.println("Free identifier");	
			System.exit(1);
		}	
		
		if(ds instanceof ASub) {		// Lookup DS recursively
			ASub asub = (ASub)ds;			
			if(asub.getName().equals(name)) {
				Interpreter interp = new Interpreter();
				return interp.strict(asub.getValue());
			}
			else {
				return lookup(name, asub.getDS());
			}
		}
		
		return null;
	}
}
