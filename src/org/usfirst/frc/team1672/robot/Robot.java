
package org.usfirst.frc.team1672.robot;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
  //  RobotDrive myRobot;
    Joystick stick, liftStick;
    //RobotDrive lifter;
  
    RobotDrive myRobot;
    Jaguar lift1 = new Jaguar(4);
	Jaguar lift2 = new Jaguar(5);
    
    Talon frontLeft = new Talon(0);
	Talon rearLeft = new Talon(1);
	Talon frontRight = new Talon(2);
	Talon rearRight = new Talon(3);
	
	Ultrasonic liftSensor = new Ultrasonic(6,7);
	
	SmartDashboard dash = new SmartDashboard();
	SendableChooser autoChooser = new SendableChooser();
	String chosenAuto;
	
	AxisCamera cam1 = new AxisCamera("10.16.72.2");
	public AxisCamera.Resolution k640x360;

	 public class autoLibrary {
		 public void runAutoRight() {
			 
		 }
		 
		 public void runAutoMiddle() {
			 
		 }
		 
		 public void runAutoLeft() {
			 
		 }
	 }
	 
	private autoLibrary autoLib; 
	
    public Robot() {
     //   myRobot.setExpiration(0.1);
        stick = new Joystick(0);
        liftStick = new Joystick(1);
       //lifter = new RobotDrive(lift1, lift2);
        myRobot = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
        myRobot.setExpiration(0.1);
        
        //THE ULTRASONIC -MUST- BE CONNECTED PROPERLY FOR THIS TO not crash the robot code
        /*
        liftSensor.setAutomaticMode(true);
      	liftSensor.setEnabled(true);
      	*/
        
        myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		
		System.out.println("Robot debug log test");
		
		autoChooser.addDefault("Right", "autoRight");
		autoChooser.addObject("Middle", "autoMiddle");
		autoChooser.addObject("Left", "autoLeft");
		SmartDashboard.putData("Autonomous Code Chooser", autoChooser);
    }
    

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	chosenAuto = (String) autoChooser.getSelected();
    	myRobot.setSafetyEnabled(false);
    	while(isAutonomous() && isEnabled()) {
    		if(chosenAuto.equals("autoRight"))
    		{
    			autoLib.runAutoRight();
    		}
    		if(chosenAuto.equals("autoMiddle"))
    		{
    			autoLib.runAutoMiddle();
    		}
    		if(chosenAuto.equals("autoLeft"))
    		{
    			autoLib.runAutoLeft();
    		}
    	}
	    /*	if(chosenAuto = "autoRight"):
	    	{
	    		autoLib.runAutoRight();
	    		break;
	    	}
	    	case("autoMiddle"):
	    	{
	    		autoLib.runAutoMiddle();
	    		break;
	    	}
	    	default:
	    	{
	    		autoLib.runAutoLeft();
	    		break;
	      	} 
      	} 
    	}*/
    }

    /**
     * Runs the motors with arcade steering.
     * */
    Thread driveThread = new Thread() {
    	public void run() {
    		myRobot.mecanumDrive_Polar(stick.getMagnitude(), stick.getDirectionDegrees(), stick.getTwist());
    		Timer.delay(0.005);
    	}
    };
   
    Thread liftThread = new Thread() {
    	public void run() {
    		lift1.set(liftStick.getY());
    		Timer.delay(0.005);
    	}
    };
    
    
    
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	/*myRobot.mecanumDrive_Polar(stick.getMagnitude(), stick.getDirectionDegrees(), stick.getTwist()); // drive with arcade style (use right stick)
        	lift1.set(liftStick.getY());
        	Timer.delay(0.005);		// wait for a motor update time */
        	driveThread.run();
        	liftThread.run();
        }
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * Runs during test mode
     */
    public void test() {
    }
}
