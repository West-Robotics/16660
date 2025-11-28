package org.firstinspires.ftc.teamcode.architecture

class RunCommand(private val action:()-> Unit,vararg subsystems: Subsystem):Command() {
    init{
        addRequirements(*subsystems)
    }

    override fun execute() {
        action()
    }

}