package org.firstinspires.ftc.teamcode.architecture.teleop

import org.firstinspires.ftc.teamcode.architecture.InstantCommand
import org.firstinspires.ftc.teamcode.architecture.commands.IntakeCommand

class Bindings: CommandOpMode() {
    var intakeToggle: Boolean = true
    override fun initBindings() {
        gamer1.apply {
            a.onTrue(IntakeCommand(intake,intakeToggle).alongWith(InstantCommand{intakeToggle = !intakeToggle}))
            b.onTrue(IntakeCommand(intake,null).alongWith(InstantCommand{intakeToggle = false}))


        }
    }

    override fun addTelemetry() {
    }
}