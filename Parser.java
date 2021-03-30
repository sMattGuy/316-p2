import java.io.*;

public class Parser{
	
}

//class definitions
abstract class classDefList{
	
}
class classDef extends classDefList{
	className classname;
	classBody classbody;
}
class multipleClassDef extends classDefList{
	classDef classdef;
	classDefList classdeflist;
}
class className extends classDef{
	String id;
}
class classBody extends classDef{
	
}
//field var list
abstract class fieldVarList extends classBody{
	
}
class fieldVar extends fieldVarList{
	
}
//function definition
abstract class funDefList extends fieldVarList{
	
}
class funDef extends funDefList{
	
}
class header extends funDef{
	
}
class funName extends header{
	String id;
}
//parameter
abstract class parameterList extends header{
	
}
class parameter extends parameterList{
	
}
class exp extends funDef{
	
}
class funExp extends exp{
	
}
class funCall extends funExp{
	
}
//expresion 
abstract class expList extends funCall{
	
}
class binaryExp extends funExp{
	
}
class arithExp extends binaryExp{
	
}
class boolExp extends binaryExp{
	
}
class compExp extends binaryExp{
	
}
class dotExp extends binaryExp{
	
}
class cond extends funExp{
	
}
class not extends funExp{
	
}
class arithOp extends arithExp{
	
}
class boolOp extends boolExp{
	
}
class compOp extends compExp{
	
}