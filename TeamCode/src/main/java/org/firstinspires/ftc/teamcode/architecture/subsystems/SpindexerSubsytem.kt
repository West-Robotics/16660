package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants
import com.qualcomm.robotcore.hardware.ServoImplEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.util.Robotconstants


class SpindexerSubsytem(hardwareMap: HardwareMap) : Subsystem(){
    val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)

    fun position(constants: Robotconstants.SpindexerPosition){
        spindexer.position = constants.pos1
    }


    override fun write() {
        spindexer.write()
    }

}