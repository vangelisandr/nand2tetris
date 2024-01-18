// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

    // addr = base address of first screen row
    @SCREEN
    D=A
    @addr
    M=D

    @KBD
    D=A
    @key
    M=D

    @8191
    D=A
    @total_screen_words
    M=D
    

  (INFINITE_LOOP)
    @key
    A=M
    D=M
    @INIT_BLACK_LOOP
    D;JNE

    // Initialize white loop
    @i
    M=0
    @SCREEN
    D=A
    @addr
    M=D

  (WHITE_LOOP)
    @i
    D=M
    @total_screen_words
    D=D-M
    @INFINITE_LOOP
    D;JGT  // Jump to INFINITE_LOOP if i-RAM[0] < 0
    
    // RAM[addr] = 0
    @addr
    A=M
    M=0
    @addr
    D=M
    @1
    D=D+A
    @addr
    M=D
    @i
    M=M+1
    @WHITE_LOOP
    0;JMP

  (INIT_BLACK_LOOP)
    @i
    M=0
    @SCREEN
    D=A
    @addr
    M=D

  (BLACK_LOOP)
    @i
    D=M
    @total_screen_words
    D=D-M
    @INFINITE_LOOP
    D;JGT  // Jump to INFINITE_LOOP if i-RAM[0] < 0
    
    // RAM[addr] = -1
    @addr
    A=M
    M=-1
    @addr
    D=M
    @1
    D=D+A
    @addr
    M=D
    @i
    M=M+1
    @BLACK_LOOP
    0;JMP

    @INFINITE_LOOP
    0;JMP
