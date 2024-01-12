package main;

public class Flight {

	int time[] = new int[4];
	char destination[] = new char[10];
	char flightRef[] = new char[7];
	int gate[] = new int[2];
	char remark[] = new char[10];
	
	public void setTime(String stringTime) {
		for(int i = 0; i < stringTime.length(); i++) {
			this.time[i] = (int)stringTime.charAt(i);
		}
		for(int i = stringTime.length(); i < this.time.length; i++) {
			this.time[i] = ' ';
		}
	}

	public void setDestination(String destination) {
		for(int i = 0; i < destination.length(); i++) {
			this.destination[i] = destination.charAt(i);
		}
		for(int i = destination.length(); i < this.destination.length; i++) {
			this.destination[i] = ' ';
		}
	}
	
	public void setFlightRef(String flightRef) {
		for(int i = 0; i < flightRef.length(); i++) {
			this.flightRef[i] = flightRef.charAt(i);
		}
		for(int i = flightRef.length(); i < this.flightRef.length; i++) {
			this.flightRef[i] = ' ';
		}
	}
	
	public void setGate(String stringGate) {
		for(int i = 0; i < stringGate.length(); i++) {
			this.gate[i] = (int)stringGate.charAt(i);
		}
		for(int i = stringGate.length(); i < this.gate.length; i++) {
			this.gate[i] = ' ';
		}
	}
	
	public void setRemark(String remark) {
		for(int i = 0; i < remark.length(); i++) {
			this.remark[i] = remark.charAt(i);
		}
		for(int i = remark.length(); i < this.remark.length; i++) {
			this.remark[i] = ' ';
		}
	}

	public char[] flightToText() {
		char result[] = new char[View.nbCharText];
		//par defaut des espaces
		for(int i =0; i < result.length; i++) {
			result[i] = ' ';
		}
		
		//time cases 1 à 6 (: case 3)
		for(int i = 0; i < 2; i++) {
			result[i+1] = (char)time[i];
		}
		result[3] = ':';
		for(int i = 0; i < 2; i++) {
			result[i+4] = (char)time[i+2];
		}
		
		//destination cases 8 à 17
		for(int i = 0; i < destination.length; i++) {
			result[i+8] = destination[i];
		}
		
		//flight cases 20 à 26
		for(int i = 0; i < flightRef.length; i++) {
			result[i+20] = flightRef[i];
		}

		//time cases 29 et 30
		for(int i = 0; i < gate.length; i++) {
			result[i+29] = (char)gate[i];
		}
		
		//remark cases 34 à 43
		for(int i = 0; i < remark.length; i++) {
			result[i+34] = remark[i];
		}
		
		return result;
	}
	
}
