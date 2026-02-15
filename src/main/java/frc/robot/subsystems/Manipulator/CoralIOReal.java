package frc.robot.subsystems.Manipulator;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.CoralConstants;

public class CoralIOReal implements CoralIO {
    private DigitalInput sensor1;
    private DigitalInput sensor2;
    private TalonFX motor1;
    private TalonFX motor2; 
    public CoralIOReal(){
        motor2 = new TalonFX(2);
        motor1 = new TalonFX(1);
        sensor1 = new DigitalInput(1);
        sensor2 = new DigitalInput(2);
    }
    public void updateInputs(CoralIOInputs inputs){
        inputs.proximity1 = sensor1.get();
        inputs.proximity2 = sensor2.get();
        inputs.motor1speed = motor1.get();
        inputs.motor2speed = motor2.get();
    }

    public boolean seen(){
        return sensor1.get();
    }

    public boolean entered(){
        return (sensor1.get() && sensor2.get());
    }

    public void spinMotors(double speed){
        motor1.set(speed);
        motor2.set(speed);
    }
}
