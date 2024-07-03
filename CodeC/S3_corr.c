#define _REENTRANT

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <pthread.h>

#define FIC_S "/tmp/cmd"
#define FIC_CHECK "/tmp/checks"

pthread_mutex_t _m = PTHREAD_MUTEX_INITIALIZER;

void* foncThread(void* persoName){
	char* name = (char*)persoName;
	
	FILE *fdS, *fdC;
	char buf[512], buf1[512], nomFicC[50];

	if((strcmp(name, "toto") != 0) && (strcmp(name, "tata") != 0)){
		
		printf("Wrong name : %s \n !", name);
	}


	if((fdS = fopen(FIC_S, "a")) == NULL){
		perror("ouverture fichier commandes");
		return 0;
	}

  sprintf(nomFicC, "/tmp/check_%s", name);

	if((fdC = fopen(nomFicC, "r")) == NULL){
		perror("ouverture fichier check");
		return 0;
	}
	
	
	pthread_mutex_lock(&_m);

	// Envoi de la commande goCheck
	sprintf(buf, "%s goCheck\n", name);

	fputs(buf, fdS);
	fflush(fdS);
	printf("envoi %s\n", buf);
	
	//attente check du portique

	while (fgets(buf1, 512, fdC) == NULL) {
	}
	printf("reponse portique :%s:\n", buf1);
	
	// Envoi de la commande goCheck
	sprintf(buf, "%s outCheck\n", name);

	fputs(buf, fdS);
	fflush(fdS);

	printf("envoi %s\n", buf);
	pthread_mutex_unlock(&_m);

	fclose(fdS);
	fclose(fdC);

	return 0;
}

	
int main(){

	char instruction[20];
	pthread_t idThread1, idThread2;
	FILE *fdS;	

	pthread_create(&idThread1, NULL, foncThread, (void *)"toto");
	pthread_create(&idThread2, NULL, foncThread, (void *)"tata");
	
	pthread_join(idThread1, NULL);
	pthread_join(idThread2, NULL);
	

	
	if((fdS = fopen(FIC_S, "a")) == NULL){
		perror("ouverture fifo ecriture : ");
		return 0;
	}	

	strcpy(instruction, "end\n");
	fputs(instruction, fdS);
	fflush(fdS);
	
	fclose(fdS);

	return 0;

}
