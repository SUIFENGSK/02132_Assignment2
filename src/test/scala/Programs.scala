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
}