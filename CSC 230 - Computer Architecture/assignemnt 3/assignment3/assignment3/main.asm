.include "m2560def.inc"
;
; ***********************************************************
; * Program information goes here.                        *
; *  Examples include:  Course name, assignment number,   *
; *     program name, program description, program input, *
; *     program output, programmer name, creation date,   *
; *     dates of program updates. *
; *********************************************************

; *************************
; * Code segment follows: *
; *************************
.cseg

;************************
; Your code starts here:
;
;number1 = 103;
;number2 = 41;
;number3 = 15;

;defining the registers in use to the same labales giving in pseudo code.
.def number1 = r16
.def number2 = r17
.def number3 = r18
.def diff = r19

;using load instruction to assign each register with corosponding value
ldi number1, 103
ldi number2, 41
ldi number3, 15

;diff = number1
mov diff, number1
;diff -= number2
sub diff, number2
;diff -= number3
sub diff, number3

;result = difference;
sts result, diff

;
; Your code finishes here.
;*************************

done:	jmp done

; *************************
; * Data segment follows: *
; *************************
.dseg
.org 0x200
result: .db 1