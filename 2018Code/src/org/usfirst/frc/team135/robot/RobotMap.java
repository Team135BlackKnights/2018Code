public interface RobotMap 
{
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
			static final public int REAR_RIGHT_TALON_ID = 2;
			static final public int FRONT_RIGHT_TALON_ID = 5;
			static final public int REAR_LEFT_TALON_ID = 1;
			static final public int FRONT_LEFT_TALON_ID = 4;
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
			public static final int ID = 1;
		}

		public interface LIFT {
			public static final int LIFT_MOTOR_ID = 3;

			public static final double LOW_POSITION = 0, SWITCH_POSITION = 500, SCALE_POSITION = 1310;

			public static final double kP = .33, kI = 0.0033, kD = 3.3, kF = .15;

		}

		public interface DRIVETRAIN {
			static final public int REAR_RIGHT_TALON_ID = 4;
			static final public int FRONT_RIGHT_TALON_ID = 5;
			static final public int REAR_LEFT_TALON_ID = 1;
			static final public int FRONT_LEFT_TALON_ID = 2;
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