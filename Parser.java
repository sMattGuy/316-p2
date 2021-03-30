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
