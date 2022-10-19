import chisel3._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val aSel = Input(UInt(5.W))
    val bSel = Input(UInt(5.W))
    val writeSel = Input(UInt(5.W))
    val writeData = Input(UInt(32.W))
    val writeEnable = Input(Bool())
    val a = Output(UInt(32.W))
    val b = Output(UInt(32.W))
  })

  // Create a register file with 32 32-bit registers with the initial value of 0
  val regFile = RegInit(VecInit(Seq.fill(32)(0.U(32.W))))

  // Select the registers to read from
  val aReg = regFile(io.aSel)
  val bReg = regFile(io.bSel)

  // Select the register to write to
  val writeReg = regFile(io.writeSel)

  // Write to the register if writeEnable is true
  when(io.writeEnable) {
    writeReg := io.writeData
  }

  // Output the register values
  io.a := aReg
  io.b := bReg
}