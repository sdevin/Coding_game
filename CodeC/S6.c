/* IPC: Memoire partagee - applicationTD5.c */



#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

struct flight {
	int time[4];
	char destination[10];	
	char flightRef[7];	
	int gate[2];
	char remarks[10];	
};


int main(void) {

  int cle, id;
  struct flight *adresse;   /* adresse du segment de memoire partagee */
/* Tirage de cle */

  if ((cle=ftok("../launchCodingGameS6", 1))==-1){
    perror("ftok");
    exit(1);
  }

  
/* Ouverture du segment de memoire partagee de la taille de la structure */
  if ((id = shmget(cle, sizeof(struct flight), 0600))==-1) {
    perror("Ouverture memoire partagee");
    exit(1);
  }

/* Attachement de la zone de memoire a l'espace memoire du processus */

  if ((long)(adresse = shmat(id, NULL, 0)) == -1) {
    perror("Attachement memoire partagee");
    shmctl(id, IPC_RMID, NULL);  /* Suppression du segment */
    exit(1);
  }
  adresse->time[0] = 1;
  adresse->time[1] = 5;
  adresse->time[2] = 2;
  adresse->time[3] = 3;
  strcpy(adresse->destination, "PARIS");
  strcpy(adresse->flightRef, "GFK14RF");
  adresse->gate[0] = 1;
  adresse->gate[1] = 5;
  strcpy(adresse->remarks, "BOARDING");
 
  return 0;
}
