#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>


int main(){

	int fd;
	char* fifo = "/tmp/fifo";
	char msg[20];
	
	printf("debut\n");
	
	mkfifo(fifo, 0666);
	printf("fifo cree\n");
	
	if((fd = open(fifo, O_RDONLY)) == -1){
		perror("ouverture fifo lecture : ");
		return 0;
	};
	read(fd, msg, 20);
	printf("recu : %s\n", msg);
	
	
	close(fd);

	return 0;

}
