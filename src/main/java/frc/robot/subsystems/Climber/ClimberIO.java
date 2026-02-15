package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
  @AutoLog
  public static class ClimberIOInputs {
    public double positionRotations = 0.0;
    public double velocityRps = 0.0;
    public double appliedVolts = 0.0;
    public double[] currentAmps = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ClimberIOInputs inputs) {}

  /** Run the motor at a specific voltage. */
  public default void setVoltage(double volts) {}

  /** Soft-reset the encoder position. */
  public default void setPosition(double position) {}
}