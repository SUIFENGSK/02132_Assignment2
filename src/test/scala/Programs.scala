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
    "b010000_00000_00000_0000000000000000".U(32.W), // 00. LI R0 0            R0 = 0
    "b010000_00001_00000_0000000000010011".U(32.W), // 01. LI R1 19           R1 = 19
    "b010000_00010_00000_0000000011111111".U(32.W), // 02. LI R2 255          R2 = 255
    "b010000_10101_00000_0000000000010100".U(32.W), // 03. LI R21 20          R21 = 20

    "b010011_00011_00000_0000000000000000".U(32.W), // 04. MOVE R3 R0         R3(x), x=0, first for-loop
    "b010011_00100_10101_0000000000000000".U(32.W), // 05. MOVE R4 R21        R(4) = constant 20
    "b000001_00101_00100_00011_00000000000".U(32.W),// 06. SUB R5 R4 R3       R5=R4-R3=20-x
    "b100001_00101_00000_0000000000111011".U(32.W), // 07. JEQ R5 R0 59       Jump to END if R5 = 0

    "b010011_00110_00000_0000000000000000".U(32.W), // 08. MOVE R6 R0         R6(y), y=0, second for-loop
    "b010011_00111_10101_0000000000000000".U(32.W), // 09. MOVE R7 R21        R7 = constant 20
    "b000001_01000_00111_00110_00000000000".U(32.W),// 10. SUB R8 R7 R6       R8=R7-R6=20-y
    "b100001_01000_00000_0000000000111001".U(32.W), // 11. JEQ R8 R0 57       Jump to x++ if R8 = 0

    "b000101_01001_00110_00000_00000010100".U(32.W), // 12. MULTI R9 R6 20    Calculate memory address R9=y*20
    "b000000_01010_01001_00011_00000000000".U(32.W), // 13. ADD R10 R9 R3     R10=y*20+x
    "b000011_01010_01010_00000_00110010000".U(32.W), // 14. ADDI R10 R10 400  R10=y*20+x+400 out_image

    "b100001_00011_00000_0000000000010001".U(32.W), // 15. JEQ R3 R0 17       Jump to line 17 if x=0
    "b100000_00000_00000_0000000000010011".U(32.W), // 16. JR 19              Jump to line 19 if x!=0
    "b010010_01010_00000_0000000000000000".U(32.W), // 17. SD R10 R0          memory[y*20+x+400]=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 18. JR 55              Jump to y++

    "b100001_00110_00000_0000000000010101".U(32.W), // 19. JEQ R6 R0 21       Jump to line 21 if y=0
    "b100000_00000_00000_0000000000010111".U(32.W), // 20. JR 23              Jump to line 23 if y!=0
    "b010010_01010_00000_0000000000000000".U(32.W), // 21. SD R10 R0          memory[y*20+x+400]=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 22. JR 55              Jump to y++

    "b100001_00011_00001_0000000000011001".U(32.W), // 23. JEQ R3 R1 25       Jump to line 26 if x=19
    "b100000_00000_00000_0000000000011011".U(32.W), // 24. JR 27              Jump to line 27 if x!=19
    "b010010_01010_00000_0000000000000000".U(32.W), // 25. SD R10 R0          memory[y*20+x+400]=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 26. JR 55              Jump to y++

    "b100001_00110_00001_0000000000011101".U(32.W), // 27. JEQ R6 R1 29       Jump to line 29 if y=19
    "b100000_00000_00000_0000000000011111".U(32.W), // 28. JR 31              Jump to line 31 if y!=19
    "b010010_01010_00000_0000000000000000".U(32.W), // 29. SD R10 R0          memory[y*20+x+400]=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 30. JR 55              Jump to y++

    "b000100_01011_01010_00000_00110010000".U(32.W), // 31. SUBI R11 R10 400  R11=y*20+x, in_image

    "b010001_01100_01011_0000000000000000".U(32.W), // 32. LD R12 R11         R12=memory[R11]=memory[y*20+x]

    "b100001_01100_00000_0000000000100011".U(32.W), // 33. JEQ R12 R0 35      Jump to line 35 if memory[R11]=0
    "b100000_00000_00000_0000000000100101".U(32.W), // 34. JR 37              Jump to line 37 if memory[R11]!=0
    "b010010_01010_00000_0000000000000000".U(32.W), // 35. SD R10 R0          memory[y*20+x+400]=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 36. JR 55              Jump to y++

    "b100001_01011_00000_0000000000000110".U(32.W), // 37. JEQ R6 R0 39       Jump to line 39 if R6=0
    "b000100_01101_01011_0000000000000001".U(32.W), // 38. SUBI R13 R11 1     R13=R11-1=y*20+x-1
    "b000011_01110_01011_0000000000000001".U(32.W), // 39. ADDI R14 R11 1     R14=R11+1=y*20+x+1
    "b100001_01011_00000_0000000000000110".U(32.W), // 40. JEQ R6 R0 42       Jump to line 42 if R6=0
    "b000100_01111_01011_0000000000010100".U(32.W), // 41. SUBI R15 R11 20    R15=R11-20=y*20+x-20
    "b000011_10000_01011_0000000000010100".U(32.W), // 42. ADDI R16 R11 20    R16=R11+20=y*20+x+20

    "b010001_10001_01101_0000000000000000".U(32.W), // 43. LD R17 R13         R17=memory[R13]=memory[y*20+x-1]
    "b010001_10010_01110_0000000000000000".U(32.W), // 44. LD R18 R14         R18=memory[R14]=memory[y*20+x+1]
    "b010001_10011_01111_0000000000000000".U(32.W), // 45. LD R19 R15         R19=memory[R15]=memory[y*20+x-20]
    "b010001_10100_10000_0000000000000000".U(32.W), // 46. LD R20 R16         R20=memory[R16]=memory[y*20+x+20]

    "b100001_10001_00000_0000000000110100".U(32.W), // 47. JEQ R17 R0 52      Jump to line 52 if R17=0, in_image(x-1,y)==0
    "b100001_10010_00000_0000000000110100".U(32.W), // 48. JEQ R18 R0 52      Jump to line 52 if R18=0, in_image(x+1,y)==0
    "b100001_10011_00000_0000000000110100".U(32.W), // 49. JEQ R19 R0 52      Jump to line 52 if R19=0, in_image(x,y-1)==0
    "b100001_10100_00000_0000000000110100".U(32.W), // 50. JEQ R20 R0 52      Jump to line 52 if R20=0, in_image(x,y+1)==0
    "b100000_00000_00000_0000000000110110".U(32.W), // 51. JR 54              Jump to line 54 if all in_image(x+-1,y+-1)!=0

    "b010010_01010_00000_0000000000000000".U(32.W), // 52. SD R10 R0          out image(x,y)=0
    "b100000_00000_00000_0000000000110111".U(32.W), // 53. JR 55              Jump to y++

    "b010010_01010_00010_0000000000000000".U(32.W), // 54. SD R10 R2          out image(x,y)=255

    "b000011_00110_00110_0000000000000001".U(32.W), // 55. ADDI R6 R6 1       y++

    "b100000_00000_00000_0000000000001010".U(32.W), // 56. JR 10              Jump to line 10 for a new y-loop

    "b000011_00011_00011_0000000000000001".U(32.W), // 57. ADDI R3 R3 1       x++

    "b100000_00000_00000_0000000000000110".U(32.W), // 58. JR 6               Jump to line 6 for a new x-loop

    "b111111_00000_00000_0000000000000000".U(32.W) // 59. END                 End of program
  )
}