#define _REENTRANT

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <pthread.h>
//envoi

int totoPosX = 3;
int totoPosY = 4;
int tataPosX = 5;
int tataPosY = 2;
int goalX = 8;
int goalY = 7;

int fd;
	
	
void* foncThread(void* persoName){

	char* name = (char*)persoName;
	int x, y;
	if(strcmp(name, "toto") == 0){
		x = totoPosX;
		y = totoPosY;
	}else if(strcmp(name, "tata") == 0){
		x = tataPosX;
		y = tataPosY;
	}else{
		printf("Wrong name : %s \n !", name);
	}
	
	while(x != goalX){
		char instruction[20];
		instruction[0] = '\0';
		strcat(instruction, name);
		if(x < goalX){
			strcat(instruction, " r\n");
			x++;
		}else{
			strcat(instruction, " l\n");
			x--;
		}
		write(fd, instruction, strlen(instruction));
		printf("message envoyé : %s", instruction, strlen(instruction));
	}
	
	while(y != goalY){
		char instruction[20];
		instruction[0] = '\0';
		strcat(instruction, name);
		if(y < goalY){
			strcat(instruction, " d\n");
			y++;
		}else{
			strcat(instruction, " u\n");
			y--;
		}
		write(fd, instruction, strlen(instruction));
		printf("message envoyé : %s", instruction);
	}

}

int main(){

	char* fifo = "/tmp/fifo";
	
	mkfifo(fifo, 0666);
	printf("fifo cree\n");
	
	if((fd = open(fifo, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fifo ouverte\n");
	
	
	pthread_t idThread1, idThread2;

	pthread_create(&idThread1, NULL, foncThread, (void *)"toto");
	pthread_create(&idThread2, NULL, foncThread, (void *)"tata");
	
	pthread_join(idThread1, NULL);
	pthread_join(idThread2, NULL);
	
	close(fd);

	return 0;

}
