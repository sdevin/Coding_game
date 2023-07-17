#define _REENTRANT

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <pthread.h>

char* ficCmd = "/tmp/cmd";
char* ficCheck = "/tmp/checks";
int fd, fdCheck;
pthread_mutex_t _m = PTHREAD_MUTEX_INITIALIZER;

void* foncThread(void* persoName){
	char* name = (char*)persoName;
	
	char instruction[20];
	instruction[0] = '\0';
	strcat(instruction, name);
	strcat(instruction, " goCheck\n");
	pthread_mutex_lock(&_m);	
	write(fd, instruction, strlen(instruction));
	printf("envoie go %s\n", name);
	
	//attente check
	char buf[512];
	int nb;
	while((nb = read(fdCheck, buf, 512)) == 0){
	//printf("read : %s\n", buf);
	}
	
	instruction[0] = '\0';
	strcat(instruction, name);
	strcat(instruction, " outCheck\n");
	write(fd, instruction, strlen(instruction));
	printf("envoie out %s\n", name);
	pthread_mutex_unlock(&_m);
}

	
int main(){

	if((fd = open(ficCmd, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier commande ouvert\n");
	if((fdCheck = open(ficCheck, O_RDONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier checks ouvert\n");
	
	pthread_t idThread1, idThread2;

	pthread_create(&idThread1, NULL, foncThread, (void *)"toto");
	pthread_create(&idThread2, NULL, foncThread, (void *)"tata");
	
	pthread_join(idThread1, NULL);
	pthread_join(idThread2, NULL);
	
	char instruction[20];
	strcat(instruction, "end\n");
	write(fd, instruction, strlen(instruction));
	
	close(fd);
	close(fdCheck);

	return 0;

}
