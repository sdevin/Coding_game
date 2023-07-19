#define _REENTRANT
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <pthread.h>
#include <semaphore.h>


char* ficCmd = "/tmp/cmd";
char* ficCheck = "/tmp/barman";
int fd, fdCheck;
char _produit[20]; 
sem_t _Sreponse, _Srequete;
pthread_mutex_t _m = PTHREAD_MUTEX_INITIALIZER; 


void *client(void *arg){

  int num = (int)arg;
  char name[20];
  char instruction[20];
  char produit[20];
  
  if(num == 1){
  	strcat(name, "toto");
  	strcat(produit, "biere");
  }else if(num == 2){
  	strcat(name, "tata");
  	strcat(produit, "jus");
  }
  
    pthread_mutex_lock(&_m);
    instruction[0] = '\0';
    strcat(instruction, name);
    strcat(instruction, " enter\n");
    write(fd, instruction, strlen(instruction));
    printf("%s", instruction);
    
    sprintf(_produit, produit);
    sem_post(&_Srequete);
    sem_wait(&_Sreponse);
    
    instruction[0] = '\0';
    strcat(instruction, name);
    strcat(instruction, " tip\n");
    write(fd, instruction, strlen(instruction));
    printf("%s", instruction);
    pthread_mutex_unlock(&_m);
 return 0;
}

void *serveur(void *arg){

  char instruction[20];
  while (1) { 
    sem_wait(&_Srequete);
    instruction[0] = '\0';
    strcat(instruction, "barman");
    strcat(instruction, " ");
    strcat(instruction,  _produit);
    strcat(instruction, "\n");
    write(fd, instruction, strlen(instruction));
    printf("%s", instruction);
    
    //attente service
char buf[512];
int nb;
while((nb = read(fdCheck, buf, 512)) == 0){
//printf("read : %s\n", buf);
}
    sem_post(&_Sreponse);
  }
  
 return 0;
}


int main(void){

	if((fd = open(ficCmd, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier commande ouvert\n");
	if((fdCheck = open(ficCheck, O_RDONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier retour ouvert\n");
	
  pthread_t idThreadC1,idThreadC2, idThreadS;
  pthread_create(&idThreadS, NULL, serveur, NULL);
  pthread_create(&idThreadC1, NULL, client, (void*)1);
  pthread_create(&idThreadC2, NULL, client, (void*)2);

  pthread_join(idThreadC1, NULL);
  pthread_join(idThreadC2, NULL);
  
	
	char instruction[20];
	instruction[0] = '\0';
	strcat(instruction, "end\n");
	write(fd, instruction, strlen(instruction));
	printf("envoie end \n");
	
	close(fd);
	close(fdCheck);


  return 0;
}


