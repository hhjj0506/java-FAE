package edu.handong.csee.plt;

import java.util.ArrayList;
import java.util.Stack;

import edu.handong.csee.plt.ast.AST;
import edu.handong.csee.plt.ast.Add;
import edu.handong.csee.plt.ast.App;
import edu.handong.csee.plt.ast.Fun;
import edu.handong.csee.plt.ast.Num;
import edu.handong.csee.plt.ast.Sub;
import edu.handong.csee.plt.ast.Symbol;

public class Parser {

	AST parse(String exampleCode) {
		ArrayList<String> subExpressions = splitExpressionAsSubExpressions(exampleCode);

		if(subExpressions.size() == 1 && isNumeric(subExpressions.get(0))) {
			return new Num(subExpressions.get(0));
		}

		if(subExpressions.get(0).equals("+")) {	
			return new Add(parse(subExpressions.get(1)),parse(subExpressions.get(2)));
		}

		if(subExpressions.get(0).equals("-")) {	
			return new Sub(parse(subExpressions.get(1)),parse(subExpressions.get(2)));
		}

		if(subExpressions.size() == 1 && !subExpressions.get(0).equals("with")) {
			return new Symbol(subExpressions.get(0));
		}

		if(subExpressions.get(0).equals("with")) {
			String[] divided = subExpressions.get(1).split(" ", 2);
			String idtf = divided[0].substring(1);
			String value = divided[1].substring(0, divided[1].length()-1);

			Fun fun = new Fun(idtf, parse(subExpressions.get(2)));			
			return new App(fun, parse(value));
		}

		if(subExpressions.get(0).equals("fun")) {		
			String param = subExpressions.get(1).substring(1,subExpressions.get(1).length()-1);
			return new Fun(param, parse(subExpressions.get(2)));
		}	
		
		if(subExpressions.get(0).startsWith("{") || subExpressions.size() == 2) { 
			return new App(parse(subExpressions.get(0)),parse(subExpressions.get(1)));
		}	
		
		return null;
	}

	private ArrayList<String> splitExpressionAsSubExpressions(String exampleCode) {

		if ((exampleCode.startsWith("{") && !exampleCode.endsWith("}"))
				|| (!exampleCode.startsWith("{") && exampleCode.endsWith("}"))) {
			System.out.println("Syntax error");
			System.exit(0);
		}

		if (exampleCode.startsWith("{"))
			exampleCode = exampleCode.substring(1, exampleCode.length() - 1);

		return getSubExpressions(exampleCode);
	}

	/**
	 * This method return a list of sub-expression from the given expression. For
	 * example, {+ 3 {+ 3 4} -> +, 2, {+ 3 4} TODO JC was sleepy while implementing
	 * this method...it has complex logic and might be buggy... You can do better or
	 * find an external library.
	 * 
	 * @param exampleCode
	 * @return list of sub expressions
	 */
	private ArrayList<String> getSubExpressions(String code) {

		ArrayList<String> subExpressions = new ArrayList<>();
		Stack<Character> stack = new Stack<>();

		String strBuffer = "";

		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == ' ' && stack.isEmpty()) {
				if (!strBuffer.isEmpty()) {
					subExpressions.add(strBuffer.trim());
					strBuffer = "";
				}
			} else {
				if (code.charAt(i) == '{') {
					stack.add('{');
				} else if (code.charAt(i) == '}' && !stack.isEmpty()) {
					stack.pop();
				}
			}

			strBuffer += code.charAt(i);
		}

		subExpressions.add(strBuffer.trim());

		return subExpressions;

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

}
