package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Red Near")
class RedNear: PedroTestOpMode() {
    override var startPose: Pose = Pose(125.50906095551898,60.585,Math.toRadians(307.0))
    override var shootPose: Pose = Pose(84.455,75.914,Math.toRadians(230.0))
    override var parkPose: Pose = Pose(81.778,37.2454695222405,Math.toRadians(0.0))
}