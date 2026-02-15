package frc.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Climber extends SubsystemBase {
  private final ClimberIO io;
  private final ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();

  /** Creates a new Climber with injected IO. */
  public Climber(ClimberIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs); // Pull hardware data
    Logger.processInputs("Climber", inputs); // Log data (if using AdvantageKit)
  }

  public void moveMotor(double speed) {
    // Convert -1.0 to 1.0 speed to voltage (-12V to 12V)
    io.setVoltage(speed);
  }

  public void resetMotor() {
    io.setPosition(0);
  }
  
  public double getPosition() {
    return inputs.positionRotations;
  }
}