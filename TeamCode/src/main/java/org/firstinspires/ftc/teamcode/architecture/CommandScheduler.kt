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

        // Check for conflicts
        for (subsystem in requirements) {
            subsystemCommands[subsystem]?.let { currentCommand ->
                cancel(currentCommand)
            }
        }

        // Schedule the command
        scheduledCommands[command] = requirements.toMutableSet()
        requirements.forEach { subsystemCommands[it] = command }
        command.initialize()
    }

    fun cancel(command: Command) {
        scheduledCommands[command]?.forEach { subsystem ->
            subsystemCommands.remove(subsystem)
        }
        scheduledCommands.remove(command)
        command.end(true)
    }

    fun run() {
        // Process all triggers first
        triggers.forEach { (trigger, action) ->
            action(trigger.getState())
        }

        // Run all subsystem periodic methods
        registeredSubsystems.forEach { it.periodic() }

        // Run scheduled commands
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

        // Schedule default commands for idle subsystems
        registeredSubsystems.forEach { subsystem ->
            if (subsystem !in subsystemCommands) {
                defaultCommands[subsystem]?.schedule()
            }
        }
    }

    fun cancelAll() {
        scheduledCommands.keys.toList().forEach { cancel(it) }
    }
}