package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D

class Pinpoint(hardwareMap: HardwareMap, name: String = "pinpoint") {

    private val odo = hardwareMap.get(name) as GoBildaPinpointDriver

    init {
        odo.setOffsets(-2.125, 1.625, DistanceUnit.INCH)
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
        odo.setEncoderDirections(
            GoBildaPinpointDriver.EncoderDirection.FORWARD,
            GoBildaPinpointDriver.EncoderDirection.FORWARD
        )
        odo.resetPosAndIMU()
    }
    val pos
        get()=odo.position
    fun update() {odo.update()}
    fun resetPosAndIMU() {odo.resetPosAndIMU()}

}