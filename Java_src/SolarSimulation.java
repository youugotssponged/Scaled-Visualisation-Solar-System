
// AUTHOR: Jordan McCann
// STUDENT ID: 23571144
// PURPOSE: To process given data and animate a fully working solar simulation 

public class SolarSimulation {
    
    // Main Entry Point
    public static void main(String[] args){
        // PROGRAM DATA
        // TEXTURE LOCATION
        // Uses getProperty to fetch the project's working directory and adds its extended folder path to the texture images
        String textureDir = System.getProperty("user.dir") + "\\src\\textures\\";
        /*
            Note:
                Hi-Res Textures
                have been sourced 
                from https://www.solarsystemscope.com/textures/
        */
        // Array to hold file names of the textures
        String[] textureName = new String[]{
            "sun.jpg",
            "mercury.jpg",
            "venus_surface.jpg",
            "earth_daymap.jpg",
            "mars.jpg",
            "jupiter.jpg",
            "saturn.jpg",
            "uranus.jpg",
            "neptune.jpg"
        };
        
        // PLANET SPEEDS
        // Array to hold planetary speeds
        // Speeds manually downscaled to mimic the original speed's within the (-1, 1) scale 
        double[] planetSpeeds = new double[]{
            0.0,
            0.0056543,
            0.0034950,
            0.002943,
            0.0023253,
            0.0013210,
            0.0009334,
            0.0006503,
            0.0005447
        };
        
        // Variables to work out the suns proportion in accordance with the earth
        double earthR = 0.0063675; // Earth Radius
        double earthD = earthR * 2; // Earth Diameter
        double sunSizeD = earthD * 109; // Sun Diameter
        double sunSizeR = sunSizeD / 2; // Sun Radius
        double earthDist = sunSizeR + 0.1; // Earth Distance
        
        // PLANET DISTANCES - FROM THE SUN
        // Array to hold orbital distances from the sun while taking into account the sun's radius
        // Distances manually downscaled to mimic the original distances within the (-1, 1) scale
        double[] planetDistancesFromSun = new double[]{
            0.0,
            (sunSizeR + 0.04),
            (sunSizeR + 0.07),
            earthDist,
            (sunSizeR + 0.15),
            (sunSizeR + 0.5),
            (sunSizeR + 0.95),
            (sunSizeR + 1.91),
            (sunSizeR + 3.0)
        };
        
        // PLANET SIZES
        // Array to hold planet sizes based off of the earth's radius using a percentage calculation
        // Percentages manually downscaled to mimic the original sizing within the (-1, 1) scale
        double[] planetSizes = new double[]{
            sunSizeR,
            (earthR * 0.38),
            (earthR * 0.95),
            earthR,
            (earthR * 0.53),
            (earthR * 11.2),
            (earthR * 9.45),
            (earthR * 4.0),
            (earthR * 3.88)
        };
        
        
        // Setup Canvas / Windowing
        StdDraw3D.fullscreen(); // Forces the canvas into fullscreen mode
        StdDraw3D.setScale(-1, 1); // Sets the scale so the Sun can be positioned at the centre 0,0
        
        // Store the camera's original position and direction
        StdDraw3D.Vector3D initalCamPos = StdDraw3D.getCameraPosition(); 
        StdDraw3D.Vector3D initialCamDir = StdDraw3D.getCameraDirection();
        
        // Setup Scene
        StdDraw3D.clearLight(); // Removes pre-inserted light
        StdDraw3D.setBackgroundSphere(textureDir + "stars.jpg"); // Sets the skybox to a stary night texture
     
        // Planet Array to hold planet objects
        Planet[] planets = new Planet[9];
       
        // Construct and Place each planet
        // For every planet
        for(int i = 0; i < planets.length; i++){
            // Create
            planets[i] = new Planet(0, 0, 0, planetSizes[i], planetSpeeds[i], textureDir + textureName[i]);
            // Place
            planets[i].setDistanceFromSun(planetDistancesFromSun[i]);
        }
        
        // Setup point light at the sun's position
        StdDraw3D.pointLight(planets[0].x, planets[0].y, planets[0].y, StdDraw3D.WHITE, 150.0);
        
        
        // Switches for changing the camera's position dynamically
        boolean changeCameraPosition = false;
        boolean shouldResetCamPos = true;
        
        // Animation Loop
        while(true){

            // Check if the spacebar is pressed
            if(StdDraw3D.isKeyPressed(32)){
                // Flip boolean on each press
                changeCameraPosition = changeCameraPosition != true;
            }
            
            // If the camera should return to its initial positon and orbit mode
            if(!changeCameraPosition && shouldResetCamPos){
                StdDraw3D.setCameraMode(StdDraw3D.ORBIT_MODE); // Set Mode
                StdDraw3D.setCameraPosition(initalCamPos); // Set Position
                StdDraw3D.setCameraDirection(initialCamDir); // Set Direction
                
                shouldResetCamPos = false; // Lock - stopping constant repositioning
            }
            
            // For Every Planet
            for(int i = 0; i < planets.length; i++){
                planets[i].selfRotate(); // Rotate on its local Y to simulate spinning
                planets[i].rotateInOrbit(false); // Rotate through the orbit angle given
                
                // If the camera's position should change
                if(changeCameraPosition){
                    StdDraw3D.setCameraMode(StdDraw3D.FPS_MODE); // Change mode to FPS
                    StdDraw3D.setCameraPosition(planets[3].x + planetSizes[3], planets[3].y + 0.01, planets[3].z); // Reposition to slighly above the earth's equator
                    
                    shouldResetCamPos = true; // Reset CamPos boolean
                }
            }
            
            // Show every frame - 20ms
            StdDraw3D.show(20);
        }
    }
}
