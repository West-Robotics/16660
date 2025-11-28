package org.firstinspires.ftc.teamcode.architecture

class InstantCommand(private val action:()->Unit) : Command(){
    override fun execute(){
        action()
    }
    override fun isFinished() = true

}