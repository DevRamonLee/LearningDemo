#ifndef _APUE_H
#define _APUE_H

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <errno.h>
#include <signal.h>
#include <stdarg.h>
#include <string.h>
#include <time.h>

#define MAXLINE 4096
#define SAFE_CLOSE(fd) if ((fd) != -1) close(fd)
#define min(a,b) ((a) < (b) ? (a) : (b))

void err_sys(const char *fmt, ...);
void err_msg(const char *fmt, ...);
void err_quit(const char *fmt, ...);
void err_ret(const char *fmt, ...);
void err_dump(const char *fmt, ...);
void err_exit(int status, const char *fmt, ...);
void err_cont(int status, const char *fmt, ...);
void err_flush(void);
void err_file(const char *fmt, ...);

#endif /* _APUE_H */

