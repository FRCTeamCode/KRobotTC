package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw extends SubsystemBase {
    public Servo clawServo;
    private final Telemetry telemetry;
    public static double lowerClaw = 0.29;
    public static double lowClaw = 0.31;
    public static double lowMidClaw = 0.34;
    public static double middleLowClaw = 0.375;
    public static double middleClaw = 0.3865;
    public static double highClaw = 0.40;
    public static double highLowerClaw = 0.394;
    public static double pixelPutLowClaw = 0.3885;//0.385
    public static double pixelPutMidClaw = 0.391;//0.3895

    public Claw(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        clawServo = hardwareMap.get(Servo.class, "goB1");//0.1->0.2 claw up
        middleClaw();
    }

    @Override
    public void periodic() {
        telemetry.addData("ClawServo", clawServo.getPosition());
    }

    public void lowerClaw() {
        clawServo.setPosition(lowerClaw);
    }
    public void lowClaw() {
        clawServo.setPosition(lowClaw);
    }public void lowMidClaw() {
        clawServo.setPosition(lowClaw);
    }
    public void middleClaw() {
        clawServo.setPosition(middleClaw);
    }
    public void highClaw() {
        clawServo.setPosition(highClaw);
    }
    public void highLowerClaw() {
        clawServo.setPosition(highLowerClaw);
    }
    public void middleLowClaw() {
        clawServo.setPosition(middleLowClaw);
    }
    public void pixelPutLowClaw() {
        clawServo.setPosition(pixelPutLowClaw);
    }
    public void pixelPutMidClaw() {
        clawServo.setPosition(pixelPutMidClaw);
    }
}
