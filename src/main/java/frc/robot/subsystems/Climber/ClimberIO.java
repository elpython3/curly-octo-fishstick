package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
    @AutoLog
    public static class ClimberIOInputs{
        public double pos = 0.0;
        public double speed = 0.0;
        public double voltage = 0.0;
        public double current = 0.0;
    }

    public default void stopMotor(){}

    public default void moveIn(){}

    public default void moveOut(){}

    public default void updateInputs(ClimberIOInputs inputs){}
}

