#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

	
int main(){

	char* ficCmd = "/tmp/cmd";
	char* ficEntree = "listeCmd.txt";
	char buf[512];
	int nb, fdE, fdS;
	
	if((fdE = open(ficEntree, O_RDONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier entree ouvert\n");
	if((fdS = open(ficCmd, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier commande ouvert\n");
	
	while(nb = read(fdE, buf, 512)){
		write(fdS, buf, nb);
	}
	
	close(fdE);
	close(fdS);

	return 0;

}
