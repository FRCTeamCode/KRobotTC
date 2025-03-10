package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumFaster;
import org.firstinspires.ftc.teamcode.hardware.ArmAuto;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.CameraAuto;
import org.firstinspires.ftc.teamcode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous
@Config
public class AutoBlueLeft extends LinearOpMode {
    SampleMecanumFaster drive;
//    MyCamera myCamera;
    CameraAuto cameraAuto;
    ArmAuto armAuto;
    Claw claw;
    Intake intake;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    private final Telemetry dashboardTelemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
    AprilTagDetection tagOfInterest = null;
    String targetSide = "";
    TrajectorySequence targetRoad;
    ElapsedTime timer = new ElapsedTime();
    double lastTime = 0;
    double currentTime = 0;
    private int tagID = 4;
    public static double cycleDelay = .52;
    public static double turretAfterScoreDelay = .7;
    private boolean isAutoEnd = false;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumFaster(hardwareMap, dashboardTelemetry);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.setPoseEstimate(AutoConstants.START);

        armAuto = new ArmAuto(hardwareMap, dashboardTelemetry);
        claw = new Claw(hardwareMap, dashboardTelemetry);
        intake = new Intake(hardwareMap, dashboardTelemetry);
//        myCamera = new MyCamera(hardwareMap, dashboardTelemetry);
        cameraAuto = new CameraAuto(hardwareMap, dashboardTelemetry);

        isAutoEnd = false;

        TrajectorySequence pathLeft = drive.trajectorySequenceBuilder(AutoConstants.START)
                .setVelConstraint(AutoConstants.PARK_VEL)
                .setAccelConstraint(AutoConstants.PARK_ACCEL)
                .addTemporalMarker(0.0, () -> {
                    armAuto.setArmPos(1.08);
                })
                .addTemporalMarker(0.9, () -> {
                    claw.lowClaw();
                })
                .addTemporalMarker(1.6, () -> {
                    intake.openIntake();
                })
                .addTemporalMarker(2.0, () -> {
                    claw.middleClaw();
                })
                .lineToLinearHeading(AutoConstants.BL1_PUT)
                .addTemporalMarker(3.0, () -> {
                    intake.closeIntake();
                })
                .addTemporalMarker(3.0, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArmPre);
                })
                .addTemporalMarker(3.6, () -> {
                    claw.lowerClaw();
                })
                .lineToLinearHeading(AutoConstants.BL1_BACKSTAGE)
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArm);
                })
                .waitSeconds(1.8)
                .lineToLinearHeading(AutoConstants.BL1_Tag)
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(1.8);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    claw.middleClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(0.58);
                })
                .lineToLinearHeading(AutoConstants.BL1_STOP)
                .lineToLinearHeading(AutoConstants.BL1_STOP_BACK)
                .build();

        TrajectorySequence pathMiddle = drive.trajectorySequenceBuilder(AutoConstants.START)
                .setVelConstraint(AutoConstants.PARK_VEL)
                .setAccelConstraint(AutoConstants.PARK_ACCEL)
                .addTemporalMarker(0.0, () -> {
                    armAuto.setArmPos(1.08);
                })
                .addTemporalMarker(2.0, () -> {
                    claw.lowClaw();
                })
                .addTemporalMarker(2.5, () -> {
                    intake.openIntake();
                })
                .lineToLinearHeading(AutoConstants.BM1_PUT)
                .addTemporalMarker(3.6, () -> {
                    intake.closeIntake();
                })
                .addTemporalMarker(3.6, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArmPre);
                })
                .addTemporalMarker(4.2, () -> {
                    claw.lowerClaw();
                })
                .lineToLinearHeading(AutoConstants.BM1_BACKSTAGE)
