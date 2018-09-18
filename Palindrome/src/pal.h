/*
 * pal.h
 *
 *  Created on: 29 Mar 2018
 *      Author: valdar
 */

#ifndef PAL_H_
#define PAL_H_
#include "useful.h"

int readInput();
displayState(int* pListOfNumbers,  int  positionOfCursor, int number_of_digits, int numberOgGoes);
void moveCursorRight(int* pPosOfCursor, int max);
void moveCursorLeft(int* pPosOfCursor);
void incrementDigitInListAtPos(int* pListOfNumbers, int positionOfCursor);
void decrementDigitInListAtPos(int* pListOfNumbers, int positionOfCursor);
boolean is_palindrome(int* plist_of_numbers, int size);
char get_command();
void processCommand(int* pList, int size, int* pPositionOfCursor, char command, int* numberOfGoes);
int randRange(int min, int max);

#endif /* PAL_H_ */
