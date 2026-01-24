package org.firstinspires.ftc.teamcode.autonomous.lime

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

class Alignment (val hardwareMap: HardwareMap, var telemetry: Telemetry, val drive: drivetrain){
    val limelight = hardwareMap.get("LimeLight") as Limelight3A
    fun init(){
        limelight.pipelineSwitch(8)
        limelight.start()
    }

    fun align(){
        var llResult: LLResult = limelight.latestResult
        if (llResult != null && llResult.isValid){
            telemetry.addData("TargetX", llResult.tx)
            telemetry.addData("TargetY",llResult.ty)
            telemetry.addData("TargetArea",llResult.ta)

        }
    }
}