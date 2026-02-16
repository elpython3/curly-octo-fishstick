package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
    
    @AutoLog
    public static class LogIn{
        public double voltage = 0.0;
        public double current = 0.0;
        public double velocity = 0.0;
        public double pos = 0.0;
    }
    
    public default void updateInputs(LogIn logger){ }

    public default void turningMotor(){ }

    public default void stopMotor() {}

    public default void setVolts(double volts_V, double ff_V) {}

    public default void setVelocity(double velocity_rps) {}

    public default void setPosition(double position_deg) {}


} 