;TODO MACROS/Organizing the code

;Setting up the Hardware Stack
.def temp = r16
ldi r16, low(RAMEND)
out SPL, temp
ldi r16, high(RAMEND)
out SPH, temp
.undef temp

;Setting up the Software Stack
ldi XH, 03
ldi XL, 44
#define pushd(Rr) st -X, Rr
#define popd(Rd) ld Rd, X+

;testing get_message
part1:
;putting the locations into registers


ldi YH, high(msg1)
ldi YL, low(msg1)
ldi ZH, high(msg1_p<<1)
ldi ZL, low(msg1_p<<1)
call get_message
ldi YH, high(msg2)
ldi YL, low(msg2)
ldi ZH, high(msg2_p<<1)
ldi ZL, low(msg2_p<<1)
call get_message
test_reverse:
ldi YH, high(msg1)
ldi YL, low(msg1)
ldi ZH, high(line1)
ldi ZL, low(line1)

push YH
push YL
push ZH
push ZL
call reverse

ldi YH, high(msg2)
ldi YL, low(msg2)
ldi ZH, high(line2)
ldi ZL, low(line2)

push YH
push YL
push ZH
push ZL
call reverse

test_display:
ldi YH, high(line1)
ldi YL, low(line1)
pushd(YH)
pushd(YL)
ldi YH, high(line2)
ldi YL, low(line2)
pushd(YH)
pushd(YL)
call display

done: jmp done



;get_message (prog_loc, data_loc)
;The parameter prog_loc is an address in program memory and must be passed to the
;function in registers (use Z). The parameter data_loc is an address in data memory and must be
;passed to the function in registers (use Y).
get_message:
.def char = r23

;Protecting the registers
push YH
push YL
push ZH
push ZL
push char
;Copying message into IRam
copy_loop:
lpm char, Z+
st  Y+, char
tst char
brne copy_loop

pop char
pop ZL
pop ZH
pop YL
pop YH
.undef char

ret
;reverse(message, location)
;The parameter message is an address in data memory that references the start of the string
;that will be reversed and must be passed to the function on the hardware stack. The
;parameter location is the address in data memory where the reversed string will be stored.
;It must be passed to the function on the hardware stack. 
reverse:
	.def char = r16
	;getting them dun return pointers
	pop r20
	pop r21
	pop r22
	pop ZL
	pop ZH
	pop YL
	pop YH
	;Protecting the registers
	push r17
	push char
	push YH
	push YL
	push ZH
	push ZL
	;The String reversal
	ldi r17, 18	
	add YL, r17
	reverse_loop:
	ld char, -Y
	st Z+, char
	dec r17
	brne reverse_loop	
	;Stack shenanigans
	pop ZL
	pop ZH
	pop YL
	pop YH
	pop char
	pop r17
	;returnin
	push r22
	push r21
	push r20
	.undef char
	ret


;display(lineA, lineB)
;The parameter lineA is the address in program memory of the first line ed and and lineB is
;the address in program memory of the second line to be displayed. Both must be passed to the
;function using the software stack.
display:
;protecting the registers
push ZL
push ZH
push YL
push YH
.def char = r16
push char
push r17

popd(YL)
popd(YH)
ldi ZH, high(LCDCacheBottom)
ldi ZL, low(LCDCacheBottom)
ldi r17, 18	
copytoLCDBot:
ld char, Y+
st  Z+, char
dec r17
brne copytoLCDBot
popd(YL)
popd(YH)
ldi ZH, high(LCDCacheTopLine)
ldi ZL, low(LCDCacheTopLine)
ldi r17, 18	
copytoLCDTop:
ld char, Y+
st  Z+, char
dec r17
brne copytoLCDTop
pop r17
pop char
pop YH
pop YL
pop ZH
pop ZL
.undef char
ret


;***********************************************************************************
msg1_p: .db "abcdefghijklmnop ", 0
msg2_p: .db "Teaches Assembly ", 0
.dseg
; note message length = 17 characters + the null character (0)
.equ msg_length = 18
; The program copies the strings from program memory
; into data memory.
;
msg1: .byte msg_length
msg2: .byte msg_length
; These strings contain characters to be displayed on the LCD.
; Each time through, the characters are copied into these memory locations.
line1: .byte msg_length
line2: .byte msg_length
line3: .byte msg_length
line4: .byte msg_length
; LCD Cache: contains a copy of the 2 lines to be displayed on the screen
LCDCacheTopLine: .byte msg_length
LCDCacheBottom: .byte msg_length