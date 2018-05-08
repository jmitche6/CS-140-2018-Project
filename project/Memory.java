package project;

public class Memory {
	public static int DATA_SIZE = 2048;
	private int[] data = new int[DATA_SIZE];
	public static final int CODE_MAX = 2048;
	private int[] code = new int[CODE_MAX];
	private int changedIndex = -1;
	
	
	public int getData (int index) {
		return data[index];
	}
	public int[] getCode() {
		return code;
	}
	public int[] getData() {
		return data;
	}
	public int getOp(int i) {
		return code[2*i];
	}
	public int getArg(int i) {
		return code[2*i + 1];
	}
	public int getChangedIndex() {
		return changedIndex;
	}
	public String getHex(int i) {
		return "" + Integer.toHexString(code[2*i]).toUpperCase() + " " + Integer.toHexString(code[2*i+1]).toUpperCase();
	}
	public String getDecimal(int i) {
		return "" + InstrMap.toMnemonic.get(code[2*i]) + " " + code[2*i+1];
	}
	public void clearCode(int start, int end) {
		for(int i = start; i < end; i++) {
			code[2*i]=0;
			code[2*i+1]=0;
		}
	}
	public void clearData(int start, int end) {
		changedIndex = -1;
		for(int i = start; i < end; i++) {
			data[i] = 0;
		}
	}
	public void setCode(int index, int op, int arg) {
		code[2*index] = op;
		code[2*index+1] = arg;
	}
	public void setData(int index, int value) {
		//changedIndex = index;
		data[index] = value;
	}

}
