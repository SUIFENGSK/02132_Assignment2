import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterFileTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  // Check if register 0 and 1 contains zero
  poke(dut.io.aSel, 0)
  poke(dut.io.bSel, 1)
  step(1)
  expect(dut.io.a, 0)
  expect(dut.io.b, 0)
  step(1)

  // Write to register 0 and 1
  poke(dut.io.writeSel, 0)
  poke(dut.io.writeData, 10)
  poke(dut.io.writeEnable, true)
  step(1)
  poke(dut.io.writeSel, 1)
  poke(dut.io.writeData, 20)
  poke(dut.io.writeEnable, true)
  step(1)

  // Check if register 0 and 1 contains 10 and 20
  poke(dut.io.aSel, 0)
  poke(dut.io.bSel, 1)
  step(1)
  expect(dut.io.a, 10)
  expect(dut.io.b, 20)
  step(1)

  // Check if writeEnable prevents writing to register
  poke(dut.io.writeSel, 0)
  poke(dut.io.writeData, 30)
  poke(dut.io.writeEnable, false)
  step(1)
  poke(dut.io.aSel, 0)
  step(1)
  expect(dut.io.a, 10)
  step(1)



}

object RegisterFileTester {
  def main(args: Array[String]): Unit = {
    println("Testing Register File")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterFileTester(c)
    }
  }
}
