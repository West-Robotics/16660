package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.hardware.limelightvision.*
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D

class Limelight (hardwareMap: HardwareMap,name:String="Limelight"){
    val limelight = hardwareMap.get(name) as Limelight3A

    init{
        limelight.pipelineSwitch(8)
    }

    fun pipelineSwitch(int: Int) {
        limelight.pipelineSwitch(int)
    }
    fun start(){limelight.start()}


    val llResult: LLResult = limelight.latestResult



}