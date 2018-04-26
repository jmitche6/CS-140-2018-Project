package project;

public class MachineModel {
	
	public class CPU {
		private int accumulator;
		private int instructionPointer;
		private int memoryBase;
		
		public void incrementIP(int val) {
			instructionPointer += val;
		}
		
		public void incrementIP() {
			instructionPointer += 1;
		}
		
		
	}

}
