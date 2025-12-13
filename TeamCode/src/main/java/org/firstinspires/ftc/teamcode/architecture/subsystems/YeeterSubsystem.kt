package org.firstinspires.ftc.teamcode.architecture.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.Subsystem
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.CRServoConstants

class YeeterSubsystem(hardwareMap: HardwareMap): Subsystem(){
    val rightIntake = CRservoSetup(hardwareMap,"rightIntake",CRServoConstants.GOBILDA_TORQUE)
    val leftIntake = CRservoSetup(hardwareMap,"leftIntake",CRServoConstants.GOBILDA_TORQUE)




    fun run(running:Boolean?){
        if (running == true){
            leftIntake.effort = Robotconstants.OuttakeBallPower.ON.speedServo1
            rightIntake.effort = Robotconstants.OuttakeBallPower.ON.speedServo2
        } else if (running ==false){
            leftIntake.effort = Robotconstants.OuttakeBallPower.OFF.speedServo1
            rightIntake.effort = Robotconstants.OuttakeBallPower.OFF.speedServo2
        } else{

        }
    }

    override fun write() {
        rightIntake.write()
        leftIntake.write()
    }
}