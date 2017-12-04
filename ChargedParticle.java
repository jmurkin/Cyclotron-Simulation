import java.util.*;
/**
 * A Class to represent a massive, electrically charged particle
 * @author Ian Bailey
 * @author Jake Murkin
 * @version 1.5
 */
public class ChargedParticle extends Particle{

	protected double charge; //the electric charge of the particle in C
    
	/**
	* The Default Constructor. Sets everything to zero.
	*
	*/
	public ChargedParticle(){
		super();
		charge=0.0;
	}
    
	/**
	* Constructor with two inputs - the charge and mass of the particle. Set everything else to zero.
	* @param mIn the mass
	* @param qIn the charge
	*/
	public ChargedParticle(double mIn, double qIn){
		super(mIn);
		charge=qIn;
		
	}
    
	/**
	*  Constructor that sets mass, charge, position and velocity
	*  @param mIn mass of the particle
	*  @param qIn charge of the particle
	*  @param positionIn initial position of particle
	*  @param velocityIn initial velocity of particle 
	*/
	public ChargedParticle(double mIn,double qIn, PhysicsVector positionIn,PhysicsVector velocityIn)
	{
		super(mIn, positionIn, velocityIn);
		charge = qIn;		
		
	}
    
	/**
	* Return the charge of the particle (in Coulombs)
	*
	* @return charge
	*/
	public double getCharge()
	{
		return charge;
	}
    
	/**
	* Set the charge of the particle (in Coulombs)
	*
	* @param chargeIn The new charge
	*/
	public void setCharge(double qIn)
	{
		charge=qIn;
	}
	
	/**
	* Update the position and velocity of the particle using the runge-kutta 4 method
	* @param fields The fields in the simulation
	* @param time the time
	* @param deltaTime  The change in time
	*/
	public void rk4(double deltaTime, ArrayList<GeneralEMField> fields, double time)
	{

		PhysicsVector k1x = new PhysicsVector(this.getVelocity());
		PhysicsVector k1v = new PhysicsVector(this.getAcceleration());
		PhysicsVector x1 = new PhysicsVector(this.getPosition());
		PhysicsVector v1 = new PhysicsVector(this.getVelocity());
		x1.increaseBy(PhysicsVector.scale((deltaTime),k1x));
		v1.increaseBy(PhysicsVector.scale((deltaTime),k1v));
		PhysicsVector k2x = new PhysicsVector(v1);
		PhysicsVector k2v = new PhysicsVector();
		
		for (GeneralEMField field : fields){
			k2v.increaseBy(field.getAcceleration(this, time+(deltaTime),v1));
		}	
		
		PhysicsVector x2 = new PhysicsVector(this.getPosition());
		PhysicsVector v2 = new PhysicsVector(this.getVelocity());
		x2.increaseBy(PhysicsVector.scale((deltaTime/2),k2x));
		v2.increaseBy(PhysicsVector.scale((deltaTime/2),k2v));
		PhysicsVector k3x = new PhysicsVector(v2);
		PhysicsVector k3v = new PhysicsVector();

		
		for (GeneralEMField field : fields){
			//k3v.increaseBy(field.getAcceleration(this, time+(deltaTime/2),v2));
		}	
		
		PhysicsVector x3 = new PhysicsVector(this.getPosition());
		PhysicsVector v3 = new PhysicsVector(this.getVelocity());
		x3.increaseBy(PhysicsVector.scale(deltaTime,k3x));
		v3.increaseBy(PhysicsVector.scale(deltaTime,k3v));
		PhysicsVector k4x = new PhysicsVector(v3);
		PhysicsVector k4v = new PhysicsVector();
		
		
		for (GeneralEMField field : fields){
			//k4v.increaseBy(field.getAcceleration(this, time+(deltaTime),v3));
		}	
		
		PhysicsVector x4 = new PhysicsVector(PhysicsVector.add(this.getPosition(), PhysicsVector.scale(deltaTime/2, PhysicsVector.add(k1x, PhysicsVector.add(PhysicsVector.scale(2,k2x), PhysicsVector.add(PhysicsVector.scale(2,k3x),k4x))))));
		PhysicsVector v4 = new PhysicsVector(PhysicsVector.add(this.getVelocity(), PhysicsVector.scale(deltaTime/2, PhysicsVector.add(k1v, PhysicsVector.add(PhysicsVector.scale(2,k2v), PhysicsVector.add(PhysicsVector.scale(2,k3v),k4v))))));
		
		
		position = x4;
		velocity = v4;
	}
    
		/**
	* Update the position and velocity of the particle using the runge-kutta 2 method
	* @param fields The fields in the simulation
	* @param time the time
	* @param deltaTime  The change in time
	*/
	public void rk2(double deltaTime, ArrayList<GeneralEMField> fields, double time)
	{

		PhysicsVector k1x = new PhysicsVector(this.getVelocity());
		PhysicsVector k1v = new PhysicsVector(this.getAcceleration());
		PhysicsVector x1 = new PhysicsVector(this.getPosition());
		PhysicsVector v1 = new PhysicsVector(this.getVelocity());
		x1.increaseBy(PhysicsVector.scale((deltaTime),k1x));
		v1.increaseBy(PhysicsVector.scale((deltaTime),k1v));
		PhysicsVector k2x = new PhysicsVector(v1);
		PhysicsVector k2v = new PhysicsVector();
		
		for (GeneralEMField field : fields){
			k2v.increaseBy(field.getAcceleration(this, time+(deltaTime),v1));
		}	
		
	
		
		PhysicsVector x2 = new PhysicsVector(PhysicsVector.add(this.getPosition(), PhysicsVector.scale(deltaTime/2, PhysicsVector.add(k1x, k2x))));
		PhysicsVector v2 = new PhysicsVector(PhysicsVector.add(this.getVelocity(), PhysicsVector.scale(deltaTime/2, PhysicsVector.add(k1v, k2v))));
		
		
		position = x2;
		velocity = v2;
	}
    
	/**
	* Create a string containing the mass, charge, position, velocity, and acceleration of the particle.
	* This method is called automatically by System.out.println(someparticle)
	* @return string with the format
	* " mass "+mass+" charge "+charge+" Position: "+position+" Velocity: "+velocity+" Acceleration: "+acceleration
	*/
	@Override
	public String toString()
	{
		return " mass "+mass+ " charge " +charge+" Position: "+position.returnSimpleString()+" Velocity: "+velocity.returnSimpleString()+" Acceleration: "+acceleration.returnSimpleString();
	}
}


