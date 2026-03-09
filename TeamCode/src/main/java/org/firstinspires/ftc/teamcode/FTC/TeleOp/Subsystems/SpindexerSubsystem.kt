package org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants

class SpindexerSubsystem(hardwareMap: HardwareMap) {

    val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)



    fun write(){
        spindexer.write()
    }
}