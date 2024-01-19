// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[3], respectively.)

  // Initialize i to 0
  @0
  D=A
  @i
  M=D

  // Initialize RAM[2] = 0
  @R2
  M=0

  // Check if any of the the two numbers is 0
  @R0
  D=M
  @ZERO
  D;JEQ
  @R1
  D=M
  @ZERO
  D;JEQ

  // Check if one of the numbers is negative
  @R0
  D=M
  @R0_NEGATIVE
  D;JLT
  @R1
  D=M
  @R1_NEGATIVE
  D;JLT

  // goto both positive
  @BOTH_POSITIVE_MULT
  0;JMP

(R0_NEGATIVE)
  // Check if the other is negative too and if so goto BOTH_NEGATIVE
  @R1
  D=M
  @BOTH_NEGATIVE
  D;JLT

  // Negate R0
  @R0
  M=-M
(R01_NEGATIVE_MULT)  
  // if i - RAM[1] == 0 goto NEGATE else keep multiplying
  @i
  D=M
  @R1
  D=D-M
  @NEGATE
  D;JEQ

  @R0
  D=M
  @R2
  M=M+D
  @i
  M=M+1
  @R01_NEGATIVE_MULT
  0;JMP

(R1_NEGATIVE)
  // Check if the other is negative too and if so goto BOTH_NEGATIVE
  @R0
  D=M
  @BOTH_NEGATIVE
  D;JLT

  // Negate R1
  @R1
  M=-M
  @R01_NEGATIVE_MULT
  0;JMP

(NEGATE)
  // Negate and goto END
  @R2
  M=-M
  @END
  0;JMP

(BOTH_NEGATIVE)
  // Negate both numbers
  @R0
  M=!M
  @R1
  M=!M

(BOTH_POSITIVE_MULT)
  // if i - RAM[1] == 0 goto END
  @i
  D=M
  @R1
  D=D-M
  @END
  D;JEQ

  @R0
  D=M
  @R2
  M=M+D
  @i
  M=M+1
  @BOTH_POSITIVE_MULT
  0;JMP

(ZERO)
  @R2
  M=0

(END)
  @END
  0;JMP
