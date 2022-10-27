import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    // Input
    val opcode = Input(UInt(6.W))

    // Output
    val regWrite = Output(Bool())
    val aluOp = Output(UInt(6.W))
    val aluSrc = Output(Bool())
    val memToReg = Output(Bool())
    val writeReadReg = Output(Bool())
    val writeToMemory = Output(Bool())
    val jump = Output(Bool())
    val stop = Output(Bool())
  })

  // Default values
  io.regWrite := false.B // Not used for this instruction
  io.aluOp := "b000000".U // Not used for this instruction
  io.aluSrc := false.B //Not used for this instruction
  io.memToReg := false.B // Not used for this instruction
  io.writeReadReg := false.B // Not used for this instruction
  io.writeToMemory := false.B // Not used for this instruction
  io.jump := false.B // Not used for this instruction
  io.stop := true.B // Don't stop

  switch (io.opcode) {

    /* R-Type */

    // Addition
    is ("b000000".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000000".U // Addition
      io.aluSrc := false.B // Read from register
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Use instruction[15-11] as read register
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Subtraction
    is("b000001".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000001".U // Subtraction
      io.aluSrc := false.B // Read from register
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Use instruction[15-11] as read register
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Multiplication
    is("b000010".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000010".U // Multiplication
      io.aluSrc := false.B // Read from register
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Use instruction[15-11] as read register
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Add immediate
    is("b000011".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000011".U // Add immediate
      io.aluSrc := true.B // Read from immediate
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Sub immediate
    is("b000100".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000100".U // Subtract immediate
      io.aluSrc := true.B // Read from immediate
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Mul immediate
    is("b000101".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000101".U // Mul immediate
      io.aluSrc := true.B // Read from immediate
      io.memToReg := false.B // Save ALU result to register
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    /* I-Type */

    // Load immediate
    is("b010000".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000000".U // Add
      io.aluSrc := true.B // Read from immediate
      io.memToReg := false.B // Read from memory
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Load data
    is("b010001".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000000".U // Add
      io.aluSrc := true.B // Read from immediate
      io.memToReg := true.B // Read from memory
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Store data
    is("b010010".U) {
      io.regWrite := false.B // Don't write to register
      io.aluOp := "b000000".U // Add
      io.aluSrc := false.B // Read b
      io.memToReg := true.B // Not used for this instruction
      io.writeReadReg := true.B // Read R1
      io.writeToMemory := true.B // Write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    // Move data
    is("b010011".U) {
      io.regWrite := true.B // Write to register
      io.aluOp := "b000000".U // Add
      io.aluSrc := true.B // Read from immediate
      io.memToReg := false.B // Read from ALU
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := false.B // Don't jump
      io.stop := false.B // Don't stop
    }

    /* J-Type */

    // Jump
    is("b100000".U) {
      io.regWrite := false.B // Write to register
      io.aluOp := "b000000".U // Not used for this instruction
      io.aluSrc := true.B // Not used for this instruction
      io.memToReg := false.B // Not used for this instruction
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := true.B // Jump
      io.stop := false.B // Don't stop
    }

    // Jump if equal
    is("b100001".U) {
      io.regWrite := false.B // Don't write to register
      io.aluOp := "b100001".U // Subtraction
      io.aluSrc := false.B // Read from register
      io.memToReg := false.B // Not used for this instruction
      io.writeReadReg := true.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := true.B // Jump
      io.stop := false.B // Don't stop
    }

    // Jump if neq
    is("b100010".U) {
      io.regWrite := false.B // Don't write to register
      io.aluOp := "b000001".U // Subtraction
      io.aluSrc := false.B // Read from register
      io.memToReg := false.B // Not used for this instruction
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Don't write to memory
      io.jump := true.B // Jump
      io.stop := false.B // Don't stop
    }

    // End execution
    is("b111111".U) {
      io.regWrite := false.B // Not used for this instruction
      io.aluOp := "b000000".U // Not used for this instruction
      io.aluSrc := false.B //Not used for this instruction
      io.memToReg := false.B // Not used for this instruction
      io.writeReadReg := false.B // Not used for this instruction
      io.writeToMemory := false.B // Not used for this instruction
      io.jump := false.B // Not used for this instruction
      io.stop := true.B // Don't stop
    }

  }

}