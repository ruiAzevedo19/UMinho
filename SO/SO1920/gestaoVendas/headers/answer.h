#ifndef _ANSWER_H_
#define _ANSWER_H_

/* --- Answer structure --- */
typedef struct answer *Answer;

/**
 * @returns pointer to an answer
 */
Answer createAnswer();

/**
 * Initializes an answer structure 
 * @param char answer type;
 * @param int answer int value
 * @returns answer pointer 
 */
Answer initAnswer(char, int);

/**
 * Destroys an answer structure 
 *
 * @param Answer answer to destroy 
 */
void destroyAnswer(Answer);

/**
 * @param Answer answer
 * @returns type of answer 
 */
char getAnswerType(Answer);

/**
 * @param Answer answer 
 * @returns number of bytes to be read from output
 */
int getAnswerOutput(Answer);

/**
 * @returns size of an answer 
 */
int answerSize();

#endif 
