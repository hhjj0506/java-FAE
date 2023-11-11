package edu.handong.csee.plt;

import edu.handong.csee.plt.ast.AST;
import edu.handong.csee.plt.ast.DefrdSub;
import edu.handong.csee.plt.ast.FAEVal;
import edu.handong.csee.plt.ast.MtSub;

public class Main {
	
	static boolean onlyParser = false; // for -p option
	
	public static void main(String[] args) {
		
		// This is just an example code. Use args to get -p option and actual code from CLI
		String exampleCode = "{+ {+ 2 {+ {+ 4 5} 4}} {+ 1 2}}";
		
		if(args.length == 1) {			// Only Result
			exampleCode = args[0];	
		}
		else if (args.length == 2 && args[0].equals("-p")) {		// Only Parser
			onlyParser = true;
		    exampleCode = args[1];	
		}
		else {
			System.out.println("Usage <-p>? <FAE>");
			System.exit(1);
		}

		// Parser
		Parser parser = new Parser();
		AST ast = parser.parse(exampleCode);
		
		if(ast == null)
			System.out.println("Syntax Error!");
		
		if(onlyParser) {	
			System.out.println(ast.getASTCode());	// Print Abstract Code
		}
		else {			
			Interpreter interpreter = new Interpreter();
			DefrdSub mtSub = new MtSub();
			
			FAEVal result = interpreter.interp(ast, mtSub);

			System.out.println(result.getASTCode());	// Print Result
		}
	}
}
