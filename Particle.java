
/**
* A Class to represent a massive particle
*  It can have position, velocity, acceleration and mass
* @author  Alex Finch
* @author Ian Bailey
* @version 1.6
*/
public class Particle implements Comparable {
	// NB Private => only visible inside this class
	// Protected => visible inside this class AND any subclasses
	protected double mass; //the mass of the particle 
	protected PhysicsVector position, velocity, acceleration;
	private double defaultMass=0.0;
	
	
	/**
	* The Default Constructor. Sets everything to zero.
	*
	*/
	public Particle(){
		mass = this.defaultMass;
		position = new PhysicsVector();
		velocity = new PhysicsVector();
		acceleration= new PhysicsVector();
	}
    
	/**
	* Constructor with one input, the mass of the particle. Set everything else to zero.
	* @param mIn mass of the particle
	*/
	public Particle(double mIn){
		this();
		mass = mIn;
	}
    
	/**
	*  Constructor that sets mass, position and velocity
	*  @param mIn mass of the particle
	*  @param positionIn initial position of particle
	*  @param velocityIn initial velocity of particle 
	*/
	public Particle(double mIn,PhysicsVector positionIn,PhysicsVector velocityIn)
	{
		this(mIn);
		position = new PhysicsVector(positionIn);
		velocity = new PhysicsVector(velocityIn);
	}
    
    
	/**
	*  Copy Constructor 
	*  @param particleIn particle whose properties are to be copied to the new particle
	*/
	public Particle(Particle particleIn)
	{
		this(particleIn.mass,particleIn.position,particleIn.velocity);
		acceleration= new PhysicsVector(particleIn.acceleration);
	}
	
	/**
	*  Method to set the properties of the particle equal to those of another particle
	*  @param particleIn particle whose properties are to be copied to 'this' particle
	*/
	public void setAll(Particle particleIn)
	{
		setMass(particleIn.mass);
		setPosition(particleIn.position);
		setVelocity(particleIn.velocity);
		setAcceleration(particleIn.acceleration);
	}
	
	
	/**
	* Return the position
	*
	* @return position
	*/
	public PhysicsVector getPosition()
	{
		return new PhysicsVector(position);
	}
    
	/**
	* Return the velocity
	*
	* @return velocity
	*/
	public PhysicsVector getVelocity()
	{
		return new PhysicsVector(velocity);
	}
	
	/**
	* Return the acceleration
	*
	* @return acceleration
	*/
	public PhysicsVector getAcceleration()
	{
		return new PhysicsVector(acceleration);
	}
	
	
	/**
	* Return the mass
	*
	* @return mass
	*/
	public double getMass()
	{
		return mass;
	}
	
	/**
	* Return the kinetic energy
	*
	* @return the kinetic energy in J
	*/
	public double getKE()
	{
		double speed=velocity.magnitude();
		return 0.5*mass*speed*speed;
	}
	
	/**
	* Set the mass
	*
	* @param massIn The new mass
	*/
	public void setMass(double massIn)
	{
		mass=massIn;
	}
    
	/**
	* Set the position
	*
	* @param pIn The new position
	*/
	public void setPosition(PhysicsVector pIn)
	{
		position = new PhysicsVector(pIn);
		return;
	}
    
	
	/**
	* Set the velocity
	*
	* @param vecIn The new velocity
	*/
	public void setVelocity(PhysicsVector velocityIn)
	{
		velocity = new PhysicsVector(velocityIn);
		return;
	}
	
	/**
	* Set the acceleration
	*
	* @param accIn The new acceleration
	*/
	public void setAcceleration(PhysicsVector accIn)
	{
		acceleration= new PhysicsVector(accIn);
		return;
	}
    
	
	/**
	* Update the position and velocity of the particle subject to a constant acceleration for a time.
	* After the time has passed the acceleration reverts to its previous value.
	*                                      
	* @param deltaTime  The change in time
	* @param accelIn    The applied acceleration
	*/
	public void update(double deltaTime, PhysicsVector accelIn)
	{
		PhysicsVector savedAcceleration = acceleration;
		
		// apply the new acceleration for a short time
		acceleration = new PhysicsVector(accelIn);
		update(deltaTime);
		
		// revert acceleration to previous value 
		acceleration = savedAcceleration;
		
		return;
	}
	
