// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class TowardsCoral extends Command {
  private Manipulator manipulator;
  /** Creates a new TowardsCoral. */
  public TowardsCoral(Manipulator m) {
    manipulator = m;
    addRequirements(manipulator);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(manipulator.input1.get() == true && manipulator.input2.get() == false) {
      manipulator.startMotors();
    }
    else if (manipulator.input2.get() == false && manipulator.input1.get() == true) {
      manipulator.startMotors();
    }
    else if (manipulator.input2.get() == false && manipulator.input1.get() == false) {
      manipulator.stopMotors();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
