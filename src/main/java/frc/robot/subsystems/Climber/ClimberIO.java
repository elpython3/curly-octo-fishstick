package frc.robot.subsystems.Climber;

public interface ClimberIO {
    @AutoLog
    public static class LogInputs{
        public double voltage = 0.0;
        public double velocity = 0.0;
        public double current = 0.0;
        public double position = 0.0;
    } 
    public default void updateInputs(LogInputs inputs){}
    public default void turn(double angle) {}
    public default void goToEncoder() {}
    public default void stop() {}
    public default void setVoltage(double volts, double feedForward){} 

}
