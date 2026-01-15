package org.firstinspires.ftc.teamcode.architecture.commands

import org.firstinspires.ftc.teamcode.architecture.Command
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

class AlignCommand(drivetrain: drivetrain): Command() {



    override fun execute() {

    }

    override fun isFinished(): Boolean {
        return true
    }

}