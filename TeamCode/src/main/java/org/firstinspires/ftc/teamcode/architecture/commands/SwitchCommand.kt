package org.firstinspires.ftc.teamcode.architecture.commands

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Command
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.util.Robotconstants

class SwitchCommand(spindexer: SpindexerSubsytem,position: Robotconstants.SpindexerPosition): Command() {
    val spindexer = spindexer
    val position = position

    init {
        addRequirements(spindexer)
    }

    override fun initialize() {
        spindexer.position(position)
    }

    override fun execute() {
    }

    override fun isFinished(): Boolean { return true }





}