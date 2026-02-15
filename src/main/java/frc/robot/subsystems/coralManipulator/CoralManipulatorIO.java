// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.coralManipulator;

import org.littletonrobotics.junction.AutoLog;

public interface CoralManipulatorIO
{
    @AutoLog 
    public static class CoralManipulatorIOInputs {
        // voltage, current, velocity of the motors
        public double motor1Volts_V = 0.0;
        public double motor2Volts_V = 0.0;
        public double motor1Vel_mps = 0.0;
        public double motor2Vel_mps = 0.0;
        public double motor1Current_A = 0.0;
        public double motor2Current_A = 0.0;

        //state of the sensors
        public boolean sensor1Triggered = false;
        public boolean sensor2Triggered = false;
    }

    public default void updateInputs(CoralManipulatorIOInputs inputs) {}

    public default void setMotorVoltage(double volts_V, double ff_V) {}

    public default void setMotorVelocity(double velocity_rps) {}

    public default void stopMotors() {}

}
