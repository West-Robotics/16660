package org.firstinspires.ftc.teamcode.architecture

abstract class Command: BaseCommand{
    private var isScheduled = false

    override fun initialize() {}
    override fun execute() {}
    override fun isFinished(): Boolean = true
    override fun end(interrupted: Boolean){}

    override fun schedule() {
        if (!isScheduled){
            isScheduled = true
            initialize()
        }
    }
    override fun run() {
        execute()
    }
    override fun shouldFinish() = isFinished()

    override fun cancel() {
        if (isScheduled){
            end(true)
            isScheduled = false
        }
    }
    override fun finish() {
        if (isScheduled){
            end(false)
            isScheduled = false
        }

    }
    override fun getRequirements(): Set<Subsystem> = emptySet()
}