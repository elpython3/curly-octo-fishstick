// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private TalonFX m_elevatorMotor;

  private PIDController m_controller;
  private ElevatorFeedforward m_ff;

  private final double kP, kI, kD, kyS, kG, kV;

  private double target;
  
    /** Creates a new Elevator. */
    public Elevator() {
      m_elevatorMotor = new TalonFX(0);

      kP = 0;
      kI = 0;
      kD = 0;
      kyS = 0;
      kG = 0;
      kV = 0;
  
      m_controller = new PIDController(kP, kI, kD);
      m_ff = new ElevatorFeedforward(kyS, kG, kV);
    }
  
    public void setHeight(double target)
    {
      this.target = target;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double position = m_elevatorMotor.getPosition().getValueAsDouble();
    double pidOut = m_controller.calculate(position, target);

    double feedOut = m_ff.calculate(0);

    VoltageOut totalOutput = new VoltageOut(pidOut + feedOut);

    m_elevatorMotor.setControl(totalOutput);
  }
}
