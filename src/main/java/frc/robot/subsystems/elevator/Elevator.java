// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private TalonFX motor1 = new TalonFX(0);
  private PIDController pid;
  private SimpleMotorFeedforward ff;
  private final double kP=0;
  private final double kI=0;
  private final double kD=0;
  private final double kS=0;
  private final double kG=0;
  private final double kV=0;
  private double targetHeight=0;
  
    /** Creates a new Elevator. */
    public Elevator() {
      pid = new PIDController(kP, kI, kD);
      ff = new SimpleMotorFeedforward(kS, kG, kV);
    }
  
    public void setHeight(double targetHeight)
    {
      this.targetHeight = targetHeight;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double currentPosition = motor1.getPosition().getValueAsDouble();
    double pidOut = pid.calculate(currentPosition, targetHeight);
    double ffOut = ff.calculate(0);

    motor1.setControl(new VoltageOut(pidOut + ffOut));
  }
}
