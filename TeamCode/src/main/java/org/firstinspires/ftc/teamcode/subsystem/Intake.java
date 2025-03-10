package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake extends SubsystemBase {
    public Servo intakeServo;
    private final Telemetry telemetry;
    public static double closeIntake = 0.16;
    public static double looseIntake = 0.23;
    public static double openIntake = 0.47;//0.47;

    public Intake(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        intakeServo = hardwareMap.get(Servo.class, "goB2");//0.23->0.4 intake open
        closeIntake();
    }

    @Override
    public void periodic() {
        telemetry.addData("IntakeServo", intakeServo.getPosition());
    }
    public  void  intakeSetPos(double pos) {
        intakeServo.setPosition(pos);
    }

    public void openIntake() {
        intakeServo.setPosition(openIntake);
    }

    public void looseIntake() {
        intakeServo.setPosition(looseIntake);
    }

    public void closeIntake() {
        intakeServo.setPosition(closeIntake);
    }
}
