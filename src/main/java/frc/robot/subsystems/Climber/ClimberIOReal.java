package frc.robot.subsystems.Climber;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.VoltageOut;

public class ClimberIOReal implements ClimberIO {
  private final TalonFX m_motor = new TalonFX(0);
  private final VoltageOut m_voltageControl = new VoltageOut(0);

  public ClimberIOReal() {
    // Configure motor settings here (current limits, neutral mode, etc.)
  }

  @Override
  public void updateInputs(ClimberIOInputs inputs) {
    inputs.positionRotations = m_motor.getPosition().getValueAsDouble();
    inputs.velocityRps = m_motor.getVelocity().getValueAsDouble();
    inputs.appliedVolts = m_motor.getMotorVoltage().getValueAsDouble();
    inputs.currentAmps = new double[] { m_motor.getStatorCurrent().getValueAsDouble() };
  }

  @Override
  public void setVoltage(double volts) {
    m_motor.setControl(m_voltageControl.withOutput(volts));
  }

  @Override
  public void setPosition(double position) {
    m_motor.setPosition(position);
  }
}