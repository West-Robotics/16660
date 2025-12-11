package org.firstinspires.ftc.teamcode.architecture.teleop

import org.firstinspires.ftc.teamcode.architecture.commands.IntakeCommand

class Bindings: CommandOpMode() {
    override fun initBindings() {
        gamer1.apply {
            a.onTrue(IntakeCommand(intake,true))
            a.onFalse(IntakeCommand(intake,false))

        }
    }

    override fun addTelemetry() {
    }
}