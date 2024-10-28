package symbol;

public class Symbol {
    public int id;		// 当前单词对应的id。
    public int tableId; 	// 当前单词所在的符号表编号。
    public String token; 	// 当前单词所对应的字符串。
    public int type; 		// 0 -> var, 1 -> array, 2 -> func
    public int btype; 	// 0 -> int, 1 -> char
    public int con;		// 0 -> const, 1 -> var
    // 数组的维数：a[dim1] dim1
    // 变量的值：val，寄存器：reg
    // func：
    // 	retype		// 0 -> void, 1 -> int, 2 -> char
    //	paramNum	// 参数数量
    // 	paramTypeList	// 参数类型
}
