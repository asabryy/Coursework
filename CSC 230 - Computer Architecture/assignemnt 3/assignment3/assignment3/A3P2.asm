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
.def number = r16
.def orginalnumber = r17
.def mask = r20
.def count_h = r29
.def count_l = r28

ldi r19, 0xff
sts DDRL, r19 
out DDRB, r19

ldi count_h, 0x02
ldi count_l, 0x00

ldi number, 0x05
jmp loop

loop:
	;store mask
	ldi mask, 0b00000001
	mov orginalnumber, number
	;store number to data memeory
	st y, number
	;bitwise operations
	AND number, mask
	ldi mask, 0b00000010
	LSL number

	AND orginalnumber, mask
	LSL orginalnumber
	LSL orginalnumber
	OR number, orginalnumber
	LD orginalnumber, y
	ldi mask, 0b00000100

	AND orginalnumber, mask
	LSL orginalnumber
	LSL orginalnumber
	LSL orginalnumber
	OR number, orginalnumber
	LD orginalnumber, y
	ldi mask, 0b00001000

	AND orginalnumber, mask
	LSL orginalnumber
	LSL orginalnumber
	LSL orginalnumber
	LSL orginalnumber
	OR number, orginalnumber
	LD orginalnumber, y

	ldi mask, 0b00001111


    mov r18, number
    sts PORTL, r18
	ldi mask, 0b00001111
	AND number, mask
    mov r18, number
    out PORTB, r18

;delay: 
;    ldi r24, 0x2A ; approx. 0.5 second delay
;    outer2:
;		ldi r23, 0xFF
;		middle2: 
;			ldi r22, 0xFF
;			inner2: 
;				dec r22
;			brne inner2
;			dec r23
;		brne middle2
;		dec r24
;   brne outer2
  
    mov number, orginalnumber
    dec number
	cpi number, 0
    breq done

    jmp loop
 

;
; Your code finishes here.
;*************************

done:    jmp done 

; *************************
; * Data segment follows: *
; *************************
.dseg
.org 0x200
store:    .db 1










  
