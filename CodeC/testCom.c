#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

//envoi

int main(){

	int fd;
	char* fifo = "/tmp/fifo";
	char* listMsg[] = {"toto,r,", "tata,d,", "toto,r,", "toto,d,", "tata,d,", "toto,r,", "tata,r,", "tata,r,"};
	
	printf("debut\n");
	
	mkfifo(fifo, 0666);
	printf("fifo cree\n");
	if((fd = open(fifo, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fifo ouverte\n");
	for(int i = 0; i < 8; i++){
		write(fd, listMsg[i], sizeof(listMsg[i]));
		write(fd, "\n", 1);
	printf("message envoyé : %s\n", listMsg[i]);
	}
	
	
	close(fd);

	return 0;

}
