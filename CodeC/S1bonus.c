#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

	
int main(){

	char* ficCmd = "/tmp/cmd";
	char* ficEntree = "/tmp/posPerso";
	char instruction[20], buf[512];
	int nb, fdE, fdS;
	char nomPerso[20];
	int x, y;
	int xGoal = 8;
	int yGoal = 7;
	
	if((fdE = open(ficEntree, O_RDONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier entree ouvert\n");
	
	nb = read(fdE, buf, 512);
	char* p = strtok(buf, " ");
	strcpy(nomPerso, p);
	p= strtok(NULL, " ");
	printf("x : %s\n", p);
	x = atoi(p);
	p = strtok(NULL, " ");
	y = atoi(p);
	
	printf("position de %s en %d - %d\n", nomPerso, x, y);
	
	close(fdE);
	
	if((fdS = open(ficCmd, O_WRONLY)) == -1){
		perror("ouverture fifo ecriture : ");
		return 0;
	};
	printf("fichier commande ouvert\n");
	
	while(x != xGoal){
		instruction[0] = '\0';
		strcat(instruction, nomPerso);
		if(x < xGoal){
			strcat(instruction, " r\n");
			x++;
		}else{
			strcat(instruction, " l\n");
			x--;
		}
		write(fdS, instruction, strlen(instruction));
		//printf("message envoyé : %s", instruction, strlen(instruction));
	}
	
	while(y != yGoal){
		instruction[0] = '\0';
		strcat(instruction, nomPerso);
		if(y < yGoal){
			strcat(instruction, " d\n");
			y++;
		}else{
			strcat(instruction, " u\n");
			y--;
		}
		write(fdS, instruction, strlen(instruction));
		//printf("message envoyé : %s", instruction);
	}
	
	instruction[0] = '\0';
	strcat(instruction, "end\n");
	write(fdS, instruction, strlen(instruction));
	
	close(fdS);

	return 0;

}
