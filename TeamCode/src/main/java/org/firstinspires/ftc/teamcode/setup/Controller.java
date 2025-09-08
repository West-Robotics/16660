package org.firstinspires.ftc.teamcode.setup;

import com.qualcomm.robotcore.hardware.Gamepad;
public class Controller {

    public Gamepad gamepad;

    public Controller(Gamepad gamer) {
        gamepad = gamer;
    }

    private button dpadUpBool = new button(), dpadDownBool = new button(), dpadRightBool = new button(), dpadLeftBool = new button(), xBool = new button(), yBool = new button(), aBool = new button(), bBool = new button();

    private button leftStickButtonBool = new button(), rightStickButtonBool = new button(), leftBumperBool = new button(), rightBumperBool = new button(), startBool = new button(), backBool = new button();
    public double left_stick_x, left_stick_y, right_stick_x, right_stick_y;
    public double right_trigger, left_trigger;

    public void update(){
        dpadUpBool.check(gamepad.dpad_up);
        dpadDownBool.check(gamepad.dpad_down);
        dpadRightBool.check(gamepad.dpad_right);
        dpadDownBool.check(gamepad.dpad_down);
        xBool.check(gamepad.x);
        yBool.check(gamepad.y);
        aBool.check(gamepad.a);
        bBool.check(gamepad.b);
        leftStickButtonBool.check(gamepad.left_stick_button);
        rightStickButtonBool.check(gamepad.right_stick_button);
        leftBumperBool.check(gamepad.left_bumper);
        rightBumperBool.check(gamepad.right_bumper);
        startBool.check(gamepad.start);
        backBool.check(gamepad.back);
        this.left_stick_x = gamepad.left_stick_x;
        this.left_stick_y = gamepad.left_stick_y;
        this.right_stick_x = gamepad.right_stick_x;
        this.right_stick_y = gamepad.right_stick_y;
        this.right_trigger = gamepad.right_trigger;
        this.left_trigger = gamepad.left_trigger;

    }

    public boolean dpad_Up() {return 0 < dpadUpBool.returnValue();}
    public boolean dpad_UpOnce() {return 0 == dpadUpBool.returnValue();}
    public boolean dpad_Down() {return 0 < dpadDownBool.returnValue();}
    public boolean dpad_DownOnce() {return 0 == dpadDownBool.returnValue();}
    public boolean dpad_Right() {return 0 < dpadRightBool.returnValue();}
    public boolean dpad_RightOnce() {return 0 == dpadRightBool.returnValue();}
    public boolean dpad_Left() {return 0 < dpadLeftBool.returnValue();}
    public boolean dpad_LeftOnce() {return 0 == dpadLeftBool.returnValue();}






}
