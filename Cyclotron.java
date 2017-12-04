import java.lang.Math;
import java.util.*;
import java.io.*;


public class Cyclotron  {
        /**
        * Main method to simulate the motion of a charged particle in a cyclotron
        *
        */
	 static double timeStep=0.00001; // time step in seconds
   	 public static void main (String[] args) throws IOException
   	 {
		
		//simulation controls
		int method = 0; //variable which stores the type of method being used, 1 is Euler method and 2 is Euler-Cromer
		double time=0.0; // set simulation time
		double timeMeasure = 100;
		double oldDisplacement=0.0;
		double displacement=0.0;
		boolean converge=false; // flag to indicate whether proton is approaching or diverging from the origin
		int nRev=0; // number of orbits the proton completes
		double timeStep=0.00001; // time step in seconds
   	 	double maxTime=100000000; // maximum simulation time in seconds
   	 	int maxRev=10; // number of orbital revolutions for proton
		char rDist='U'; // the random distribution to use (U for uniform, G for Gaussian)
		String FileName = (" "); //variable for name of output file (gets changed later)
		double phase = (Math.PI); //phase angle of the oscillating field
		boolean inZone = false; //boolean to check if the bunch is in the accelerating field
		double kE1 = 0;
		double kE2 = 0; //variables to store the bunches kE at the start and end of accelerating field
		double totalKE = 0;
		double timesCrossed = 0;//number of times the bunch has crossed the gap
		
		
		//bunch parameters
		final double pMass=1.67262178E-27; // proton mass in kg
   	 	final double pCharge=1.60217657e-19; // proton charge in Coulombs
		int nProtons = 15;
		double pSpeed = 1.0e-3; // initial speed of proton in ms^-1
   	 	double Mag=1.0e-7; // magnetic flux density in Tesla
		double radius=pMass*pSpeed/(pCharge*Mag); //calculates the radius of curvature 
		PhysicsVector pDirn = new PhysicsVector(0,1,0); // direction of bunch at start of simulation
   	 	PhysicsVector pDirnSpread=new PhysicsVector(0,0,0); // relative spread in the direction of the bunch at the start of the simulation
   	 	PhysicsVector pOrigin= new PhysicsVector(); // start the proton at the origin
   	 	PhysicsVector pSpread = new PhysicsVector(1*radius/100, 1*radius/100, 0); // absolute spread in position of particles in bunch
		double frequency = (pCharge*Mag)/pMass; //Cyclotron Frequency
		double pEnergy = 0.5*pMass*(pSpeed)*(pSpeed); // initial average energy of particles in bunch (non-relativistic)
   	 	double pESpread= 0.00*pEnergy; // absolute spread in energy of particles in the bunch
   	 	double L = 0.05*radius; //Region where the E field is active
		
		
		//create cyclotron fields
		ArrayList<GeneralEMField> cyclotron = new ArrayList<GeneralEMField>();
		
		GeneralEMField theBField = new EMField(new PhysicsVector(), new PhysicsVector(0,0,Mag)); //creates the B-Field
		cyclotron.add(theBField);
		
		GeneralEMField theEField = new EMField(new PhysicsVector(0,Mag,0), new PhysicsVector()); //creates the E-Field
		GeneralEMField EOscField = new OscillatingField(((GeneralEMField)theEField), frequency, phase); //makes the E-field oscillate
		GeneralEMField EOscBndField = new BoundField(((GeneralEMField)EOscField), 999, L); //makes the E-field bound
		cyclotron.add(EOscBndField);
		
		//User input
		Scanner sc = new Scanner(System.in);
		while(method!=1 && method!=2 && method!=3){ //Gets input from the user depending on what method of updating the proton they want to use
			System.out.println("Input 1 to use Euler method"); 
			System.out.println("Input 2 to use Euler-Cromer method.");
			System.out.println("Input 3 to use Runge-Kutta 4 method.");
			method = sc.nextInt();
			if(method == 1){
				System.out.println("Euler method selected.");
			}else if(method == 2){
				System.out.println("Euler-Cromer method selected.");
			}else if(method == 3){
				System.out.println("Runge-Kutta 4 selected");
			}else{
				System.out.println("Please select a number between 1 or 3.");
			}
   	 	}
		
		//Create File
		if(method ==1){
			FileName = ("plotEuler.csv");
		}else if(method ==2){
			FileName = ("plotEulerCrom.csv");
		}else{
			FileName = ("plotRK4.csv");
		}
		
		File file = new File(FileName);
		PrintWriter output = new PrintWriter(file);
		
		//create bunch
		ChargedParticle proton = new ChargedParticle(pMass, pCharge);
		Bunch<ChargedParticle> pBunch = new Bunch<ChargedParticle>();
		for (int i=1; i<=nProtons; i++){
   	 		pBunch.addParticle(new ChargedParticle(pMass, pCharge));
   	 	}
		
		pBunch.setDist(rDist);
   	 	pBunch.setPosition(pOrigin,pSpread);
   	 	pBunch.setVelocity(pDirn,pDirnSpread,pEnergy,pESpread);
   	 	System.out.println(pBunch);
		
		
		
		//Running the simulation
		while(nRev<maxRev){// Loop over time
			time+=timeStep;
			
			Iterator<ChargedParticle> bunchIt = pBunch.iterator();
			while(bunchIt.hasNext()){
			 	ChargedParticle aParticle=bunchIt.next();
			
				PhysicsVector acceleration = new PhysicsVector();
			 	// Loop over all fields
				for (GeneralEMField field : cyclotron){
					acceleration.increaseBy(field.getAcceleration(aParticle, time));
				}	
			
				if(method ==1){
					aParticle.updateEuler1(timeStep, acceleration);
				}else if(method ==2){
					aParticle.updateEuler2(timeStep, acceleration);
					}
				else{
					aParticle.rk4(timeStep, cyclotron, time);
				}
			
			}	  
		    
			checkVelocity(pBunch, L, timeStep);
			
			//Check to see if bunch is in gap and check for the change in KE between start and end
			if(inZone == false){
				if(pBunch.getPosition().getY() <= L && pBunch.getPosition().getY() >= -L){
					inZone = true;
					kE1 = pBunch.getTotalKE();
				}
			}
			
			if(inZone == true){
				if(pBunch.getPosition().getY() >= L || pBunch.getPosition().getY() <= -L){
					inZone = false;
					timesCrossed = timesCrossed + 1;
					kE2 = pBunch.getTotalKE();
					totalKE = totalKE + Math.abs(kE2-kE1);
					System.out.println("Bunch has crossed gap: " + timesCrossed  + " times");
					System.out.println("Difference in KE between start and end of gap: " + Math.abs(kE2-kE1) + "J");
					System.out.println("\n");
				}
			}
			
			
			displacement=(PhysicsVector.subtract(pBunch.getPosition(), pOrigin)).magnitude();
		    	if (displacement>=oldDisplacement){
		    		if (converge){
		    			// passed through closest approach and is now diverging from origin
		    	   	   	nRev+=1;
		    	   	   
		    	   	}
		    	   	converge=false;
		    	}
		    	else{
		    	   	converge=true; // proton is approaching origin
		    	}
		    oldDisplacement=displacement;
				
			if((int)(time/timeStep) % timeMeasure == 0){ //outputs average position after a certain number of iterations to not spam data
				output.println(pBunch.getPosition().getX() + "," + pBunch.getPosition().getY());
				output.flush();
			}
		}
		System.out.println("Average difference in KE: " + (totalKE /timesCrossed) + "J");
		System.out.println("Spread in KE " + pBunch.getSpreadKE() + "J");
		System.out.println("Ratio of KeSpread to final KE " + (pBunch.getSpreadKE()/pBunch.getAvgKE()));
		
	 }
	 

	 
	 /**
	* check to see if how fast the bunch is moving
	* if its moving too fast i.e it will skip over the gap
	* then lower the time step so it the bunch can be found in the gap
	* @param deltaTime  The change in time
	* @param Bunch The bunch of protons
	* @param Width the width of the gap
	*/
	 public static void checkVelocity(Bunch<ChargedParticle> Bunch, double Width, double deltaTime){
		 double speed = Bunch.getVelocity().magnitude();
		 if(Width/speed < 1*deltaTime){
			 timeStep = timeStep / 2;
		 }
	 }
 }

