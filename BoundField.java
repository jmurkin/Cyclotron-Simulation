public class BoundField extends GeneralEMField{
	/**
	* Represents an arbitrary electromagnetic field which is only active in a certain region.  SI units are used throughout.
	*
	* @author Jake Murkin
	* @version 1.0
	*/	
	protected double xBounds, yBounds, zBounds;
	protected GeneralEMField otherField;
	
	/**
 	*  Constructor with four inputs - the field and where its bound in all 3 spacial dimensions
 	*
 	* @param Field The field being bound
 	* @param x the absolute value of x such that the field is active
	* @param y the absolute value of y such that the field is active
	* @param z the absolute value of z such that the field is active
 	*/
	public BoundField(GeneralEMField Field, double x, double y, double z){
 		xBounds = x;
		yBounds = y;
		zBounds = z;
 		this.otherField = Field;
 	}
	
	/**
 	*  Constructor with three inputs - the field and where its bound in the x and y direction
 	*
 	* @param Field The field being bound
 	* @param x the absolute value of x such that the field is active
	* @param y the absolute value of y such that the field is active
 	*/
	public BoundField(GeneralEMField Field, double x, double y){
		xBounds = x;
		yBounds = y;
		zBounds = 0;
		this.otherField = Field;
	}
	
	/**
 	*  Constructor with one input - the field being bound
 	*
 	* @param Field The field being bound
 	*/
	public BoundField(GeneralEMField Field){
		xBounds = 0;
		yBounds = 0;
		zBounds = 0;
		this.otherField = Field;
	}
	
	/**
 	*  Set the x bounds of the field such that it is active
 	*
 	* @param x the absolute value of x such that the field is active
 	*/
	public void setxBounds(double x){
 		xBounds = x;
 	}
	
	/**
 	*  Set the y bounds of the field such that it is active
 	*
 	* @param y the absolute value of x such that the field is active
 	*/
	public void setyBounds(double y){
 		yBounds = y;
 	}
	
	/**
 	*  Set the z bounds of the field such that it is active
 	*
 	* @param z the absolute value of x such that the field is active
 	*/
	public void setzBounds(double z){
 		zBounds = z;
 	}
	
	/**
 	*  Get the absolute distance in the x direction that the field is active
 	*
 	* @return the abs value of x that the field is active
 	*/
	public double getxBounds(){
		return xBounds;
	}
	/**
 	*  Get the absolute distance in the y direction that the field is active
 	*
 	* @return the abs value of y that the field is active
 	*/
	public double getyBounds(){
		return yBounds;
	}
	
	/**
 	*  Get the absolute distance in the z direction that the field is active
 	*
 	* @return the abs value of z that the field is active
 	*/
	public double getzBounds(){
		return zBounds;
	}
	
	/**
 	*  Return the electric field strength
 	*
 	* @return The current value of the electric field strength
 	*/
	public PhysicsVector getElectric(Particle aParticle, double t){
		return otherField.getElectric(aParticle, t);
	}
 	
 	/**
 	*  Get the magnetic flux density being experienced by a particle
 	*
 	* @return The magnetic flux density
 	*/
 	public PhysicsVector getMagnetic(Particle aParticle, double t){
		return otherField.getMagnetic(aParticle, t);
	}
 	
 	
 	/**
 	*  Return the electric field strength at a position
 	*
 	* @return The electric field strength 
 	*/
 	public  PhysicsVector getElectric(PhysicsVector aPosition, double t){
		return otherField.getElectric(aPosition, t);
	}
 	
 	/**
 	*  Get the magnetic flux density being experienced by a particle
 	*
 	* @return The magnetic flux density
 	*/
 	public PhysicsVector getMagnetic(PhysicsVector aPosition, double t){
		return otherField.getMagnetic(aPosition, t);
	}
 	/**
 	*  Get the potential energy of a particle due to the field
 	*
 	* @return The potential energy in J
 	*/
 	public double getPotentialE(ChargedParticle aParticle, double t){
		return otherField.getPotentialE(aParticle, t);
	}
 	
 	/**
 	* Returns the acceleration experienced by a charged particle according to the Lorentz force law (non-relativistic)
	* if the particle is in range of the bound field
 	* @param theParticle - the charged particle moving in the field
 	* @param t - time
	* @return the acceleration calculated from (qE + vXB / m) if in range
	* @return 0 if out of range
 	*/
 	public PhysicsVector getAcceleration(ChargedParticle theParticle, double t)
 	{
 		if(theParticle.getPosition().getY() <= yBounds && theParticle.getPosition().getY() >= -yBounds && theParticle.getPosition().getX() <= xBounds && theParticle.getPosition().getX() >= -xBounds)
 		{
			return otherField.getAcceleration(theParticle, t);
		}else{
			return new PhysicsVector();
		}
 	}
	
	
	/**
 	* Returns the acceleration experienced by a charged particle according to the Lorentz force law (non-relativistic)
	* if the particle is in range of the bound field
 	* @param theParticle - the charged particle moving in the field
 	* @param t - time
	* @param velocity - the velocity of the particle
	* @return the acceleration calculated from (qE + vXB / m) if in range
	* @return 0 if out of range
 	*/
	public PhysicsVector getAcceleration(ChargedParticle theParticle, double t, PhysicsVector velocity)
 	{
 		if(theParticle.getPosition().getY() <= yBounds && theParticle.getPosition().getY() >= -yBounds && theParticle.getPosition().getX() <= xBounds && theParticle.getPosition().getX() >= -xBounds)
 		{
			return otherField.getAcceleration(theParticle, t, velocity);
		}else{
			return new PhysicsVector();
		}
 	}
	
	
	
}