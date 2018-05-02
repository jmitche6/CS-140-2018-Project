package project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {
	public static final Map<Integer, Instruction> INSTRUCTIONS = new TreeMap<>();
	private CPU cpu = new CPU();
	private Memory mem = new Memory();
	private HaltCallBack callback;
	private boolean withGUI;
	
	public MachineModel() {
		this(false, null);
	}
	
	public MachineModel(boolean GUI, HaltCallBack HCF) {
		this.withGUI = GUI;
		this.callback = HCF;
		
		INSTRUCTIONS.put(0x0, arg -> {
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x1, arg -> {
			cpu.accumulator = arg;
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x2, arg -> {
			int arg1 = mem.getData(cpu.memoryBase + arg);
			cpu.accumulator = arg1;
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x3, arg -> {
			int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
            cpu.accumulator = arg2;
            cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x4, arg -> {
			mem.setData(cpu.memoryBase+arg, cpu.accumulator);
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x5, arg -> {
			int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
			mem.setData(arg2, arg1);
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x6, arg -> {
			cpu.instructionPointer += arg;
		});
		
		INSTRUCTIONS.put(0x7, arg -> {
			cpu.instructionPointer +=(cpu.memoryBase + arg);
		});
		
		INSTRUCTIONS.put(0x8, arg -> {
			cpu.instructionPointer = arg;
		});
		
		INSTRUCTIONS.put(0x9, arg -> {
			if (cpu.accumulator == 0) {
				cpu.instructionPointer += arg;
			} else {
				cpu.incrementIP();
			}
		});
		
		INSTRUCTIONS.put(0xA, arg -> {
			if (cpu.accumulator == 0) {
				cpu.instructionPointer +=(cpu.memoryBase + arg);
			} else {
				cpu.incrementIP();
			}
		});
		
		INSTRUCTIONS.put(0xB, arg -> {
			if (cpu.accumulator == 0) {
				cpu.instructionPointer = arg;
			} else {
				cpu.incrementIP();
			}
		});
		
		INSTRUCTIONS.put(0xC, arg -> {
            cpu.accumulator += arg;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0xD, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            cpu.accumulator += arg1;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0xE, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
            cpu.accumulator += arg2;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0xF, arg -> {
            cpu.accumulator -= arg;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x10, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            cpu.accumulator -= arg1;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x11, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
            cpu.accumulator -= arg2;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x12, arg -> {
            cpu.accumulator *= arg;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x13, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            cpu.accumulator *= arg1;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x14, arg -> {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
            cpu.accumulator *= arg2;
            cpu.incrementIP();
        });
		
		INSTRUCTIONS.put(0x15, arg -> {
            if (arg == 0) {
            	throw new DivideByZeroException("Cannot Divide By 0!");
            } else {
			cpu.accumulator /= arg;
            cpu.incrementIP();
            }
        });
		
		INSTRUCTIONS.put(0x16, arg -> {
			if (arg == 0) {
            	throw new DivideByZeroException("Cannot Divide By 0!");
            } else {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            cpu.accumulator /= arg1;
            cpu.incrementIP();
            }
        });
		
		INSTRUCTIONS.put(0x17, arg -> {
			if (arg == 0) {
            	throw new DivideByZeroException("Cannot Divide By 0!");
            } else {
            int arg1 = mem.getData(cpu.memoryBase+arg);
            int arg2 = mem.getData(cpu.memoryBase+arg1);
            cpu.accumulator /= arg2;
            cpu.incrementIP();
            }
        });
		
		INSTRUCTIONS.put(0x18, arg -> {
			if (cpu.accumulator != 0 && arg != 0) {
				cpu.accumulator = 1;
			} else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x19, arg -> {
			int arg1 = mem.getData(cpu.memoryBase+arg);
			if (cpu.accumulator != 0 && arg1 != 0) {
				cpu.accumulator = 1;
			} else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x1A, arg -> {
			if (cpu.accumulator != 0) {
				cpu.accumulator = 1;
			} else if (cpu.accumulator == 0) {
				cpu.accumulator = 0;
			}
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x1B, arg -> {
			int arg1 = mem.getData(cpu.memoryBase+arg);
			if (arg1 < 0) {
				cpu.accumulator = 1;
			} else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x1C, arg -> {
			int arg1 = mem.getData(cpu.memoryBase+arg);
			if (arg1 == 0) {
				cpu.accumulator = 1;
			} else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP();
		});
		
		INSTRUCTIONS.put(0x1F, arg -> {
			callback.halt();
		});
	}
	
	private class CPU {
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
