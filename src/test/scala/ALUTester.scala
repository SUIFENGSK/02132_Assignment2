import chisel3._
import chisel3.iotesters.PeekPokeTester

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {
  //ADD
  poke(dut.io.sel, "b000000".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 10.U(32.W))
  step(1)
  expect(dut.io.result, 15.S(32.W))

  //SUB
  poke(dut.io.sel, "b000001".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 1.U(32.W))
  step(1)
  expect(dut.io.result, 4.U(32.W))

  //MULT
  poke(dut.io.sel, "b000010".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 10.U(32.W))
  step(1)
  expect(dut.io.result, 50.U(32.W))

  //ADDI
  poke(dut.io.sel, "b000011".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 1.U(32.W))
  step(1)
  expect(dut.io.result, 6.U(32.W))

  //SUBI
  poke(dut.io.sel, "b000100".U(8.W))
  poke(dut.io.oper1, 15.U(32.W))
  poke(dut.io.oper2, 10.U(32.W))
  step(1)
  expect(dut.io.result, 5.U(32.W))

  //MULTI
  poke(dut.io.sel, "b000101".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 2.U(32.W))
  step(1)
  expect(dut.io.result, 10.U(32.W))

  // LOAD IMMEDIATE
  poke(dut.io.sel, "b010000".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 100.U(32.W))
  step(1)
  expect(dut.io.result, 100.U(32.W))

  // MOVE DATA
  poke(dut.io.sel, "b010011".U(8.W))
  poke(dut.io.oper1, 50.U(32.W))
  poke(dut.io.oper2, 10.U(32.W))
  step(1)
  expect(dut.io.result, 50.U(32.W))

  //JEQ (true)
  poke(dut.io.sel, "b100001".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 5.U(32.W))
  step(1)
  expect(dut.io.comparisonResult, true)

  //JEQ (false)
  poke(dut.io.sel, "b100001".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 100.U(32.W))
  step(1)
  expect(dut.io.comparisonResult, false)

  //JR
  poke(dut.io.sel, "b100000".U(8.W))
  poke(dut.io.oper1, 5.U(32.W))
  poke(dut.io.oper2, 100.U(32.W))
  step(1)
  expect(dut.io.comparisonResult, true)
}

object ALUTester {
  def main(args: Array[String]): Unit = {
    println("Testing ALU")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ALU"),
      () => new ALU()) {
      c => new ALUTester(c)
    }
  }
}