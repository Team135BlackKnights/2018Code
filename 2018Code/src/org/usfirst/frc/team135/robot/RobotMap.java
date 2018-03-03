package org.usfirst.frc.team135.robot;

import jaci.pathfinder.Waypoint;

public interface RobotMap 
{
	public interface PROFILING
	{
		public static final Waypoint[] 
				
				LeftSideToNearSwitch = 
				{
					new Waypoint(0, 0, 0) //x, y, theta (in radians)			
				},
				
				RightSideToNearSwitch = 
				{
					new Waypoint(0, 0, 0)
				},
				
				MidToRightSwitch =
				{
					new Waypoint(0, 0, 0)
				},
				
				MidToLeftSwitch =
				{
					new Waypoint(0, 0, 0)
				},
				
				LeftSwitchToPyramid =
				{
			
				},
				
				PyramidToLeftSwitch =
				{
			
				},
				
				RightSwitchToPyramid =
				{
					
				},
				
				PyramidToRightSwitch = 
				{
					
				},
				
				SideToScale = 
				{
					new Waypoint(0, 0, 0)
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
			AUTO_LINE = 135, 
			WALL_SLANT_END = 10, 
			SCALE_X = 42.5f, 
			SCALE_Y = 248, 
			SIDE_SWITCH_X = 54.5f,
			SIDE_SWITCH_Y = 165, 
			MID_SWITCH_X = 105f, 
			MID_SWITCH_Y = 92.1f;
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

			public static final double LOW_POSITION = 0, SWITCH_POSITION = 500, SCALE_POSITION = 1310;

			public static final double kP = 4.5, kI = 0.045, kD = 15, kF = .2;

		}

		public interface DRIVETRAIN {
			public static final double 
			MAX_VELOCITY_TICKS_PER_100MS = 288,
		 	MAX_VELOCITY_TICKS = MAX_VELOCITY_TICKS_PER_100MS * 10, //Per second
		 	MAX_ACCELERATION_TICKS_PER_100MS = 3000,
		 	MAX_ACCELERATION_TICKS = MAX_ACCELERATION_TICKS_PER_100MS * 10,
		 	MAX_JERK_TICKS_PER_100MS = 300000,
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
			public static final int HANG_1_VICTOR_ID = 3;
			public static final int SOLENOID_PORT = 4;

		}
	}
	
	public interface COMPETITION
	{

		public interface CANIFIER {
			public static final int ID = 0;
		}

		public interface LIFT {
			public static final int LIFT_MOTOR_ID = 3;

			public static final double LOW_POSITION = 0, SWITCH_POSITION = 500, SCALE_POSITION = 1310;

			public static final double kP = .33, kI = 0.0033, kD = 3.3, kF = .15;

		}

		public interface DRIVETRAIN {
			public static final double 
				MAX_VELOCITY_TICKS_PER_100MS = 288,
			 	MAX_VELOCITY_TICKS = MAX_VELOCITY_TICKS_PER_100MS * 10, //Per second
			 	MAX_ACCELERATION_TICKS_PER_100MS = 3000,
			 	MAX_ACCELERATION_TICKS = MAX_ACCELERATION_TICKS_PER_100MS * 10,
			 	MAX_JERK_TICKS_PER_100MS = 300000,
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
	}
}