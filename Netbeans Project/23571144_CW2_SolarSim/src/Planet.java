// FILE: Planet.java
// AUTHOR: Jordan McCann
// STUDENT ID: 23571144
// PURPOSE: To hold Planetary characteristics and behaviours, 
//          such as positioning and moving (rotating in orbit)


public class Planet {
    // LOCATION
    public double x; // X-POSITION
    public double y; // Y-POSITION
    public double z; // Z-POSITION
    public double r; // RADIUS 
    
    // TEXTURE
    public String textureLoc; // TEXTURE DIRECTORY LOCATION
    
    // ROTATION
    public double selfRotationSpeed; // SELF-ROTATION SPEED (LOCAL)
    public double orbitRadius; // RADIUS TO BE ORBITED AROUND
    public double rotation = 0; // ROTATION - POSITIVE = ANTI-CLOCKWISE, NEGATIVE = CLOCKWISE
    
    // Shape
    private StdDraw3D.Shape planetShape = null; // THE 3D ShAPE OF THE PLANET
    
    // Constructor
    /**
     * Planet Ctor 
     * Used to instantiate and assign appropriate properties to a planet
     * and initially render a 3D sphere that the planet will take shape from.
     * 
     * @param x X-Position
     * @param y Y-Position
     * @param z Z-Position
     * @param r Radius of the planet
     * @param selfRotationSpeed Rotational speed of the planet
     * @param textureLoc  Texture Image Location
     */
    public Planet(double x, double y, double z, double r, 
            double selfRotationSpeed, String textureLoc)
    {
        // Set internal values via the parameters given
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.selfRotationSpeed = selfRotationSpeed;
        this.orbitRadius = x;
        this.textureLoc = textureLoc;
        
        // Create and assign Sphere to represent the planet along with applied texture
        planetShape = StdDraw3D.sphere(x, y, z, r, textureLoc);
    }
    
    /**
     * setDistanceFromSun
     * used to position the planet after instantiation
     * @param dist The added distance from its initial X-coordinate
     */
    public void setDistanceFromSun(double dist){
        planetShape.setPosition(dist, y, z); // Set Position
        x = dist; // Update X
        orbitRadius = x; // Update Orbit Radius - which is also the orbital distance from the sun
    }
    
    /**
     * selfRotate
     * Used to rotate the planet on its local y-axis
     */
    public void selfRotate(){
        // Rotate using orbit speed + modifier to simulate each planet rotating on its Y-axis (Euler)
        planetShape.rotate(0, selfRotationSpeed + 0.25, 0);
    }
    
    /**
     * rotateInOrbit : Used to rotate the planet along it's orbital radius
     * @param rotDirection Determines which direction the rotation is done on the z-axis 
     *          true = anticlockwise 
     *          false = clockwise
     * 
     */
    public void rotateInOrbit(boolean rotDirection){
        // Decide direction via parameter given
        if(rotDirection) rotation -= selfRotationSpeed;
        else rotation += selfRotationSpeed;
        
        // Calculate X and Z coordinates and update them
        x = (orbitRadius * Math.cos(rotation));
        z = (orbitRadius * Math.sin(rotation));
        
        // Set new updated position
        // Y does not change
        planetShape.setPosition(x, y, z);
    }
    
}
