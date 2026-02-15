package frc.robot.subsystems.Manipulator;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import frc.robot.Constants;

public class CoralIOSim implements CoralIO {
    private DIOSim sensor1;
    private DIOSim sensor2;
    private DCMotorSim motor1;
    private DCMotorSim motor2;
    private LinearSystem plant;


    public CoralIOSim(){
        sensor1 = new DIOSim(1);
        sensor2 = new DIOSim(2);
        plant = LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1),
        1, // random values
        1
        );
        motor1 = new DCMotorSim(plant, DCMotor.getKrakenX60(1));
        motor2 = new DCMotorSim(plant, DCMotor.getKrakenX60(1));
    }

    public boolean seen(){
        return sensor1.getValue();
    }

    public boolean entered(){
        return (sensor1.getValue() && sensor2.getValue());
    }

    public void updateInputs(CoralIOInputs inputs){
        motor1.update(Constants.globalDelta_s);
        motor2.update(Constants.globalDelta_s);
        
        inputs.motor1speed = motor1.getAngularVelocityRPM();
        inputs.motor2speed = motor2.getAngularVelocityRPM();
        inputs.proximity1 = sensor1.getValue();
        inputs.proximity2 = sensor2.getValue();
    }

    public void spinMotors(double speed){
        motor1.setAngularVelocity(speed);
        motor2.setAngularVelocity(speed);
    }
}
