import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    var sel= Input(UInt(6.W))
    var oper1= Input(UInt(32.W))
    var oper2= Input(UInt(32.W))
    var comparisonResult= Output(Bool())
    var result= Output(UInt(32.W))
  })
  //Implement this module here
  io.result := 0.U(32.W)
  io.comparisonResult := 0.U
  switch(io.sel)
  {
    is("b000000".U) //ADD
    {
      io.result := io.oper1 + io.oper2
    }
    is("b000001".U) //SUB
    {
      io.result := io.oper1 - io.oper2
    }
    is("b000010".U) //MULT
    {
      io.result := io.oper1 * io.oper2
    }
    is("b000011".U) //ADDI
    {
      io.result := io.oper1 + io.oper2
    }
    is("b000100".U) //SUBI
    {
      io.result := io.oper1 - io.oper2
    }
    is("b000101".U) //MULTI
    {
      io.result := io.oper1 * io.oper2
    }
    is("b010000".U) //LOAD IMMEDIATE
    {
      io.result := io.oper2
    }
    is("b010011".U) // MOVE DATA
    {
      io.result := io.oper1
    }
    is("b100001".U) //JEQ
    {
      when(io.oper1===io.oper2)
      {
        io.comparisonResult := 1.U
      }.otherwise
      {
        io.comparisonResult := 0.U
      }
    }
  }
}