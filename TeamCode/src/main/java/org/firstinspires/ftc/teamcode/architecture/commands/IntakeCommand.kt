package org.firstinspires.ftc.teamcode.architecture.commands

import org.firstinspires.ftc.teamcode.architecture.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.architecture.Command
import org.firstinspires.ftc.teamcode.util.Robotconstants

class IntakeCommand(private val intake: IntakeSubsystem, private val toggle: Boolean) :Command(){
    var intakeval = Robotconstants.IntakeMotorPower.STOP

    init {
        addRequirements(intake)
    }
    override fun initialize() {
        if (toggle) {
            intakeval = Robotconstants.IntakeMotorPower.INTAKE
        }   else {
            intakeval = Robotconstants.IntakeMotorPower.STOP
        }
    }

    override fun execute() {
        intake.setEffort(intakeval.power)
    }

    override fun isFinished(): Boolean = true

}