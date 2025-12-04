package org.firstinspires.ftc.teamcode.architecture

abstract class Subsystem {
    open fun init(){}
    open fun periodic() {}
    open fun stop(){}
    open fun write(){}
}