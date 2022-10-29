import chisel3._
import chisel3.iotesters.PeekPokeTester

class ControlUnitTester(dut: ControlUnit) extends PeekPokeTester(dut) {

  // Check if the control unit is working correctly for the R-Type instructions
  // Addition
  poke(dut.io.opcode, "b000000".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Subtraction
  poke(dut.io.opcode, "b000001".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000001".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Multiplication
  poke(dut.io.opcode, "b000010".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000010".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Add immediate
  poke(dut.io.opcode, "b000011".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000011".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Sub immediate
  poke(dut.io.opcode, "b000100".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000100".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Mul immediate
  poke(dut.io.opcode, "b000101".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000101".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Check if the control unit is working correctly for the I-Type instructions

  // Load immediate
  poke(dut.io.opcode, "b010000".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Load data
  poke(dut.io.opcode, "b010001".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, true)
  expect(dut.io.readReg, true)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,true)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Store data
  poke(dut.io.opcode, "b010010".U)
  step(1)
  expect(dut.io.regWrite, false)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, true)
  expect(dut.io.readReg, true)
  expect(dut.io.writeToMemory, true)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)

  // Move data
  poke(dut.io.opcode, "b010011".U)
  step(1)
  expect(dut.io.regWrite, true)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, false)


  // Check if the control unit is working correctly for the J-Type instructions

  // Jump
  poke(dut.io.opcode, "b100000".U)
  step(1)
  expect(dut.io.regWrite, false)
  expect(dut.io.aluOp, "b100000".U)
  expect(dut.io.aluSrc, true)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, true)
  expect(dut.io.stop, false)

  // Jump if equal
  poke(dut.io.opcode, "b100001".U)
  step(1)
  expect(dut.io.regWrite, false)
  expect(dut.io.aluOp, "b100001".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, true)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, true)
  expect(dut.io.stop, false)

  // End execution
  poke(dut.io.opcode, "b111111".U)
  step(1)
  expect(dut.io.regWrite, false)
  expect(dut.io.aluOp, "b000000".U)
  expect(dut.io.aluSrc, false)
  expect(dut.io.memToReg, false)
  expect(dut.io.readReg, false)
  expect(dut.io.writeToMemory, false)
  expect(dut.io.loadFromMemory,false)
  expect(dut.io.jump, false)
  expect(dut.io.stop, true)

}

object ControlUnitTester {
  def main(args: Array[String]): Unit = {
    println("Testing Control Unit")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ControlUnit"),
      () => new ControlUnit()) {
      c => new ControlUnitTester(c)
    }
  }
}
