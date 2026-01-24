package org.firstinspires.ftc.teamcode.architecture

import org.firstinspires.ftc.robotcore.external.Telemetry

object CommandScheduler {

    val scheduledCommands = mutableSetOf<Command>()
    private val subsystemOwnership = mutableMapOf<Subsystem, Command>()
    private val defaultCommands = mutableMapOf<Subsystem, Command>()
    val subsystems = mutableSetOf<Subsystem>()
    val triggers = mutableSetOf<Trigger>()

    val finishedCommands = mutableSetOf<Command>()

    fun registerSubsystem(subsystem: Subsystem) {
        subsystems.add(subsystem)
    }

    fun setDefaultCommand(subsystem: Subsystem, command: Command) {
        defaultCommands[subsystem] = command
    }

    fun registerTrigger(trigger: Trigger) {
        triggers.add(trigger)
    }
    var initialized: Boolean = false
    init{
        initialized = false
    }
    fun schedule(command: Command) {
        if (command in scheduledCommands) {return}

        // Cancel conflicting commands
        command.requirements.forEach { subsystem ->
            subsystemOwnership[subsystem]?.let { cancel(it) }
        }

        // Claim subsystems
        command.requirements.forEach {
            subsystemOwnership[it] = command
        }

        scheduledCommands.add(command)
        command._initialize()
        initialized = true
    }

    fun cancel(command: Command) {
        if (command !in scheduledCommands) return

        scheduledCommands.remove(command)

        command.requirements.forEach {
            subsystemOwnership.remove(it)
            it.stop()
        }

        command._end(true)
    }

    fun run(telemetry: Telemetry) {
        // --- TRIGGERS ---

        triggers.forEach { it.update() }
        triggers.forEach { it.fire() }
        triggers.forEach { it.commit() }


        // --- SUBSYSTEM PERIODIC ---
        subsystems.forEach { it.periodic() }

        // --- COMMAND EXECUTION PHASE ---
        telemetry.addData("scheduledCommands",scheduledCommands)
        for (cmd in scheduledCommands){
            cmd.execute()
            if (cmd.isFinished()){
                finishedCommands.add(cmd)
            }
        }

        // --- WRITE TO HARDWARE PHASE ---
        subsystems.forEach { it.write() }

        // --- CLEANUP PHASE ---
        finishedCommands.forEach { command ->
            scheduledCommands.remove(command) // remove from scheduled commands
            // Release subsystems
            command.requirements.forEach { subsystem ->
                subsystemOwnership.remove(subsystem)
                subsystem.stop()
            }
            // End the command
            command._end(false)
        }


        // --- DEFAULT COMMANDS ---
        subsystems.forEach { subsystem ->
            if (subsystem !in subsystemOwnership) {
                val default = defaultCommands[subsystem]
                if (default != null && default !in scheduledCommands) {
                    schedule(default)
                }
            }
        }


    }

    fun cancelAll() {
        scheduledCommands.toList().forEach { cancel(it) }
    }

    fun resetAll(){
        triggers.clear()
        scheduledCommands.clear()
        subsystems.clear()
        subsystemOwnership.clear()
        defaultCommands.clear()
        finishedCommands.clear()
    }
}
