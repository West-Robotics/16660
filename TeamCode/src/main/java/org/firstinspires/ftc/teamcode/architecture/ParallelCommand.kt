package org.firstinspires.ftc.teamcode.architecture

class ParallelCommand(vararg command:Command):Command() {
    private val command = command.toList()
    init {
        command.forEach { cmd -> requirements.addAll(cmd.requirements) }
    }

    override fun initialize() {
        command.forEach { it.initialize() }
    }

    override fun execute() {
        command.forEach { it.execute() }
    }
    override fun isFinished():Boolean = command.all{it.isFinished()}

    override fun end(interrupted: Boolean){
        command.forEach { it.end(interrupted) }
    }

}