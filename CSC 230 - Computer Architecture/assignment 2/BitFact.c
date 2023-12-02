#include <stdio.h>

 
#define SIZE_INT 16

void printBitArray(unsigned char theBits[SIZE_INT]){
	int i;
	printf("0b");
	for(i=SIZE_INT-1; i>=0; i--){
		printf("%u", theBits[i]);
	}		
}

void toBits(unsigned short value,unsigned char inBits[SIZE_INT]){
	
	unsigned i;
	int c = 15;
	for(i=1 << 15; i>0; i =i/2){
		if(value & i){
			inBits[c]=1;
		}else{
			inBits[c]=0;
		}
		c--;
	}
}

signed short factorial(unsigned short num){
	
	if(num == 0){
		return 1;
	}
	return num*factorial(num - 1);
}


int main()

{
	int flag = 0;
	unsigned char binary[SIZE_INT];
	signed short result;
	unsigned short value;
	char exit;

	printf( "\nFACTORIAL AND BIT TESTER\n\n" );

	//printBitArray(test);
	while(flag == 0){

		printf( "\nInput a positive integer value between 0 and 65535 ==> " );

		scanf("%hu", &value);
		result = factorial(value);

		toBits(result, binary);
		printf("%hu Factorial = %hu	or	",value, result);
		printBitArray(binary);
		
		printf( "\nDo another (y/n)?\n" );

		scanf(" %c", &exit);
		
		if(exit == 'n'){
			flag = 1;
		}	   
		
	}
	
	return 0;
}