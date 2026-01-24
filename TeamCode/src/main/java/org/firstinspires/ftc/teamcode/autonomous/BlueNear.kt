package org.firstinspires.ftc.teamcode.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Blue Near")
class BlueNear: PedroOpMode() {
    override var startPose: Pose = Pose(21.60131795716643,125.42998352553546,Math.toRadians(234.0))
    override var shootPose: Pose = Pose(60.494233937397034,83.03130148270182,Math.toRadians(314.0))
    override var parkPose: Pose = Pose(60.485996705107084,59.243822075782546,Math.toRadians(180.0))
}