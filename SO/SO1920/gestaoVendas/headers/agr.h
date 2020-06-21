#ifndef _AGR_H_
#define _AGR_H_

typedef struct agr *Agr;

/**
 * Initializes structure
 *
 * @param const char* path to output file 
 * @param const char* path to index file 
 * @param int pipe that receives what processes has finished
 * @returns agr structure 
 */
Agr initAgr(const char*,const char*,int);

/**
 * Destroys structure 
 */
void destroyAgr(Agr);

/**
 * main method no aggregate files 
 *
 * @param Agr structure 
 */
void aggregate(Agr);

#endif
