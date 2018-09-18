/*
 * pal.c
 *
 *  Created on: 29 Mar 2018
 *      Author: valdar
 */

#include "useful.h"
#include <stdbool.h>

/*
 * Read in user input
 */
int readInput(){

	int num;

	printf("Enter a number");

	scanf("%d", &num);

	return num;

}

/*
 * Display the state of the game
 */
displayState(int* pListOfNumbers,  int  positionOfCursor, int number_of_digits, int numberOfGoes)
{
	printf("\n\nGame State:\n");

	int i = 0;

	//Prints out the game state
	printf("< ");
	for(i = 0; i < number_of_digits; i++){
	      printf("%d ", *(pListOfNumbers + i));

	}

	printf(">  Number of Goes %d\n", numberOfGoes);

	for(i = 0; i < number_of_digits; i++){ // code block

		printf("  ");
		 if(positionOfCursor == i){
			 printf("^");

		}

	}

}

/*
 * Move cursor right
 */
void moveCursorRight(int* pPosOfCursor, int max)
{

	//If position is at max moves cursor to start else moves cursor right 1
	if(*pPosOfCursor == max - 1){

		*pPosOfCursor = 0;

	}
	else{

		++*pPosOfCursor;

	}

}

/*
 * Moves cursor left
 */
void moveCursorLeft(int* pPosOfCursor, int max)
{
	//If position is at start moves cursor to end else moves cursor left 1
	if(*pPosOfCursor == 0){

		*pPosOfCursor = max - 1;

	}
	else{

		--*pPosOfCursor;

	}

}

/*
 * Increment the digit by 1
 */
void incrementDigitInListAtPos(int* pListOfNumbers, int positionOfCursor){


	int x = *(pListOfNumbers + positionOfCursor);

	// If number is at 9 rolls over to 0 when incrementing
	if(x == 9){

		x = 0;

	}
	else{

		x++;

	}

	pListOfNumbers[positionOfCursor] = x;

}

/*
 * decrement the digit by 1
 */
void decrementDigitInListAtPos(int* pListOfNumbers, int positionOfCursor){

	int x = *(pListOfNumbers + positionOfCursor);

	// If number is at 0 rolls over to 9 when decrementing
	if(x == 0){

		x = 9;

	}
	else{

		x--;

	}

		pListOfNumbers[positionOfCursor] = x;

}

/*
 * Checks to see if numbers are a palindrome
 */
boolean is_palindrome(int* plist_of_numbers, int size){

	   int i,j;

	   // Goes throught the numbers. checks first with last and so on
	   for (i= 0, j = size - 1 ; i < j ; ++i, --j) {

	      if (plist_of_numbers[i] != plist_of_numbers[j]) {


	    	  return False; // Not palindrome

	    	  }

	   }

	   return True; //Palindrome

}

/**
 * Get users command
 */
char get_command()
{

	char  validCharacters = { 'a','d', 'w', 'x'};

	printf("\n\nPlease enter a command\na = left, d = right, w = increase number, x = decrease number");

	char res = my_get_char();

	//If input is not one of the allowed it will ask the user again
	while(res != 'a' && res != 'd' && res != 'w' && res != 'x'){

		printf("\ninvalid \nPlease enter a command\na = left, d = right, w = increase number, x = decrease number");

		res = my_get_char();

	}

	return res;

}

/*
 * Process command by doing whatever action indicated by what was chosen
 */
void processCommand(int* pList, int size, int* pPositionOfCursor, char command, int* numberOfGoes)
{

	if(command == 'a'){

		moveCursorLeft(pPositionOfCursor, size);
		++*numberOfGoes;


	}
	else if(command == 'd'){

		moveCursorRight(pPositionOfCursor, size);
		++*numberOfGoes;

	}
	else if(command == 'w'){

		incrementDigitInListAtPos(pList, *pPositionOfCursor);
		++*numberOfGoes;

	}
	else if(command == 'x'){

		decrementDigitInListAtPos(pList, *pPositionOfCursor);
		++*numberOfGoes;

	}

}

/*
 * Creates array with a number
 */
int * initialiseArray(int number){

	int count = 0;

	int number1 = number;

	// get number of digits in number - this will be size of the array
	while(number1 > 0){

		count++;
		number1 /= 10;

	}

	int *numberArray = (int *)malloc((count) * sizeof(count));

	int i = 0;

	// add number to array
	for(i = 0; i < count; i++){

		numberArray[count - 1 - i] = number % 10;

		number = number / 10;


	}

	// return array and size of array
	int toBereturned[] = {numberArray, &count};

	return toBereturned;

}

/*
 * Get random value in a range
 */
int randRange(int min, int max)
{

	int ran = srand(); // try srand - random seed.
	ran = (ran % max) + min;


	return ran;

}
