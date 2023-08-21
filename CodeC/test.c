#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>

int main(int argc, char *argv[]){

	char* ficCmd = "/tmp/cmd";
	char instruction[20];
	int fd;
	
	if(argc < 2){
		printf("Argument manquant !\n");
		return 0;
	}else{
		instruction[0] = '\0';
		strcat(instruction, argv[1]);
	}

	if((fd = open(ficCmd, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};

	
	write(fd, instruction, strlen(instruction));
	printf("envoie %s \n", instruction);
	
	close(fd);

	return 0;
}

