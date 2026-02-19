package org.firstinspires.ftc.teamcode.architecture.commands

import org.firstinspires.ftc.teamcode.architecture.Command
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain

class AlignCommand(drivetrain: drivetrain): Command() {



    override fun execute() {

    }

    override fun isFinished(): Boolean {
        return true
    }

}