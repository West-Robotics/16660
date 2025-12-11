package org.firstinspires.ftc.teamcode.architecture
object CommandScheduler {
    private val scheduledCommands = mutableMapOf<Command, MutableSet<Subsystem>>()
    private val subsystemCommands = mutableMapOf<Subsystem, Command>()
    private val defaultCommands = mutableMapOf<Subsystem, Command>()
    private val registeredSubsystems = mutableSetOf<Subsystem>()
    private val triggers = mutableListOf<Pair<Trigger, (Boolean) -> Unit>>()

    fun registerSubsystem(subsystem: Subsystem) {
        registeredSubsystems.add(subsystem)
    }

    fun setDefaultCommand(subsystem: Subsystem, command: Command) {
        defaultCommands[subsystem] = command
    }

    fun registerTrigger(trigger: Trigger, action: (Boolean) -> Unit) {
        triggers.add(trigger to action)
    }

    fun schedule(command: Command) {
        val requirements = command.requirements

        for (subsystem in requirements) {
            subsystemCommands[subsystem]?.let { currentCommand ->
                cancel(currentCommand)
            }
        }

        scheduledCommands[command] = requirements.toMutableSet()
        requirements.forEach { subsystemCommands[it] = command }
    }

    fun cancel(command: Command) {
        scheduledCommands[command]?.forEach { subsystem ->
            subsystemCommands.remove(subsystem)
        }
        scheduledCommands.remove(command)
        command.end(true)
    }

    fun run() {
        triggers.forEach { (trigger, action) ->
            action(trigger.getState())
        }

        registeredSubsystems.forEach { it.periodic() }

        val iterator = scheduledCommands.iterator()
        while (iterator.hasNext()) {
            val (command, _) = iterator.next()
            command.execute()

            if (command.isFinished()) {
                command.end(false)
                command.requirements.forEach { subsystem ->
                    subsystemCommands.remove(subsystem)
                }
                iterator.remove()
            }
        }

        registeredSubsystems.forEach { subsystem ->
            if (subsystem !in subsystemCommands) {
                defaultCommands[subsystem]?.schedule()
            }
        }
        registeredSubsystems.forEach { it.write() }
    }

    fun cancelAll() {
        scheduledCommands.keys.toList().forEach { cancel(it) }
    }
}