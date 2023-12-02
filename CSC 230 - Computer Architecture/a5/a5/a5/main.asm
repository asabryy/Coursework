
.def tempb = r22

ldi r16, low(RAMEND)
out SPL, r16
ldi r16, high(RAMEND)
out SPH, r16

main:
	call get_message
	
	ldi XH, high(msg1)
	push XH
	ldi XL, low(msg1)
	push XL
	ldi YH, high(line1)
	push YH
	ldi YL, low(line1)
	push Yl
	call reverse

	ldi XH, high(msg2)
	push XH
	ldi XL, low(msg2)
	push XL
	ldi YH, high(line2)
	push YH
	ldi YL, low(line2)
	push Yl
	call reverse

	ldi ZH, 03
	ldi ZL, 44

	ldi XH, high(line1)
	st -Z, XH
	ldi XL, low(line1)
	st -Z, XL

	ldi XH, high(line2)
	st -Z, XH
	ldi XL, low(line2)
	st -Z, XL

	call display

	call timer1_setup

done: jmp done

get_message:
	push XH
	push XL
	push YH
	push YL

	ldi XH, high(msg1)
	ldi XL, low(msg1)
	ldi YH, high(msg1_p<<1)
	ldi YL, low(msg1_p<<1)

	save_msg1:
		lpm tempb, Z+
		st  Y+, tempb
		tst tempb
		brne save_msg1
	
	ldi XH, high(msg2)
	ldi YL, low(msg2)
	ldi YH, high(msg2_p<<1)
	ldi YL, low(msg2_p<<1)

	save_msg2:
		lpm tempb, Z+
		st  Y+, tempb
		tst tempb
		brne save_msg2

	pop YL
	pop YH
	pop XL
	pop XH

	ret

reverse:
	pop r17
	pop r18

	push XH
	push XL
	push ZH
	push ZL

	push r20

	ldi r20, 18	
	add XL, r20
	
	lp:
		ld tempb, -X
		st Y+, tempb
		dec r17
		brne lp
		
	pop r20

	pop YL
	pop YH
	pop XL
	pop XH

	push r18
	push r17


	ret

.equ TIMER1_DELAY = 208
.equ TIMER1_MAX_COUNT = 0xFFFF
.equ TIMER1_COUNTER_INIT=TIMER1_MAX_COUNT-TIMER1_DELAY + 1
timer1_setup:	
	; timer mode	
	ldi r16, 0x00		; normal operation
	sts TCCR1A, r16

	; prescale 
	; Our clock is 16 MHz, which is 16,000,000 per second
	;
	; scale values are the last 3 bits of TCCR1B:
	;
	; 000 - timer disabled
	; 001 - clock (no scaling)
	; 010 - clock / 8
	; 011 - clock / 64
	; 100 - clock / 256
	; 101 - clock / 1024
	; 110 - external pin Tx falling edge
	; 111 - external pin Tx rising edge
	;ldi r16, (1<<CS12)|(1<<CS10)	; clock / 1024
	ldi r16, (1<<CS10)				; clock / 1
	sts TCCR1B, r16
	
	; allow timer to interrupt the CPU when it's counter overflows
	ldi r16, 1<<TOIE1
	sts TIMSK1, r16

	; set timer counter to TIMER1_COUNTER_INIT (defined above)
	ldi r16, high(TIMER1_COUNTER_INIT)
	sts TCNT1H, r16 	; must WRITE high byte first 
	ldi r16, low(TIMER1_COUNTER_INIT)
	sts TCNT1L, r16		; low byte
	
	; enable interrupts (the I bit in SREG)
	sei	

	ret


display:
	push YL
	push YH
	push XL
	push XH

	push r20

	ld XL, Z+
	ld XH, Z+
	ldi YH, high(LCDCacheBottom)
	ldi YL, low(LCDCacheBottom)

	ldi r20, 18	
	save_msg1lcd:
		ld tempb, X+
		st  Y+, tempb
		dec r20
		cpi r20, 0
		brne save_msg1lcd

	ld XL, Z+
	ld XH, Z+
	ldi YH, high(LCDCacheTopLine)
	ldi YL, low(LCDCacheTopLine)

	ldi r20, 18	
	save_msg2lcd:
		ld tempb, X+
		st  Y+, tempb
		dec r20
		cpi r20, 0
		brne save_msg2lcd

	pop r20

	pop XH
	pop XL
	pop YH
	pop YL

	ret


;***********************************************************************************
msg1_p: .db "Ahmed     Ahmed  ", 0
msg2_p: .db "CSC     230    A5", 0
.dseg

msg1: .byte 40
msg2: .byte 40
msg3: .byte 40
msg4: .byte 40
msg5: .byte 40

line1: .byte 19
line2: .byte 19
line3: .byte 19
line4: .byte 19

; note message length = 17 characters + the null character (0)
.equ msg_length = 18

; LCD Cache: contains a copy of the 2 lines to be displayed on the screen
LCDCacheTopLine: .byte msg_length
LCDCacheBottom: .byte msg_length