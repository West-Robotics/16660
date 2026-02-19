package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

class HoodSubsystem(hardwareMap: HardwareMap): Subsystem() {

    val flyWheel = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,
        DcMotor.ZeroPowerBehavior.BRAKE)
    val hoodAngle = servoSetup(hardwareMap,"angle", ServoConstants.AXON)

    private val TICK_PER_REV = 28.0 // NOT REAL TODO GET REAL NUMBER

    fun setRPM(rpm:Double){
        val ticksPerSec = rpm * TICK_PER_REV / 60.0
        flyWheel.motor.velocity = ticksPerSec
    }

    

    fun position(value: Double){
        hoodAngle.position = value
    }

    fun position(value: Robotconstants.SpindexerPosition){
        hoodAngle.position = value.pos1
    }

    override fun write() {
        flyWheel.write()
        hoodAngle.write()
    }
}