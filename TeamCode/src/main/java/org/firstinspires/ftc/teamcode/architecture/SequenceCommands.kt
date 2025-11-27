package org.firstinspires.ftc.teamcode.architecture

import kotlin.collections.set

abstract class SequenceCommands(vararg val comand: Command): BaseCommand{

    private var sequencedCommands = mutableSetOf<Command>()
    private var sequencedRequirements = mutableMapOf<Subsystem, Command>()
    override fun initialize() {

    }
    override fun execute() {

    }
    override fun isFinished() : Boolean = true
    override fun schedule() {
        for (com in comand){
            sequencedCommands.add(com)
            com.getRequirements().forEach{sequencedRequirements[it]=com}
        }
    }



}