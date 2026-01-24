package org.firstinspires.ftc.teamcode.architecture

abstract class Command {

    internal var isScheduled = false
        private set

    val requirements = mutableSetOf<Subsystem>()

    open fun initialize() {}
    open fun execute() {}
    open fun isFinished(): Boolean = false
    open fun end(interrupted: Boolean) {}

    fun schedule() {
        CommandScheduler.schedule(this)
    }

    fun cancel() {
        CommandScheduler.cancel(this)
    }

    fun addRequirements(vararg subsystems: Subsystem) {
        requirements.addAll(subsystems)
    }

    internal fun _initialize() {
        isScheduled = true
        initialize()
    }

    internal fun _end(interrupted: Boolean) {
        isScheduled = false
        end(interrupted)
    }
    fun andThen(next: Command): Command = SequenceCommand(this, next)
    fun alongWith(other: Command): Command = ParallelCommand(this, other)
}
