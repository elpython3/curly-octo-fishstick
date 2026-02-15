package frc.robot.subsystems.coralManipulator;

import org.littletonrobotics.junction.AutoLog;



public interface CoralManipulatorIO {
    @AutoLog
    public static class LogInputs {
        public double motor1_A = 0.0;
        public double motor1_V = 0.0;
        public double motor1_vel = 0.0;
        
        public double motor2_V = 0.0;
        public double motor2_A = 0.0;
        public double motor2_vel = 0.0;

        public boolean sensor1Input = false;
        public boolean sensor2Input = false;
    }

    public default void updateInputs(LogInputs inputs){}
    public default void spin(double speed){}
    public default void stop(){}
}