//                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
//                    armAuto.setArmPos(2.8);
//                })
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArm);
                })
                .waitSeconds(1.8)
                .lineToLinearHeading(AutoConstants.BM1_Tag)
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(1.8);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    claw.middleClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(0.58);
                })
                .lineToLinearHeading(AutoConstants.BM1_STOP)
                .lineToLinearHeading(AutoConstants.BM1_STOP_BACK)
                .build();
        TrajectorySequence pathRight = drive.trajectorySequenceBuilder(AutoConstants.START)
                .setVelConstraint(AutoConstants.PARK_VEL)
                .setAccelConstraint(AutoConstants.PARK_ACCEL)
                .addTemporalMarker(0.0, () -> {
                    armAuto.setArmPos(1.08);
                })
                .addTemporalMarker(1.2, () -> {
                    claw.lowClaw();
                })
                .addTemporalMarker(2.1, () -> {
                    intake.openIntake();
                })
                .lineToLinearHeading(AutoConstants.BR1_PUT)
                .waitSeconds(0.85)
                .addTemporalMarker(3.8, () -> {
                    intake.closeIntake();
                })
                .addTemporalMarker(3.8, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArmPre);
                })
                .addTemporalMarker(4.3, () -> {
                    claw.lowerClaw();
                })
                .lineToLinearHeading(AutoConstants.BR1_BACKSTAGE)
                .UNSTABLE_addTemporalMarkerOffset(1.0, () -> {
                    armAuto.setArmPos(AutoConstants.autoPutArm);
                })
                .waitSeconds(1.8)
                .lineToLinearHeading(AutoConstants.BR1_Tag)
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(1.8);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    claw.middleClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    armAuto.setArmPos(0.58);
                })
                .lineToLinearHeading(AutoConstants.BR1_STOP)
                .lineToLinearHeading(AutoConstants.BR1_STOP_BACK)
                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                    AutoConstants.initAngle = AngleUnit.DEGREES.toRadians(drive.getImuRad());
                })
                .build();

        targetRoad = pathRight;

        while (!isStarted() && !isStopRequested()) {
//            myCamera.runDoubleVision();
            cameraAuto.runDoubleVision();
            dashboardTelemetry.addLine("Robot is (!isStarted() && !isStopRequested())");
            dashboardTelemetry.addData("minT", dashboardTelemetry.getMsTransmissionInterval());

            currentTime = timer.milliseconds();
            dashboardTelemetry.addLine("run-time: " + currentTime/1000);

            List<Recognition> currentRecognitions = cameraAuto.getTfodData();
            if (currentRecognitions.size() != 0) {
                boolean tagFound = false;
                for (Recognition recognition : currentRecognitions) {
                    double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                    double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
//                    dashboardTelemetry.addData(""," ");
//                    dashboardTelemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//                    dashboardTelemetry.addData("- Position", "%.0f / %.0f", x, y);
//                    dashboardTelemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
                    if (recognition.getLabel() == "BlueCube") {
                        tagFound = true;
                        if (Math.abs(x-290) < 48 && Math.abs(y-154) < 48 && Math.abs(recognition.getWidth()-86) < 24 && Math.abs(recognition.getHeight()-100) < 24) {
                            tagID = 4;
                            targetSide = "Left";
                            targetRoad = pathLeft;
                        } else if (Math.abs(x-515) < 48 && Math.abs(y-128) < 48 && Math.abs(recognition.getWidth()-82) < 24 && Math.abs(recognition.getHeight()-88) < 24) {
                            tagID = 5;
                            targetSide = "Middle";
                            targetRoad = pathMiddle;
                        } else {
                            tagID = 6;
                            targetSide = "Right";
                            targetRoad = pathRight;
                        }
                    } else {
                        tagID = 6;
                        targetSide = "Right";
                        targetRoad = pathRight;
                    }
                }

                if (tagFound) {
                    dashboardTelemetry.addLine("Tag of interest is in sight!\n\nLocation data: " + targetSide);
                } else {
                    tagID = 6;
                    targetSide = "Right";
                    targetRoad = pathRight;
                    dashboardTelemetry.addLine("Don't see tag of interest :(" + targetSide);

                    if (tagOfInterest == null) {
                        dashboardTelemetry.addLine("(The tag has never been seen1)");
                    } else {
                        dashboardTelemetry.addLine("\nBut we HAVE seen the tag before; last seen at:" + targetSide);
                    }
                }

            } else {
                tagID = 6;
                targetSide = "Right";
                targetRoad = pathRight;
                dashboardTelemetry.addLine("Don't see tag of interest :(");
                if (tagOfInterest == null) {
                    dashboardTelemetry.addLine("(The tag has never been seen2)");
                } else {
                    dashboardTelemetry.addLine("\nBut we HAVE seen the tag before; last seen at:" + targetSide);
                }

            }
            dashboardTelemetry.update();
            sleep(20);
        }
        drive.followTrajectorySequenceAsync(targetRoad);
        lastTime = timer.milliseconds();

        while (!isStopRequested() && opModeIsActive()) {
            drive.update();
            armAuto.loop();
//            myCamera.runDoubleVision();

//            if (!drive.isBusy()&&!isAutoEnd) {
//                //path has finished
//                double[] idData = myCamera.getAprilTagIDData(tagID);
//                drive.alignAprilTag(10.0, 0.0, 0.0, idData[0], idData[1], idData[2], idData[3]);
//                dashboardTelemetry.addLine("AprilTag Tracking");
//                if (drive.isEndAlign()) {
//                    isAutoEnd = true;
//                    if (targetSide == "Left") {
//                        drive.followTrajectorySequenceAsync(pathLeftPark);
//                    } else if (targetSide == "Middle") {
//                        drive.followTrajectorySequenceAsync(pathMiddlePark);
//                    } else {
//                        drive.followTrajectorySequenceAsync(pathRightPark);
//                    }
//                }
//
//            }
            currentTime = timer.milliseconds();
            dashboardTelemetry.addLine("run-time: " + currentTime/1000);
            dashboardTelemetry.addLine("loop-time: " + (currentTime - lastTime)/1000);

            lastTime = currentTime;
//            dashboardTelemetry.update();
        }

    }
}
