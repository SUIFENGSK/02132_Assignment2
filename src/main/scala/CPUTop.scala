import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //Connecting the modules

  // ProgramCounter -> ProgramMemory
  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter
  programCounter.io.stop:=controlUnit.io.stop
  programCounter.io.jump:= controlUnit.io.jump && alu.io.comparisonResult //And Gate
  programCounter.io.programCounterJump:=programMemory.io.instructionRead

  // ProgramMemory -> RegisterFile and ControUnit
  registerFile.io.aSel:=programMemory.io.instructionRead(20,16);
  // Mux Gate, if sel true, then 25-21, else 15-11
  registerFile.io.bSel:= Mux(controlUnit.io.writeReadReg,programMemory.io.instructionRead(25,21),programMemory.io.instructionRead(15,11))
  registerFile.io.writeSel:=programMemory.io.instructionRead(25,21)
  // Mux Gate, if sel true, then dataRead, else alu output
  registerFile.io.writeData:=Mux(controlUnit.io.memToReg,dataMemory.io.dataRead,alu.io.result)
  registerFile.io.writeEnable:=controlUnit.io.regWrite

  // ProgramMemory -> ControlUnit
  controlUnit.io.opcode:=programMemory.io.instructionRead(31,26)

  // RegisterFile -> ALU
  alu.io.oper1:=registerFile.io.a
  // Extend sign for instruction(15,0) from 16 to 32 bits
  val signExtend=Wire(UInt(32.W))

  signExtend:=Cat(Fill(16,0.U),programMemory.io.instructionRead(15,0))
  alu.io.oper2:=Mux(controlUnit.io.aluSrc,signExtend,registerFile.io.b)
  alu.io.sel:=controlUnit.io.aluOp

  // Reduce sign four mux output from 32 to 16 bits
  val signReduce=Wire(UInt(16.W))
  signReduce:=Mux(controlUnit.io.loadFromMemory,registerFile.io.a,Mux(controlUnit.io.aluSrc,signExtend,registerFile.io.b))

  // RegisterFile -> DataMemory
  dataMemory.io.address:=signReduce
  dataMemory.io.dataWrite:=registerFile.io.a
  dataMemory.io.writeEnable:=controlUnit.io.writeToMemory

  io.done:=controlUnit.io.stop







  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}