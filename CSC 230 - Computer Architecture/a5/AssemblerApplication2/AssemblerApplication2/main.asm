
ldi XL, 0x00 ; Initialize X
 ldi XH, 0x02
 ldi R16, 0x24 ; Initialize cumulator register (R16)
 ldi r17, 0x3E ; outer loop counter
outer: ldi r18, 0x3B ; inner counter
inner: adm R16, X
 inc XL
 sbm R16, X
 inc XL
 dec r18
 brne inner
 dec r17
 brne outer;

 done: rjmp done

