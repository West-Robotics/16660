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
        dpadLeftBool.check(gamepad.dpad_left);
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
    public boolean dpad_UpOnce() {return 1 == dpadUpBool.returnValue();}
    public boolean dpad_Down() {return 0 < dpadDownBool.returnValue();}
    public boolean dpad_DownOnce() {return 1 == dpadDownBool.returnValue();}
    public boolean dpad_Right() {return 0 < dpadRightBool.returnValue();}
    public boolean dpad_RightOnce() {return 1 == dpadRightBool.returnValue();}
    public boolean dpad_Left() {return 0 < dpadLeftBool.returnValue();}
    public boolean dpad_LeftOnce() {return 1 == dpadLeftBool.returnValue();}
    public boolean a() {return 0 < aBool.returnValue();}
    public boolean aOnce() {return 1 == aBool.returnValue();}
    public boolean b() {return 0 < bBool.returnValue();}
    public boolean bOnce() {return 1 == bBool.returnValue();}
    public boolean x() {return 0 < xBool.returnValue();}
    public boolean xOnce() {return 1 == xBool.returnValue();}
    public boolean y() {return 0 < yBool.returnValue();}
    public boolean yOnce() {return 1 == yBool.returnValue();}
    public boolean leftBumper() {return 0 < leftBumperBool.returnValue();}
    public boolean leftBumperOnce() {return 1 == leftBumperBool.returnValue();}
    public boolean rightBumper() {return 0 < rightBumperBool.returnValue();}
    public boolean rightBumperOnce() {return 1 == rightBumperBool.returnValue();}
    public boolean start() {return 0 < startBool.returnValue();}
    public boolean startOnce() {return 1 == startBool.returnValue();}
    public boolean back() {return 0 < backBool.returnValue();}
    public boolean backOnce() {return 1 == backBool.returnValue();}
    public boolean left_stick_button() {return 0 < leftStickButtonBool.returnValue();}
    public boolean left_stick_buttonOnce() {return 1 == leftStickButtonBool.returnValue();}
    public boolean right_stick_button() {return 0 < rightStickButtonBool.returnValue();}
    public boolean right_stick_buttonOnce() {return 1 == rightStickButtonBool.returnValue();}




}
