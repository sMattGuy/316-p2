import java.io.*;

public class Parser{
	public static void main(String[] args) throws IOException, FileNotFoundException{
		//error if arguments arent 1
		if(args.length != 2){
			System.out.println("Input must be 2 file\nUsage java LexAnalyzer input.txt output");
			return;
		}
		//creation of file read
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		//creations of file output
		PrintStream o = new PrintStream(new File(args[1]));
		PrintStream console = System.out;
		System.setOut(o);
		//parse tree classes
		multipleClassDef parseStart;
		ClassDefEntry classInfo;
		//begin parse
		String token = LexAnalyzer.getToken(br);
		System.out.println(token);
		String[] token_split = token.split("|");
		if(token_split[0] == "class"){
			//begin next step of parse
			token = LexAnalyzer.getToken(br);
			token_split = token.split("|");
			if(token_split[1] == "id"){
				parseStart.classDef.className.id = token_split[0];
				//parse if either superclass or if start of inside;
				token = LexAnalyzer.getToken(br);
				token_split = token.split("|");
				//super class
				if(token_split[0] == ":"){
					token = LexAnalyzer.getToken(br);
					token_split = token.split("|");
					if(token_split[1] == "id"){
						//continue program
						parseStart.classDef.superClassName.id = token_split[0];
						classInfo.superClassName = token_split[0];
						token = LexAnalyzer.getToken(br);
						token_split = token.split("|");
					}
					else{
						System.out.println(token_split[0] + ' Syntax Error, class identifier name expected');
					}
				}
				//open bracket
				if(token_split[0] == "{"){
					token = LexAnalyzer.getToken(br);
					token_split = token.split("|");
					while(token_split[1] == "id"){
						classInfo.fields.add(token_split[0]);
						token = LexAnalyzer.getToken(br);
						token_split = token.split("|");
					}
				}
				//error
				else{
					System.out.println(token_split[0] + ' Syntax Error, "{" expected');
				}
			}
			else{
				//error if id not specified
				System.out.println(token_split[0] + ' Syntax Error, class identifier name expected');
				return;
			}
		}
		else{
			//error
			System.out.println(token_split[0] + ' : Syntax Error, unexpected symbol where "class" expected');
			return;
		}
	}
}

//class definitions
//information class
class ClassDefEntry // symbol table entry for a single ⟨class def⟩
{
	String superClassName; // value is "" if superclass is absent
	LinkedList<String> fields = new LinkedList<String>();
	HashMap<String, LinkedList<String>> funMap = new HashMap<String, LinkedList<String>>();
		// function names mapped to their parameters

	public String toString()
	{
		return "\nsuperclass=" + superClassName + "\nfields=" + fields.toString() + "\nfunctions=" + funMap.toString();
	}
}
//parse classes
class multipleClassDef{
	classDef classdef;
	multipleClassDef multiclassdef;
}
class classDef extends multipleClassDef{
	className classname;
	className superClassName;
	classBody classbody;
}
class className extends classDef{
	String id;
}
class classBody extends classDef{
	multiFieldVarList multifieldvarlist;
	multiFunDefList multifundeflist;
}
//field var list
class multiFieldVarList extends classBody{
	fieldVar fieldvar;
	multiFieldVarList multifieldvarlist;
}
class fieldVar extends multiFieldVarList{
	String id;
}
//function definition
class multiFunDefList extends classBody{
	funDef fundef;
	multiFunDefList multifundeflist;
}
class funDef extends multiFunDefList{
	header head;
	exp expression;
}
class header extends funDef{
	funName funname;
	multiParameterList multiparamlist;
}
class funName extends header{
	String id;
}
//parameter
class multiParameterList extends header{
	parameter param;
	multiParameterList multiparamlist;
}
class parameter extends multiParameterList{
	String id;
}
class exp extends funDef{
	String id;
	int integer;
	float decimal;
	funExp funexp;
}
class funExp extends exp{
	funCall funcall;
	binaryExp binexp;
	cond condition;
	not notType;
}
class funCall extends funExp{
	funName funname;
	multiExpList multiexplist;
}
//expresion 
class multiExpList extends funCall{
	exp expression;
	multiExpList multiexplist;
}
class binaryExp extends funExp{
	arithExp arithexp;
	boolExp boolexp;
	compExp compexp;
	dotExp dotexp;
}
class arithExp extends binaryExp{
	arithOp arithop;
	exp exp1;
	exp exp2;
}
class boolExp extends binaryExp{
	boolOp bollop;
	exp exp1;
	exp exp2;
}
class compExp extends binaryExp{
	compOp compop;
	exp exp1;
	exp exp2;
}
class dotExp extends binaryExp{
	exp exp1;
	exp exp2;
}
class cond extends funExp{
	exp exp1;
	exp exp2;
	exp exp3;
}
class not extends funExp{
	exp exp1;
}
class arithOp extends arithExp{
	String operator;
}
class boolOp extends boolExp{
	String operator;
}
class compOp extends compExp{
	String operator;
}