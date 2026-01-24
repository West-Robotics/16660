package org.firstinspires.ftc.teamcode.architecture

import org.firstinspires.ftc.robotcore.external.Telemetry

class Trigger(private val condition: () -> Boolean) {

    var previous = false
    var current = false
    val bindings = mutableListOf<() -> Unit>()

    internal fun update() {
        current = condition()
    }

    internal fun fire() {
        bindings.forEach { it() }
    }

    internal fun commit() {
        previous = current
    }
    public var pressed = false
    private fun rising() = current && !previous
    private fun falling() = !current && previous

    fun onTrue(command: Command, telemetry: Telemetry?): Trigger {
        bindings += {
            if (rising()) {
                pressed = true
                command.schedule()
            }
        }
        CommandScheduler.registerTrigger(this)
        return this
    }

    fun onFalse(command: Command): Trigger {
        bindings += {
            if (falling()) command.schedule()
        }
        CommandScheduler.registerTrigger(this)
        return this
    }

    fun whileTrue(command: Command,telemetry: Telemetry): Trigger {
        bindings += {
            if (current){
                telemetry.addLine("fired")
                command.schedule()
            } else {
                command.cancel()
            }
        }
        CommandScheduler.registerTrigger(this)
        return this
    }

    fun toggleOnTrue(command: Command): Trigger {
        bindings += {
            if (rising()) {
                if (command.isScheduled) command.cancel()
                else command.schedule()
            }
        }
        CommandScheduler.registerTrigger(this)
        return this
    }
}
