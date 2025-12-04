package org.firstinspires.ftc.teamcode.architecture

object CommandScheduler {
    private val activeCommands = mutableSetOf<Command>()
    private val requirements = mutableMapOf<Subsystem, Command>()

    fun schedule(command: Command){
        for (sub in command.getRequirements()){
            val blocking = requirements[sub]
            if (blocking != null){
                blocking.cancel()
                activeCommands.remove(blocking)
                requirements.remove(sub)
            }
        }
        command.getRequirements().forEach { requirements[it] = command }
        activeCommands.add(command)
        command.schedule()

    }
    fun run() {
        requirements.keys.forEach {it.periodic()}
        val iterator = activeCommands.iterator()
        while(iterator.hasNext()){
            val cmd = iterator.next()
            cmd.run()
            if (cmd.shouldFinish()){
                cmd.finish()
                iterator.remove()
                cmd.getRequirements().forEach { requirements.remove(it)}
            }
        }
        requirements.keys.forEach { it.write() }
    }

    fun cancelAll() {
        activeCommands.forEach { it.cancel() }
        activeCommands.clear()
        requirements.clear()
    }

}