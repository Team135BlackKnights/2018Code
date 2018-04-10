package org.usfirst.frc.team135.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public interface RobotMap 
{
	
	public enum STRAFE_MODE
	{
		REDUCE,
		GAIN
	}
	public interface AUTO
	{
		public static String msg = "";
	}
	public interface PROFILING
	{
		public static final Waypoint[] 
				
				MID_TO_RIGHT_SWITCH =
				{
					new Waypoint(0, 0, 0),
					new Waypoint(0.288 * CONVERSIONS.INCHES2METERS, 11.167 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(357.04)),
					new Waypoint(1.163 * CONVERSIONS.INCHES2METERS, 22.333 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(353.98)),
					new Waypoint(2.660 * CONVERSIONS.INCHES2METERS, 33.500 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(350.71)),
					new Waypoint(4.847 * CONVERSIONS.INCHES2METERS, 44.667 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(347.07)),
					new Waypoint(7.839 * CONVERSIONS.INCHES2METERS, 55.833 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(342.82)),
					new Waypoint(11.845 * CONVERSIONS.INCHES2METERS, 67.000 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(337.51)),
					new Waypoint(17.279 * CONVERSIONS.INCHES2METERS, 78.167 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(330.20)),
					new Waypoint(25.206 * CONVERSIONS.INCHES2METERS, 89.333 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(318.07)),
					new Waypoint(46.516 * CONVERSIONS.INCHES2METERS, 100.5 * CONVERSIONS.INCHES2METERS, Pathfinder.d2r(270.00))
				},
				
				MID_TO_LEFT_SWITCH =
				{
					new Waypoint(0, 0, 0),
					new Waypoint(-0.011 * CONVERSIONS.INCHES2METERS, 26.834 * CONVERSIONS.INCHES2METERS, 0),
					new Waypoint(-3.966 * CONVERSIONS.INCHES2METERS, 66.548 * CONVERSIONS.INCHES2METERS, 6.0621),
					new Waypoint(-15.511 * CONVERSIONS.INCHES2METERS, 106.938 * CONVERSIONS.INCHES2METERS, 5.85),
					new Waypoint(-25.547 * CONVERSIONS.INCHES2METERS, 117.430 * CONVERSIONS.INCHES2METERS, 5.05),
					new Waypoint(-42.938 * CONVERSIONS.INCHES2METERS, 120.188 * CONVERSIONS.INCHES2METERS, 3.14),
				};
	}
	
	public interface CONVERSIONS
	{
		public static final double
			INCHES2METERS = 0.0254, //meters/inch
			TICKS2INCHES = 0.0704, //inches/tick
			INCHES2TICKS = 1 / TICKS2INCHES, //ticks/inch
			TICKS2METERS = TICKS2INCHES * INCHES2METERS,		//rev/inches * inches/tick = REV/TICK
			TICKS2REVS = (1 / (COMPETITION.DRIVETRAIN.WHEEL_DIAMETER * Math.PI)) * TICKS2INCHES,
			REVS2TICKS = 1 / TICKS2REVS,
			TICKS2RADIANS = TICKS2REVS * (2 * Math.PI), //Revs/tick * radians/rev = radians/tick
			RADIANS2TICKS = 1 / TICKS2RADIANS,
			TICKS2DEGREES = TICKS2REVS * 360, //Revs/tick * degrees/rev
			DEGREES2TICKS = 1 / TICKS2DEGREES;
	}
	public interface FIELD {
		public static final double // All measurements are in inches
			AUTO_LINE = 70, 
			WALL_SLANT_END = 10, 
			SIDE_SCALE_X = 37.5f, 
			SIDE_SCALE_Y = 326 - 48, 
			SIDE_SWITCH_X = 51f,
			SIDE_SWITCH_Y = 120, 
			MID_SWITCH_X = 105f, 
			MID_SWITCH_Y = 160,
			FAR_SCALE_X = 189f,
			FAR_SCALE_Y = 17.5,
			FAR_SWITCH_X = 160,
			
			FAR_SCALE_DISTANCE_FROM_WALL = 222,
			FAR_SCALE_DISTANCE_TO_SCALE = 200;
	}
	
	public interface DIRECTION {
		public static final int 
			FORWARD = 1, 
			BACKWARD = -1, 
			RIGHT = 1, 
			LEFT = -1, 
			CLOCKWISE = 1,
			COUNTERCLOCKWISE = -1, 
			NEUTRAL = 0;
	}
	
	public interface PRACTICE
	{

		public interface CANIFIER {
			public static final int ID = 1;
		}

		public interface LIFT {
			public static final int LIFT_MOTOR_ID = 3;

			public static final double LOW_POSITION = 0, SWITCH_POSITION = 650, SCALE_POSITION = 1410;

			public static final double kP = 0.04, kI = 0.0, kD = 0, kF = 0.33;

		}

		public interface DRIVETRAIN {
			public static final double 
			MAX_VELOCITY_TICKS_PER_100MS = 225,
			MAX_VELOCITY_TICKS = MAX_VELOCITY_TICKS_PER_100MS * 10, //Per second
			MAX_ACCELERATION_TICKS_PER_100MS = 1090,
			MAX_ACCELERATION_TICKS = MAX_ACCELERATION_TICKS_PER_100MS  * 10,
			MAX_JERK_TICKS_PER_100MS = 40000,
			MAX_JERK_TICKS = MAX_JERK_TICKS_PER_100MS * 10;
		
		public static final double
			WHEEL_DIAMETER = 6, //Inches
			TRACK_WIDTH = 22.626; //Inches
			
			public static final int REAR_RIGHT_TALON_ID = 2;
			public static final int FRONT_RIGHT_TALON_ID = 5;
			public static final int REAR_LEFT_TALON_ID = 1;
			public static final int FRONT_LEFT_TALON_ID = 4;
		}

		public interface INTAKE {
			public static final int RIGHT_WHEEL_VICTOR_ID = 1;
			public static final int LEFT_WHEEL_VICTOR_ID = 2;

			public static final int MANDIBLE_OPEN_CHANNEL = 0;
			public static final int MANDIBLE_CLOSE_CHANNEL = 1;

			public static final int RETRACT_IN_CHANNEL = 2;
			public static final int RETRACT_OUT_CHANNEL = 3;
		}

		public interface HANG {
			public static final int HANG_1_VICTOR_ID = 4;
			public static final int SOLENOID_PORT = 6;

		}
		
		public interface CAMERA
		{

			public static final int
				REFLECTIVE_TAPE_MODE = 0,
				POWER_CUBE_MODE = 1;
		
			public static final double
				CAMERA_HEIGHT = 8.625; //In inches;
			
		}
		
	}
	
	public interface COMPETITION
	{

		public interface CANIFIER {
			public static final int ID = 0;
		}

		
		public interface LIFT {
			public static final int LIFT_MOTOR_ID = 3;

			public static final double LOW_POSITION = 0, SWITCH_POSITION = 650, SCALE_POSITION = 1460;

			public static final double kP = .04, kI = 0.0, kD = .4, kF = .4;

		}

		public interface DRIVETRAIN {
			public static final double 
				MAX_VELOCITY_TICKS_PER_100MS = 285,
				MAX_VELOCITY_TICKS = MAX_VELOCITY_TICKS_PER_100MS * 10, //Per second
				MAX_ACCELERATION_TICKS_PER_100MS = 1090,
				MAX_ACCELERATION_TICKS = MAX_ACCELERATION_TICKS_PER_100MS  * 10,
				MAX_JERK_TICKS_PER_100MS = 40000,
				MAX_JERK_TICKS = MAX_JERK_TICKS_PER_100MS * 10;
			
			public static final double
				WHEEL_DIAMETER = 6, //Inches
				TRACK_WIDTH = 22.626; //Inches
			
			public static final int REAR_RIGHT_TALON_ID = 4;
			public static final int FRONT_RIGHT_TALON_ID = 5;
			public static final int REAR_LEFT_TALON_ID = 1;
			public static final int FRONT_LEFT_TALON_ID = 2;
		}

		public interface INTAKE {
			public static final int RIGHT_WHEEL_VICTOR_ID = 1;
			public static final int LEFT_WHEEL_VICTOR_ID = 2;

			public static final int MANDIBLE_OPEN_CHANNEL = 0;
			public static final int MANDIBLE_CLOSE_CHANNEL = 1;

			public static final int RETRACT_IN_CHANNEL = 2;
			public static final int RETRACT_OUT_CHANNEL = 3;
		}

		public interface HANG {
			public static final int HANG_1_VICTOR_ID = 3;
			public static final int SOLENOID_PORT = 4;

		}

		public interface CAMERA
		{

			public static final int
				REFLECTIVE_TAPE_MODE = 0,
				POWER_CUBE_MODE = 1;
		
			public static final double
				CAMERA_HEIGHT = 8.625; //In inches;

			public static final double INVALID_ANGLE = -50;
			
		}
	}
}