package org.usfirst.frc.team135.robot.subsystems;

import org.usfirst.frc.team135.robot.RobotMap;
import org.usfirst.frc.team135.robot.RobotMap.COMPETITION.CAMERA;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem implements RobotMap{

    private static Camera instance;
    private NetworkTable _cameraTable;
    
    private boolean _driverMode = false;
    
    private double cameraHeight;
    
    private static final int
    	_LED_ON = 0,
    	_LED_OFF = 1,
    	_LED_BLINK = 2;
    
    private static final int
		_VISION_MODE = 0,
		_DRIVER_MODE = 1;
    
    private  NetworkTableEntry
    	_ledMode,
    	_targetVisible,
    	_xOffsetDegrees,
    	_yOffsetDegrees,
    	_targetArea,
    	_targetSkew,
    	_pipelineLatency,
    	_camMode,
    	_pipeline;

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static Camera getInstance()
	{
		if (instance == null)
		{
			instance = new Camera();
		}
		return instance;
	}
	
	private Camera()
	{	
		this._cameraTable = NetworkTableInstance.getDefault().getTable("limelight");
		
		this._ledMode = this._cameraTable.getEntry("ledMode");
		this._camMode = this._cameraTable.getEntry("camMode");
		this._pipeline = this._cameraTable.getEntry("pipeline");
		
		this._targetVisible = this._cameraTable.getEntry("tv");
    	this._xOffsetDegrees = this._cameraTable.getEntry("tx");
    	this._yOffsetDegrees = this._cameraTable.getEntry("ty");
    	this._targetArea = this._cameraTable.getEntry("ta");
    	this._targetSkew = this._cameraTable.getEntry("ts");
    	this._pipelineLatency = this._cameraTable.getEntry("tl");
    	
    	this.setDriverMode(false);
    	
    	cameraHeight = (Preferences.getInstance().getBoolean("Is Competition Bot?", true)) ? COMPETITION.CAMERA.CAMERA_HEIGHT : PRACTICE.CAMERA.CAMERA_HEIGHT;
	}
	
	public double getXOffsetDegrees()
	{
		return this._xOffsetDegrees.getDouble(0);
	}
	
	public double getYOffsetDegrees()
	{
		return this._yOffsetDegrees.getDouble(0);
	}
	
	public void setDriverMode(boolean isDriverMode)
	{
		if (isDriverMode)
		{
			this._driverMode = true;
			this._ledMode.setDouble(Camera._LED_OFF);
			this._camMode.setDouble(Camera._DRIVER_MODE);
		}
		else
		{
			this._driverMode = false;
			this._ledMode.setDouble(Camera._LED_ON);
			this._camMode.setDouble(Camera._VISION_MODE);
		}

	}
	
	public double getXDistance(double objHeight)
	{
		double theta = this.getYOffsetDegrees();
		
		double triangulationHeight = objHeight - CAMERA.CAMERA_HEIGHT;
		
		return triangulationHeight / Math.tan(theta * (Math.PI / 180));	
	}
	
	public void setTrackingMode(int pipelineNumber)
	{
		this._pipeline.setDouble(pipelineNumber);
	}
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void periodic()
    {
    	//System.out.println(this.getXDistance(FIELD.SWITCH_TAPE_MIDPOINT_HEIGHT));
    }
}

