package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

class HoodSubsystem(hardwareMap: HardwareMap): Subsystem() {

    val hoodAngle = servoSetup(hardwareMap,"angle", ServoConstants.AXON)

    fun position(value: Double){
        hoodAngle.position = value
    }

    fun position(value: Robotconstants.SpindexerPosition){
        hoodAngle.position = value.pos1
    }

    override fun write() {
        hoodAngle.write()
    }
}