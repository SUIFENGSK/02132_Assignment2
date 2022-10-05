import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })

  val notRun = !io.run
  val runOrStop = notRun | io.stop

  val countReg = RegInit(0.U(16.W))

  val adder = countReg + 1.U

  val mux1 = Mux(io.jump, io.programCounterJump,adder)

  val mux2 = Mux(runOrStop, countReg, mux1)

  countReg := mux2

  io.programCounter := countReg

}