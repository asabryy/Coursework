.include "m2560def.inc"
;
; *********************************************************
; * Program information goes here. *
; * Examples include: Course name: CSC 230, assignment number: 03, *
; * program name, program description, program input, *
; * program output, programmer name, creation date, *
; * dates of program updates. *
; *******************************************************
; ***********************
; * Code segment follows: *
; ***********************
.cseg
;************************
; Your code starts here:
ldi XL,low(0x200)
ldi XH,high(0x200)
ldi r16, 0x0f
 .def temp = r17
 ldi r18, 0xff
 sts DDRL,r18
 .def mask = r19
on:
 ldi r19, 0b00001000
 AND r19,r16
 lsl r19
 lsl r19
 lsl r19
 lsl r19
 mov temp, r19
 ldi r19, 0b00000100
 AND r19,r16
 lsl r19
 lsl r19
 lsl r19
 OR temp, r19
 ldi r19, 0b00000010
 AND r19,r16
 lsl r19
 lsl r19
 OR temp, r19
 ldi r19, 0b00000001
 AND r19,r16
 lsl r19
 OR temp, r19
 sts PORTL,temp

; ldi r24, 0x2A ; approx. 0.5 second delay
;outer: ldi r23, 0xFF
;middle: ldi r22, 0xFF
;inner: dec r22
; brne inner
; dec r23
; brne middle
; dec r24
; brne outer
; lpm r19, Z
 st X+,r16
 dec r16
 cpi r16 ,0x00
 brne on
; Your code finishes here.
;*************************
done: jmp done
; ***********************
; * Data segment follows: *
; ***********************
.dseg
.org 0x200