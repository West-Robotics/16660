package org.firstinspires.ftc.teamcode.mechanisms


import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.motorSetup
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin
class DrivetrainTank (
    hardwareMap: HardwareMap,
){
    val frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE )
    val frontRight = motorSetup(hardwareMap, "frontRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE )
    fun effort(leftInput: Double, rightInput: Double){
        frontLeft.effort = leftInput
        frontRight.effort = rightInput
    }

    fun stickEffort(x:Double,y:Double){
        frontLeft.effort = ((y+x)/max(abs(y) + abs(x),1.0)).pow(5)
        frontRight.effort = ((y-x)/max(abs(y) + abs(x),1.0)).pow(5)
    }

    fun write(){
        frontLeft.write()
        frontRight.write()
    }
}