	public void updateEuler1(double deltaTime, PhysicsVector accelIn)
	{
		PhysicsVector savedAcceleration = acceleration;
		
		// apply the new acceleration for a short time
		acceleration = new PhysicsVector(accelIn);
		updateEuler1(deltaTime);
		
		// revert acceleration to previous value 
		acceleration = savedAcceleration;
		
		return;
	}
	
	public void updateEuler2(double deltaTime, PhysicsVector accelIn)
	{
		PhysicsVector savedAcceleration = acceleration;
		
		// apply the new acceleration for a short time
		acceleration = new PhysicsVector(accelIn);
		updateEuler2(deltaTime);
		
		// revert acceleration to previous value 
		acceleration = savedAcceleration;
		
		return;
	}
    
	/**
	* Update the position and velocity of the particle after a short time has
	* passed when the particle is experiencing the acceleration stored in the class.
	* Applies the formula s = ut + 1/2 at**2 to the position
	* Applies the formula v=u+at to the velocity
	* @param deltaTime  The change in time
	*/
	public void update(double deltaTime)
	{
			
		updateEuler1(deltaTime);
		
		return;
		
	}
    
	
	/**
	* Update the position and velocity of the particle after a short time has
	* passed when the particle is experiencing the acceleration stored in the class.
	* Applies the formula s = ut + 1/2 at**2 to the position
	* Applies the formula v=u+at to the velocity
	* @param deltaTime  The change in time
	*/
	private void updateEuler1(double deltaTime)
	{
		position.increaseBy(PhysicsVector.scale(deltaTime, velocity)); // old position + ut
		position.increaseBy(PhysicsVector.scale(0.5*deltaTime*deltaTime, acceleration)); // + 1/2 at**2
		
		velocity.increaseBy(PhysicsVector.scale(deltaTime, acceleration)); // v = u + at
	
		return;
		
	}
	
	/**
	* Update the position and velocity of the particle after a short time has
	* passed when the particle is experiencing the acceleration stored in the class.
	* Applies the formula v=u+at to the velocity
	* Applies the formula s = ut + 1/2 at**2 to the position
	* @param deltaTime  The change in time
	*/
	private void updateEuler2(double deltaTime)
	{
		
		velocity.increaseBy(PhysicsVector.scale(deltaTime, acceleration)); // v = u + at
		position.increaseBy(PhysicsVector.scale(deltaTime, velocity)); // old position + ut
		
		return;
		
	}
	
	
	
	
	/**
	* Create a string containing the mass, position, velocity, and acceleration of the particle.
	* This method is called automatically by System.out.println(someparticle)
	* @return string with the format
	* " mass "+mass+" Position: "+position+" Velocity: "+velocity+" Acceleration: "+acceleration
	*/
	public String toString()
	{
		return " mass "+mass+" Position: "+position.returnSimpleString()+" Velocity: "+velocity.returnSimpleString()+" Acceleration: "+acceleration.returnSimpleString();
	}
	
	/**
	* Compares a Particle with position (p2) to 'this' Particle's position (p1)
	* and determines which has the largest absolute value of the 
	* y coordinate of position.
	* @return -1  if |p2|>|p1|, 0 if |p2|==|p1|, 1 otherwise
	*/
	public int compareTo(Object other) throws ClassCastException
	{
		double myY=Math.abs((this.getPosition()).getY());
		double otherY=Math.abs((((Particle)other).getPosition()).getY());
    	
		if (myY< otherY){
			return(-1);
		}
		else if (myY==otherY){
			return(0);
		}
		else
		{
			return(1);
		}			
	}
}


