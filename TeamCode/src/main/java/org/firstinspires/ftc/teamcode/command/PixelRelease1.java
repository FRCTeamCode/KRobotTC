package org.firstinspires.ftc.teamcode.command;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.subsystem.Intake;

public class PixelRelease1 extends SequentialCommandGroup {
    public PixelRelease1(Intake intake) {
        addCommands(
                new IntakeControl(intake, 2.0)
        );
        addRequirements(intake);
    }
}
