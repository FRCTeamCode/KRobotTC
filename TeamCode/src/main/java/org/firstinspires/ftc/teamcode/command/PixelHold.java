package org.firstinspires.ftc.teamcode.command;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.subsystem.Intake;

public class PixelHold extends SequentialCommandGroup {

    public PixelHold(Arm arm, Claw claw, Intake intake) {
        addCommands(
                new ParallelCommandGroup(
                        new ClawControl(claw, 2.0),
                        new IntakeControl(intake, 3.0)
                ),
                new WaitCommand(400),
                new ArmControl(arm, 0.58)
        );
        addRequirements(arm, claw, intake);
    }
}
