package org.firstinspires.ftc.teamcode.architecture

interface BaseCommand {
    fun initialize() {}
    fun execute() {}
    fun isFinished():Boolean
    fun end(interrupted: Boolean){}

    fun schedule(){}
    fun run(){}
    fun cancel(){}
    fun shouldFinish() = isFinished()
    fun finish(){}
    fun getRequirements(): Set<Subsystem>
}