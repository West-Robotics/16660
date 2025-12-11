package org.firstinspires.ftc.teamcode.architecture

abstract class Command{
    var requirements = mutableSetOf<Subsystem>()
    var isScheduled = false

    open fun initialize() {}
    open fun execute() {}
    open fun isFinished(): Boolean = false
    open fun end(interrupted: Boolean){}

    fun schedule() {
        if (!isScheduled){
            isScheduled = true
            initialize()
        }
        CommandScheduler.schedule(this)
    }
    fun run() {
        execute()
    }
    fun shouldFinish() = isFinished()

    fun cancel() {
        if (isScheduled){
            end(true)
            isScheduled = false
        }
    }
    fun finish() {
        if (isScheduled){
            end(false)
            isScheduled = false
        }

    }
    fun addRequirements(vararg subsystem: Subsystem){
        subsystem.forEach { requirements.add(it) }
    }
    fun andThen(next:Command): Command = SequenceCommand(this,next)
    fun alongWith(parallel: Command): Command = ParallelCommand(this,parallel)
}