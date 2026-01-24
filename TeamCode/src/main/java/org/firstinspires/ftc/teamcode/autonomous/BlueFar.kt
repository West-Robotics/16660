package org.firstinspires.ftc.teamcode.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Blue Far")
class BlueFar : PedroOpMode(){
    override var startPose: Pose = Pose(56.0,8.0,Math.toRadians(270.0))
    override var shootPose: Pose = Pose(60.01976935749587,15.894563426688618,Math.toRadians(-66.0))
    override var parkPose: Pose = Pose(59.308072487644125,37.2454695222405,Math.toRadians(180.0))
}