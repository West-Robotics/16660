package org.firstinspires.ftc.teamcode.FTC.TeleOp.Scrim.backups

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.util.CRServoConstants
import com.qualcomm.robotcore.hardware.ColorSensor


class backup1 : LinearOpMode(){
    override fun runOpMode() {
        val intake = motorSetup(
            hardwareMap,
            "intake",
            DcMotorSimple.Direction.FORWARD,
            DcMotor.ZeroPowerBehavior.BRAKE
        )
        val spindexer = CRservoSetup(hardwareMap, "spindexer", CRServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        val controller2 = Controller(gamepad2)
        val drive = drivetrain(hardwareMap,telemetry)
        val sensor = hardwareMap.get("color") as ColorSensor
        val yeeter1 = CRservoSetup(hardwareMap,"yeeter1", CRServoConstants.GOBILDA_SPEED,
            DcMotorSimple.Direction.REVERSE)
        val yeeter2 = CRservoSetup(hardwareMap,"yeeter2", CRServoConstants.GOBILDA_SPEED,
            DcMotorSimple.Direction.FORWARD)

        val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        var atoggle = true
        var toggle = false
        var btoggle = false
        waitForStart()
        while (opModeIsActive()) {

            controller1.update()
            controller2.update()
            drive.mecanumEffort(controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            spindexer.effort = controller2.right_stick_x
            launcher.effort = controller2.left_stick_y
            if (controller2.bOnce()){
                btoggle = !btoggle
            }
            if(btoggle){
                intake.effort = 1.0
            } else{
                intake.effort = 0.0
            }

            if (controller2.aOnce()){
                toggle = !toggle
            }
            if (toggle){
                yeeter2.effort = 1.0
                yeeter1.effort = 1.0
            } else{
                yeeter2.effort = 0.0
                yeeter1.effort = 0.0
            }
            telemetry.addData("Red",sensor.red())
            telemetry.addData("Green",sensor.green())
            telemetry.addData("Blue",sensor.blue())
            telemetry.addData("yeeter1",yeeter1.effort)
            telemetry.addData("yeeter2",yeeter2.effort)
            telemetry.addData("intake",intake.effort)
            telemetry.addLine("If you are reading this use backup2 controls are better and only one controller")
            telemetry.update()
            drive.write()
            intake.write()
            spindexer.write()
            launcher.write()
            yeeter1.write()
            yeeter2.write()

        }
    }
}