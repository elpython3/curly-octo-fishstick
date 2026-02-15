package frc.robot.subsystems.coralManipulator;

import frc.robot.util.IState;

public enum CoralManipulatorStates implements IState {
    DISABLED,
    IDLE,
    MODE1MOVING,
    MODE2MOVING,
}
/*
Mode1: Start turning when 1 sensor sees game piece,
stop when the second sensor sees it.
Mode2: Both motors spin until both sensors don't see game piece.

States:
-DISABLED: motors are off, sensors are ignored
-IDLE: motors are off, sensors are active.
If sensor 1 sees coral, transition to MODE1MOVING
-MODE1MOVING: motors are on, if sensor 2 sees coral, 
transition to IDLE
-MODE2MOVING: motors are on, if both sensors don't see coral, 
transition to IDLE
 */

/*
2 talon motors. 2 proximity sensors (digital). 
if coral (game piece) is seen by the first proximity sensor,
start turning motors until the second proximity sensor sees it.
when the second sensor sees coral both motors stop.
then add a state when the driver presses a button,
both motors will spin until both sensors don't see coral.
 */


