package org.firstinspires.ftc.teamcode.architecture

import kotlin.collections.set

class SequenceCommand(vararg command: Command): Command(){

    private val command = command.toList()
    private var index: Int = 0

    init{
        command.forEach { cmd -> requirements.addAll(cmd.requirements) }
    }

    override fun initialize() {
        index = 0
        if (command.isNotEmpty()){
            command[index].initialize()
        }
    }

    override fun execute() {
        if (index < command.size){
            val cmd = command[index]
            cmd.execute()
            if (cmd.isFinished()){
                cmd.end(false)
                index++
                if (index<command.size){
                    command[index].initialize()
                }
            }
        }
    }

    override fun isFinished(): Boolean = index>=command.size
}