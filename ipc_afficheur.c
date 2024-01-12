#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>


struct flight {
	int time[4];
	char destination[10];	
	char flightRef[7];	
	int gate[2];
	char remarks[10];	
};

void flightToMsg(struct flight f, char* result){
  int j = 0;
  for(int i = 0; i<4; i++){
  	result[j] = f.time[i] + '0';
  	j++;
  }
  result[j] = ' ';
  j++;
  for(int i = 0; i<10; i++){
    if(f.destination[i] == '\0'){
    	break;
    }
  	result[j] = f.destination[i];
  	j++;
  }
  result[j] = ' ';
  j++;
  for(int i = 0; i<7; i++){
    if(f.flightRef[i] == '\0'){
    	break;
    }
  	result[j] = f.flightRef[i];
  	j++;
  }
  result[j] = ' ';
  j++;
  for(int i = 0; i<2; i++){
  	result[j] = f.gate[i] + '0';
  	j++;
  }
  result[j] = ' ';
  j++;
  for(int i = 0; i<10; i++){
    if(f.remarks[i] == '\0'){
    	break;
    }
  	result[j] = f.remarks[i];
  	j++;
  }
  result[j] = '\n';
  j++;
  result[j] = '\0';
 
}

int asChanged(struct flight oldFlight, struct flight newFlight){

    for(int i = 0; i<4; i++){
    	 if(oldFlight.time[i] != newFlight.time[i]){
    	 	return 1;
    	 }
    }
    for(int i = 0; i<10; i++){
    	 if(oldFlight.destination[i] != newFlight.destination[i]){
    	 	return 1;
    	 }
    }
    for(int i = 0; i<7; i++){
    	 if(oldFlight.flightRef[i] != newFlight.flightRef[i]){
    	 	return 1;
    	 }
    }
    for(int i = 0; i<2; i++){
    	 if(oldFlight.gate[i] != newFlight.gate[i]){
    	 	return 1;
    	 }
    }
    for(int i = 0; i<10; i++){
    	 if(oldFlight.remarks[i] != newFlight.remarks[i]){
    	 	return 1;
    	 }
    }

    return 0;
}

int main(void) {

  int cle, id;
  struct flight oldFlight;
  struct flight *adresse;  /* adresse du segment de memoire partagee */
  FILE* ficCmd;
 char* nomFicCmd = "/tmp/cmd";
  char cmd[100];
 
 
  if ((ficCmd = fopen(nomFicCmd, "w")) == NULL){
	perror("ouverture fichier donnee");
	exit(-1);
  }
	
  printf("pret\n");

/* Tirage de cle */

  if ((cle=ftok("launchCodingGameS6", 1))==-1){
    perror("ftok");
    exit(1);
  }

/* Creation d'un segment de mmoire partagee de la taille de la structure */
  if ((id = shmget(cle, sizeof(struct flight), 0600|IPC_CREAT))==-1) {
    perror("Creation memoire partagee");
    exit(1);
  }

/* Attachement de la zone de memoire a l'espace memoire du processus */

  if ((long)(adresse = shmat(id, NULL, 0)) == -1) {
    perror("Attachement memoire partagee");
    shmctl(id, IPC_RMID, NULL);  /* Suppression du segment */
    exit(1);
  }

  while(1){
  	if(asChanged(oldFlight, *adresse)){
  	  flightToMsg(*adresse, cmd);
  	  fputs(cmd, ficCmd);
			fflush(ficCmd);
  		oldFlight = *adresse;
  printf("envoie : %s\n", cmd);
  	}
  	usleep(100);
  }
/* Detachement du segment de memoire de l'espace du processus */

  shmdt(adresse);

  fclose(ficCmd);



  return 0;
}
