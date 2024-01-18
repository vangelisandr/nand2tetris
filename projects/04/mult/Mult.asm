// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[3], respectively.)

  // Initialize number to
  @R0
  D=M
  @number
  M=D

  // Initialize m to 0
  @0
  D=A
  @m
  M=D

  // Initialize RAM[3] = 0
  @R3
  M=0

(MULTIPLIER_LOOP)
  // if m - RAM[1] == 0 goto END else multiply
  @m
  D=M
  @R1
  D=D-M
  @END
  D;JEQ

  @number
  D=M
  @R3
  M=M+D
  @m
  M=M+1
  @MULTIPLIER_LOOP
  0;JMP

(END)
  @END
  0;JMP
