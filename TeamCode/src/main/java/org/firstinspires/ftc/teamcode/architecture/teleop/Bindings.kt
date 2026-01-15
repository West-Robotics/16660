package org.firstinspires.ftc.teamcode.architecture.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.architecture.InstantCommand
import org.firstinspires.ftc.teamcode.architecture.commands.AlignCommand
import org.firstinspires.ftc.teamcode.architecture.commands.IntakeCommand
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

@TeleOp(name = "Champs")
class Bindings: CommandOpMode() {
    var intakeToggle: Boolean = true
    override fun initBindings() {
        gamer1.apply {
            a.onTrue(IntakeCommand(intake,intakeToggle).alongWith(InstantCommand{intakeToggle = !intakeToggle}))
            b.onTrue(IntakeCommand(intake,null).alongWith(InstantCommand{intakeToggle = false}))
            y.whileTrue(AlignCommand(drive))

        }
    }

    override fun addTelemetry() {
    }
}