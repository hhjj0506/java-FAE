package edu.handong.csee.plt;

import edu.handong.csee.plt.ast.AST;
import edu.handong.csee.plt.ast.DefrdSub;
import edu.handong.csee.plt.ast.FAEVal;
import edu.handong.csee.plt.ast.MtSub;

public class Main {
	
	static boolean onlyParser = false;
	
	public static void main(String[] args) {
		
		String exampleCode = "";
		
		if(args.length == 1) {		
			exampleCode = args[0];	
		}
		else if (args.length == 2 && args[0].equals("-p")) {	
			onlyParser = true;
		    exampleCode = args[1];	
		}
		else {
			System.out.println("Usage : java -cp [class_path_settings] [your_class_name_that_has_main_method] [-p]? [FAE]");
			System.exit(1);
		}

		Parser parser = new Parser();
		AST ast = parser.parse(exampleCode);
		
		if(ast == null) {
			System.out.println("Syntax is incorrect");
		}
		
		if(onlyParser) {	
			System.out.println(ast.getASTCode());
		} else {			
			Interpreter interpreter = new Interpreter();
			DefrdSub mtSub = new MtSub();
			
			FAEVal result = interpreter.interp(ast, mtSub);

			System.out.println(result.getASTCode());
		}
	}
}
