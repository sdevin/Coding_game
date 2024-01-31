#define _REENTRANT

#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <pthread.h>

#define FIC_S "/tmp/cmd"
	
FILE *fdS;
	
void* foncThread(void* persoName){

	char* name = (char*)persoName;
	FILE *fdE;
	char ficEntree[30], buf[512];

	if((strcmp(name, "toto") != 0) && (strcmp(name, "tata") != 0)){
		
		printf("Wrong name : %s \n !", name);
	}

	sprintf(ficEntree, "listeCmd_%s.txt", name);

	printf("fichier entree : %s\n", ficEntree);

	if((fdE = fopen(ficEntree, "r")) == NULL){
		perror("ouverture fichier de commandes");
		return 0;
	}


	while (fgets(buf, 512, fdE) != NULL){
		printf("lu :%s:", buf);
		fputs(buf, fdS);
		fflush(fdS);
		sleep(1);
	}
	
	fclose(fdE);

	printf("Fin trajet %s\n", name);
	
	return NULL;
	}



int main(){

	char instruction[20];
	
	if((fdS = fopen(FIC_S, "a")) == NULL){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fifo ouverte\n");
	
	
	pthread_t idThread1, idThread2;

	pthread_create(&idThread1, NULL, foncThread, (void *)"toto");
	pthread_create(&idThread2, NULL, foncThread, (void *)"tata");
	
	pthread_join(idThread1, NULL);
	pthread_join(idThread2, NULL);
	
	
	strcpy(instruction, "end\n");
	fputs(instruction, fdS);
	
	fclose(fdS);

	return 0;

}
