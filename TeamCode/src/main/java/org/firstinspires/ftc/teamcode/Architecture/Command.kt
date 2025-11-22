package org.firstinspires.ftc.teamcode.Architecture

abstract class Command{
    private var isScheduled = false

    open fun initialize() {}
    open fun execute() {}
    open fun isFinished(): Boolean = true
    open fun end(interrupted: Boolean){}

    internal fun schedule() {
        if (!isScheduled){
            isScheduled = true
            initialize()
        }
    }
    internal fun run() {
        execute()
    }
    internal fun shouldFinish() = isFinished()

    internal fun cancel() {
        if (isScheduled){
            end(true)
            isScheduled = false
        }
    }
    internal fun finish() {
        if (isScheduled){
            end(false)
            isScheduled = false
        }

    }
    open fun getRequirements(): Set<Subsystem> = emptySet()
}