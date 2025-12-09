package org.firstinspires.ftc.teamcode.architecture

class Trigger(private val condition: () -> Boolean) {
    private var previousState = false

    // Runs command once when condition becomes true
    fun onTrue(command: Command): Trigger {
        CommandScheduler.registerTrigger(this) { currentState ->
            if (currentState && !previousState) {
                command.schedule()
            }
            previousState = currentState
        }
        return this
    }

    // Runs command once when condition becomes false
    fun onFalse(command: Command): Trigger {
        CommandScheduler.registerTrigger(this) { currentState ->
            if (!currentState && previousState) {
                command.schedule()
            }
            previousState = currentState
        }
        return this
    }

    // Runs command while condition is true (cancels when false)
    fun whileTrue(command: Command): Trigger {
        CommandScheduler.registerTrigger(this) { currentState ->
            if (currentState && !previousState) {
                command.schedule()
            } else if (!currentState && previousState) {
                command.cancel()
            }
            previousState = currentState
        }
        return this
    }

    // Runs command while condition is false (cancels when true)
    fun whileFalse(command: Command): Trigger {
        CommandScheduler.registerTrigger(this) { currentState ->
            if (!currentState && !previousState) {
                command.schedule()
            } else if (currentState && previousState) {
                command.cancel()
            }
            previousState = currentState
        }
        return this
    }

    // Toggles command on/off each time condition becomes true
    fun toggleOnTrue(command: Command): Trigger {
        CommandScheduler.registerTrigger(this) { currentState ->
            if (currentState && !previousState) {
                if (command.isScheduled) {
                    command.cancel()
                } else {
                    command.schedule()
                }
            }
            previousState = currentState
        }
        return this
    }

    fun getState() = condition()
}

// Gamepad trigger helpers
fun gamepadButton(button: () -> Boolean) = Trigger(button)
