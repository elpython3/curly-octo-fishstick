// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {
    @AutoLog 
    public static class ClimberIOInputs {
        public double climbVolts_V = 0.0;
        public double climbVel_rps = 0.0;
        public double climbCurrent_A = 0.0;
        public double climbPos_deg = 0.0;
    }

    public default void updateInputs(ClimberIOInputs inputs) {}

    public default void setClimbVoltage(double volts_V, double ff_V) {}

    public default void setClimbVelocity(double velocity_rps) {}

    public default void stopClimb() {}

    public default void climbTo(double position) {}

    public default void toggleBrake() {}

}
