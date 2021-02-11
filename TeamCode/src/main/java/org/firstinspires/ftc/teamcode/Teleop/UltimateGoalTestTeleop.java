package org.firstinspires.ftc.teamcode.Teleop;//package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
//import org.firstinspires.ftc.teamcode.lib.AutonomousConstants;
//import org.firstinspires.ftc.teamcode.lib.ClampState;
//import org.firstinspires.ftc.teamcode.lib.GrabberState;
//import org.firstinspires.ftc.teamcode.lib.TeleOpConstants;
//import org.firstinspires.ftc.teamcode.lib.perceptron.CollisionExecutor;
import org.firstinspires.ftc.teamcode.RapidFire;
import org.firstinspires.ftc.teamcode.robotplus.hardware.IMUWrapper;
import org.firstinspires.ftc.teamcode.robotplus.hardware.MecanumDrive;
import org.firstinspires.ftc.teamcode.robotplus.hardware.MotorPair;
import org.firstinspires.ftc.teamcode.robotplus.hardware.Robot;


@TeleOp(name = "Ultimate Goal Teleop V:4.0", group = "Basic")
public class UltimateGoalTestTeleop extends OpMode {
    private Robot robot;
    private MecanumDrive mecanumDrive;
    private DcMotor shooter1;
    private DcMotor shooter2;
    double firepower = 0.5;
    private DcMotor arm;
    private Servo claw;
    boolean clawstate = true;
    boolean hopstate = false;
    double firepowerstate = 0.0;
    private DcMotor intake;
    private CRServo hopperpush;
    private boolean intakeState;
    private double shooter1Power;



    @Override
    public void init() {

        this.robot = new Robot(hardwareMap);
        this.mecanumDrive = (MecanumDrive) this.robot.getDrivetrain();
        this.shooter1 = hardwareMap.get(DcMotor.class, "shooter1");
        this.shooter2 = hardwareMap.get(DcMotor.class, "shooter2");
        this.arm = hardwareMap.get(DcMotor.class, "arm");
        this.claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(1);
        this.hopperpush = hardwareMap.get(CRServo.class, "hopperpush");
        this.intake = hardwareMap.get(DcMotor.class, "intake");
        intakeState = false;
    }



    @Override
    public void loop() {

        telemetry.update();

        //hopper + feeding control
        if(gamepad1.right_trigger>0){
            hopperpush.setPower(-0.75);
            shooter2.setPower(1);
        }
        else if(gamepad1.a){
            hopperpush.setPower(0.75);
            shooter2.setPower(0); // TODO: 2/11/2021 maybe change this to -1 to push rings back? testing required!
        }
        else{
            hopperpush.setPower(0);
            shooter2.setPower(0);
        }



        //intake control
        if(gamepad2.right_bumper || gamepad1.right_bumper){
            intake.setPower(1);
        }
        else if(gamepad1.b || gamepad2.b){
            intake.setPower(-1);
        }
        else{
            intake.setPower(0);
        }



       //override shooter power
        if(gamepad1.left_bumper){
            shooter1Power = 0.7; // TODO: 2/11/2021 change this to be a power drivers are happy with
        }
        else{
            shooter1Power = 0.77; // TODO: 2/11/2021 change this to be a power drivers are happy with
        }


        //activate shooter
        if (gamepad1.left_trigger > 0){
            shooter1.setPower(shooter1Power);
        } else {
            shooter1.setPower(shooter1Power);
        }

        //control over drivetrain
        this.mecanumDrive.complexDrive(-gamepad1.right_stick_x, -gamepad1.left_stick_y, -gamepad1.left_stick_x, telemetry);

        //extend or retract wobble arm
        arm.setPower(gamepad2.left_stick_y); // TODO: 2/11/2021 might need to reverse again

        // Wobble Goal Claw
        if (gamepad2.a) {
            telemetry.addLine("Wobble Claw Used");
            claw.setPosition(clawstate ? 0.63 : 1);
            clawstate = !clawstate;
            try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }



    } //end loop
} //end class

