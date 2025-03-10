package org.firstinspires.ftc.teamcode.command;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Claw;
import org.firstinspires.ftc.teamcode.subsystem.Intake;

public class PixelRelease2 extends SequentialCommandGroup {
    public PixelRelease2(Intake intake) {
        addCommands(
                new IntakeControl(intake, 5.0)//0.26
        );
        addRequirements(intake);
    }
}
