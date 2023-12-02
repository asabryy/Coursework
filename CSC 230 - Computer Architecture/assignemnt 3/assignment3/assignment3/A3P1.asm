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
.def count = r17

ldi r18, 0xff
sts	DDRL, r18 
out DDRB, r18

ldi count, 0x00
ldi number, 0x05

jmp loop

loop:
	sts store, number
	inc store

	ldi r18, 0b10000000
	sts PORTL, r18
	ldi r18, 0b00000010
	out PORTB, r18

	rjmp delay
	
	dec number

	cpi number, 0
	breq done


delay: 
	ldi r24, 0x2A ; approx. 0.5 second delay
	outer2:
		ldi r23, 0xFF
		middle2: 
			ldi r22, 0xFF
			inner2: 
				dec r22
			brne inner2
			dec r23
		brne middle2
		dec r24
	brne outer2




;
; Your code finishes here.
;*************************

done:	jmp done

; *************************
; * Data segment follows: *
; *************************
.dseg
.org 0x200
store: .db 1