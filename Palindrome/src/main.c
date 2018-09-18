/*
 ============================================================================
 Name        : Assign1.c
 Author      : Valdar
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "pal.h"
#include "useful.h"
#define boolean int
#define true 1
#define false 0

int main(void) {

	// Test code
	//int listOfNumbers[] = {1,2,3,6,8,9};
//	int number_of_digits = 6;
//	int positionOfCursor = 0;
//	int numberOfGoes = 0;

//	displayState(&listOfNumbers, positionOfCursor, number_of_digits, numberOfGoes);
//	incrementDigitInListAtPos(&listOfNumbers, positionOfCursor);

	//moveCursorRight(&positionOfCursor, number_of_digits);

	//displayState(&listOfNumbers, positionOfCursor, number_of_digits);
	setbuf(stdout, NULL);
//	char res =  get_command();

//	int a[] = {1,2,3,3,2,1};
//	int b[] = {1,2,3,3,2,2};
//	int c[] = {1,2,3,1,2,1};

	int num = readInput();

	startGame(num);

	return 0;


}
/*
 * Starts the game
 */
void startGame(int number){

	//Returns a array with the numbers and a size
	int *received = initialiseArray(number);

	// our lis of numbers
	int *listOfNumbers = received[0];

	// size of array
	int *temp = received[1];

	int number_of_digits = *temp;

	int positionOfCursor = 0;

	// if array is one can only have random cursor in one position else get random position for the size
	if(number_of_digits == 1){

		int positionOfCursor = randRange(0, number_of_digits);

	}
	else{

		int positionOfCursor = randRange(0, number_of_digits -1);

	}

	// number of goes it takes to complete
	int numberOfGoes = 0;

	// display current state of game
	displayState(listOfNumbers, positionOfCursor, number_of_digits, numberOfGoes);

	// check if complete
	boolean complete = is_palindrome(listOfNumbers, number_of_digits);

	// run while not palindrome
	while(complete == False){

		char res = get_command();

		processCommand(listOfNumbers, number_of_digits, &positionOfCursor, res, &numberOfGoes);

		displayState(listOfNumbers, positionOfCursor, number_of_digits, numberOfGoes);

		if(is_palindrome(listOfNumbers, number_of_digits) == True){

			complete = True;

		}

	}

	printf("\nComplete. Number of goes %d", numberOfGoes);

}

