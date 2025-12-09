package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.sun.tools.javac.tree.DCTree
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.motorSetup

class Launcher(hardwareMap: HardwareMap): Subsystem() {
    val outTakeHigher = motorSetup(hardwareMap,
        "outTakeH",
        DcMotorSimple.Direction.FORWARD,
        DcMotor.ZeroPowerBehavior.BRAKE)
    val outTakeLower = motorSetup(hardwareMap,
        "outTakeL",
        DcMotorSimple.Direction.FORWARD,
        DcMotor.ZeroPowerBehavior.BRAKE)

    fun setEffort(power:Double){
        outTakeHigher.effort = power
        outTakeLower.effort = power
    }

    override fun write() {
        outTakeLower.write()
        outTakeHigher.write()
    }


}