package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants

class LauncherIntakeSubsytem (hardwareMap: HardwareMap): Subsystem(){
    val rightIntake = CRservoSetup(hardwareMap,"rightIntake")
    val leftIntkae = CRservoSetup(hardwareMap,"leftIntake")

    fun setEffort(){
        
    }

}