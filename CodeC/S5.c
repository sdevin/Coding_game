#define _REENTRANT
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>



#define TAILLE_FILE 5

char _fileAtt[TAILLE_FILE][10]; //TAILLE_FILE éléments de 10 caractères
sem_t _Slibre, _Sinfo;
FILE* ficCmd;
char* nomFicCmd = "/tmp/cmd";
	char cmd[20];

void *producteur(void *arg){

  int _indEcr =0;
  int num=(int)arg;
  char* nomFic = "../planes.txt";
  char buf[10];
  FILE* fic;

  // ouverture fichier de donnes
  if ((fic = fopen(nomFic, "r")) == NULL){
	perror("ouverture fichier donnee");
	exit(-1);
  }

  while (fgets(buf, sizeof(buf), fic)!=NULL) {
  buf[strlen(buf) -1] = '\0';
	// attente place libre dans la file
	sem_wait(&_Slibre);
	
	//envoie interface arrive avion
	sprintf(cmd, "%s arrive\n", buf);
	fputs(cmd, ficCmd);
	fflush(ficCmd);
  printf("envoie : %s\n", cmd);

	strcpy(_fileAtt[_indEcr], buf); // ecriture dans la file
	_indEcr = (_indEcr+1)%TAILLE_FILE;
	

	// signalement info disponible dans la file
	sem_post(&_Sinfo);
  } // fin boucle

return NULL;
}

void *consommateur(void *arg){
int indLect=0;
arg = arg; //pour supprimer le warning arg non utilisé
	char buf[512];
char* ficTO = "/tmp/takeoff";
 int fdTO;

if((fdTO = open(ficTO, O_RDONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	
  while(1) {
        // attente information dispo dans la file
	sem_wait(&_Sinfo);

  //lecture file et envoie takeoff interface
	sprintf(cmd, "%s takeOff\n", _fileAtt[indLect]);
	fputs(cmd, ficCmd);
	fflush(ficCmd);
  printf("envoie : %s\n", cmd);
	
	indLect = (indLect+1)%TAILLE_FILE;

	// signalement place libre dans la file
	sem_post(&_Slibre);
	
	//attente piste libre
	int nb;
	while((nb = read(fdTO, buf, 512)) == 0){
	//printf("read : %s\n", buf);
	}
	printf("read : %s\n", buf);

  } // fin boucle

return NULL;
}

int main(void){
pthread_t idThreadC, idThreadP;

  // ouverture fichier de commande
  if ((ficCmd = fopen(nomFicCmd, "w")) == NULL){
	perror("ouverture fichier donnee");
	exit(-1);
  }


  sem_init(&_Slibre, 0, TAILLE_FILE);
  sem_init(&_Sinfo, 0, 0);

  pthread_create(&idThreadC, NULL, consommateur, NULL);
  pthread_create(&idThreadP, NULL, producteur, NULL);

  pthread_join(idThreadP, NULL);
  
	strcpy(cmd, "end\n");
	fputs(cmd, ficCmd);
	fflush(ficCmd);
  printf("envoie : %s\n", cmd);
  
  fclose(ficCmd);

  return 0;
}
