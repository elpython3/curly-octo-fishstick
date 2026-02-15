package frc.robot.subsystems.coralManipulator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;

public class CoralManipulatorIOSim implements CoralManipulatorIO{
    private DCMotorSim m1Sim;
    private LinearSystem<N2,N1,N2> m1System;
    private DCMotorSim m2Sim;
    private LinearSystem<N2,N1,N2> m2System;

    private double m1V, m1vel;

    private double m2V,m2vel;

    private boolean sensor1In,sensor2In;

    public CoralManipulatorIOSim(){
        m1System = LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60Foc(1), 1.0, 1.0);
        m2System = LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60Foc(1),1.0,1.0);
        m1Sim = new DCMotorSim(m1System, DCMotor.getKrakenX60Foc(1));
        m2Sim = new DCMotorSim(m2System, DCMotor.getKrakenX60Foc(1));
        m1V = m1Sim.getInputVoltage();
        m2V = m2Sim.getInputVoltage();
        m1vel = m1Sim.getAngularVelocityRadPerSec();
        m2vel = m2Sim.getAngularVelocityRadPerSec();
        sensor1In = sensor2In = false;
    }

    @Override
    public void updateInputs(LogInputs inputs){
        inputs.motor1_A = m1Sim.getCurrentDrawAmps();
        inputs.motor1_V = m1V;
        inputs.motor1_vel = m1vel;

        inputs.motor2_A = m2Sim.getCurrentDrawAmps();
        inputs.motor2_V = m2V;
        inputs.motor2_vel = m2vel;

        inputs.sensor1Input = sensor1In;
        inputs.sensor2Input = sensor2In;
    }

    @Override
    public void spin(double speed){
        speed = MathUtil.clamp(12*speed/Constants.CoralConstants.MAXVEL, -12.0, 12);
        m1Sim.setInputVoltage(speed);
        m2Sim.setInputVoltage(speed);
    }

    @Override
    public void stop(){
        m2Sim.setInputVoltage(0);
        m1Sim.setInputVoltage(0);
    }

}