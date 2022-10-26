import chisel3._
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester
import java.util

object Programs{
  val program1 = Array(
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W),
    "h00000000".U(32.W)
  )

  val program2 = Array(
    "b010000_00000_00000_0000000000000000".U(32.W), // LI R0 0
    "b010000_00001_00000_0000000000010011".U(32.W), // LI R1 19
    "b010000_00010_00000_0000000011111111".U(32.W), // LI R2 255
    "b010000_10101_00000_0000000000010100".U(32.W), // LI R21 20

    "b010011_00011_00000_0000000000000000".U(32.W), // MOVE R3 R0
    "b010011_00100_10101_0000000000000000".U(32.W), // MOVE R4 R21
    "b000001_00101_00100_00011_00000000000".U(32.W) // SUB R5 R4 R3
  )

  val erosion = Array(
    "b010000_00000_00000_0000000000000000".U(32.W), // LI R0 0
    "b010000_00001_00000_0000000000010011".U(32.W), // LI R1 19
    "b010000_00010_00000_0000000011111111".U(32.W), // LI R2 255
    "b010000_10101_00000_0000000000010100".U(32.W), // LI R21 20

    "b010011_00011_00000_0000000000000000".U(32.W), // MOVE R3 R0
    "b010011_00100_10101_0000000000000000".U(32.W), // MOVE R4 R21
    "b000001_00101_00100_00011_00000000000".U(32.W),// SUB R5 R4 R3
    "b100001_00101_00000_0000000000111010".U(32.W), // JEQ R5 R0 58

    "b010011_00110_00000_0000000000000000".U(32.W), // MOVE R6 R0
    "b010011_00111_10101_0000000000000000".U(32.W), // MOVE R7 R21
    "b000001_01000_00111_00110_00000000000".U(32.W), // SUB R8 R7 R6
    "b100001_01000_00000_0000000000111000".U(32.W), // JEQ R8 R0 56

    "b000101_01001_00110_00000_00000010100".U(32.W), // MULTI R9 R6 20
    "b000000_01010_01001_00011_00000000000".U(32.W), // ADD R10 R9 R3
    "b000011_01010_01010_00000_00110010000".U(32.W), // ADDI R10 R10 400

    "b100001_00011_00000_0000000000010010".U(32.W), // JEQ R3 R0 18
    "b100000_00000_00000_0000000000010100".U(32.W), // JR 20
    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b100001_00110_00000_0000000000010101".U(32.W), // JEQ R6 R0 21
    "b100000_00000_00000_0000000000011000".U(32.W), // JR 24
    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b100001_00011_00001_0000000000011010".U(32.W), // JEQ R3 R1 26
    "b100000_00000_00000_0000000000011100".U(32.W), // JR 28
    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b100001_00110_00001_0000000000011110".U(32.W), // JEQ R6 R1 30
    "b100000_00000_00000_0000000000100000".U(32.W), // JR 32
    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b000100_01011_01010_00000_00110010000".U(32.W), // SUBI R11 R10 400

    "b010001_01100_01011_0000000000000000".U(32.W), // LD R12 R11

    "b100001_01100_00000_0000000000100010".U(32.W), // JEQ R12 R0 34
    "b100000_00000_00000_0000000000100110".U(32.W), // JR 38
    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b100001_01011_01010_0000000000101000".U(32.W), // JEQ R11 R10 40
    "b000100_01101_01011_0000000000000001".U(32.W), // SUBI R13 R11 1
    "b000011_01110_01011_0000000000000001".U(32.W), // ADDI R14 R11 1
    "b100001_01011_00000_0000000000101011".U(32.W), // JEQ R11 R0 43
    "b000100_01111_01011_0000000000010100".U(32.W), // SUBI R15 R11 20
    "b000011_10000_01011_0000000000010100".U(32.W), // ADDI R16 R11 20

    "b010001_10001_01101_0000000000000000".U(32.W), // LD R17 R13
    "b010001_10010_01110_0000000000000000".U(32.W), // LD R18 R14
    "b010001_10011_01111_0000000000000000".U(32.W), // LD R19 R15
    "b010001_10100_10000_0000000000000000".U(32.W), // LD R20 R16

    "b100001_10001_00000_0000000000110011".U(32.W), // JEQ R17 R0 51
    "b100001_10010_00000_0000000000110011".U(32.W), // JEQ R18 R0 51
    "b100001_10011_00000_0000000000110011".U(32.W), // JEQ R19 R0 51
    "b100001_10100_00000_0000000000110011".U(32.W), // JEQ R20 R0 51

    "b010010_01010_00000_0000000000000000".U(32.W), // SD R10 R0
    "b100000_00000_00000_0000000000111000".U(32.W), // JR 56

    "b010010_01010_00010_0000000000000000".U(32.W), // SD R10 R2

    "b000011_00110_00110_0000000000000001".U(32.W), // ADDI R6 R6 1

    "b100000_00000_00000_0000000000001011".U(32.W), // JR 11

    "b000011_00011_00011_0000000000000001".U(32.W), // ADDI R3 R3 1

    "b100000_00000_00000_0000000000000111".U(32.W), // JR 7

    "b111111_00000_00000_0000000000000000".U(32.W) // END
  )
}