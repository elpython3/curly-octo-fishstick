// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Manipulator extends SubsystemBase {
  private TalonFX motor1;
  private TalonFX motor2;

  public DigitalInput input1;
  public DigitalInput input2;
  /** Creates a new Manipulator. */
  public Manipulator() {
    motor1 = new TalonFX(12);
    motor2 = new TalonFX(12);

    input1 = new DigitalInput(0);
    input2 = new DigitalInput(0);
  }

  public void startMotors() {
    motor1.set(.7);
    motor2.set(.7);
  }

  public void stopMotors() {
    motor1.set(0);
    motor2.set(0);
  }
  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
}
