package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.servoSetup

class SpindexerSubsytem(hardwareMap: HardwareMap) : Subsystem(){
    val spindexer = servoSetup(hardwareMap,"spindexer")


}