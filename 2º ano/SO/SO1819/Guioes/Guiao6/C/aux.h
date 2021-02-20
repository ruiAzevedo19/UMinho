#ifndef AUX_H_
#define AUX_H_

ssize_t readln(int fildes, void *buf, size_t nbyte);

char **words(char *command);

int sys(char *command);	


#endif
