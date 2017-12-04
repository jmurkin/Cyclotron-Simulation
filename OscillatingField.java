import java.lang.Math;

public class OscillatingField extends GeneralEMField{
	/**
	* Represents an arbitrary electromagnetic field which oscillates.  SI units are used throughout.
	*
	* @author Jake Murkin
	* @version 1.0
	*/
	protected double frequency, phase;
	protected GeneralEMField otherField;
	
	/**
 	*  Constructor with two inputs - the field and the frequency at which it oscillates
 	*
 	* @param Field The oscillating field
 	* @param frequency The frequency at which the field oscillates
 	*/
	public OscillatingField(GeneralEMField Field, double freq){
 		frequency = freq;
 		this.otherField = Field;
		phase = 0;
 	}
	
	/**
 	*  Constructor with three inputs - the field, the frequency at which it oscillates and the phase angle
 	*
 	* @param Field The oscillating field
 	* @param frequency The frequency at which the field oscillates
	* @param p the phase angle
 	*/
	public OscillatingField(GeneralEMField Field, double freq, double p){
 		frequency = freq;
 		this.otherField = Field;
		phase = p;
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
 	* Returns the acceleration experienced by a charged particle according to the Lorentz force law (non-relativistic).
	* as its an oscillating field it takes into account the frequency
 	* @param theParticle - the charged particle moving in the field
 	* @param t - Time
	* @return the acceleration calculated from (qE + vXB / m)
 	*/
 	public PhysicsVector getAcceleration(ChargedParticle theParticle, double t)
 	{
 		PhysicsVector electric = otherField.getElectric(theParticle, t);
		electric.scale(Math.sin(frequency*t));
 		PhysicsVector magnetic = otherField.getMagnetic(theParticle, t);
		magnetic.scale(Math.sin(frequency*t));
 		PhysicsVector lorentz=new PhysicsVector(electric); // E
 		lorentz.increaseBy(PhysicsVector.cross(theParticle.getVelocity(),magnetic)); //+ v cross B
 		lorentz.scale(theParticle.getCharge()/theParticle.getMass()); // multiply by charge divided by mass
		return lorentz;
 		
 	}
	
	/**
 	* Returns the acceleration experienced by a charged particle according to the Lorentz force law (non-relativistic).
	* as its an oscillating field it takes into account the frequency
 	* @param theParticle - the charged particle moving in the field
 	* @param t - Time
	* @param velocity - the velocity of the particle
	* @return the acceleration calculated from (qE + vXB / m)
 	*/
	public PhysicsVector getAcceleration(ChargedParticle theParticle, double t, PhysicsVector velocity)
 	{
 		PhysicsVector electric = this.getElectric(theParticle, t);
		electric.scale(Math.sin((frequency*t) + phase));
 		PhysicsVector magnetic = this.getMagnetic(theParticle, t);
		magnetic.scale(Math.sin((frequency*t)) + phase);
 		PhysicsVector lorentz=new PhysicsVector(electric); // E
 		lorentz.increaseBy(PhysicsVector.cross(velocity,magnetic)); //+ v cross B
 		lorentz.scale(theParticle.getCharge()/theParticle.getMass()); // multiply by charge divided by mass
		return lorentz;
 		
 	}
}