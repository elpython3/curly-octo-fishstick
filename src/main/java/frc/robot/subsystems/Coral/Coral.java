// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Coral extends SubsystemBase {
  private final TalonFX m_lMotor;
  private final TalonFX m_rMotor;

  private final DigitalInput m_input1;
  private final DigitalInput m_input2;

  private boolean score;
  
  /** Creates a new Coral. */
  public Coral() {
    m_lMotor = new TalonFX(0);
    m_rMotor = new TalonFX(1);

    m_input1 = new DigitalInput(0);
    m_input2 = new DigitalInput(1);

    score = false;
  }

  public void setScore(boolean b)
  {
    score = b;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (score)
    {
      m_lMotor.set(0.5); m_rMotor.set(-0.5);

      if (!m_input1.get() && !m_input2.get())
        score = false;
    }


    if (m_input1.get() && !m_input2.get())
      m_lMotor.set(0.5); m_rMotor.set(-0.5);

    if (m_input1.get() && m_input2.get())
      m_lMotor.set(0); m_rMotor.set(0);
    
  }
}
