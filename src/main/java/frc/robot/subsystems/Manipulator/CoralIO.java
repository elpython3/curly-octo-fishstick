package frc.robot.subsystems.Manipulator;

import org.littletonrobotics.junction.AutoLog;

public interface CoralIO {
    @AutoLog
    public static class CoralIOInputs{
        public boolean proximity1;
        public boolean proximity2;
        public double motor1speed;
        public double motor2speed;
    }

    public default void updateInputs(CoralIOInputs inputs){}

    public default void spinMotors(double speed){}

    public default void stopMotors(){}

    public boolean entered(); //both sensors see

    public boolean seen(); //first sensor sees


}
