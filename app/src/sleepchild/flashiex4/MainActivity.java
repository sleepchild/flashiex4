package sleepchild.flashiex4;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import java.util.*;

/*
    flashie X4 - torch for the moto X4
    
     - the moto X4 has 3 main leds; 2 on the back, 1 on the front;
     - it also has a 4rth led on the front; located between the front facing led and the speaker;

 //*/

// this is a huge mess
// needs re-writting
//
public class MainActivity extends Activity implements SU.CB{
    //
    Flashie flash;
    final int LED_MIN = 20;
    
    final int MAX_BRIGHTNESS = 225;
    
    int brightnessB= LED_MIN,
        brightnessF = LED_MIN;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        init();
        flash = new Flashie();
    }
    
    
    void init(){
        //
    }
    
    // this is set in activity_main.xml
    public void ledonoff(View v){
        int id = v.getId();
        switch(id){
            case R.id.led_yellow:
                yellowOnOff();
                break;
            case R.id.led_white:
                whiteOnoff();
                break;
            case R.id.led_dualwy:
                yellowWhite();
                break;
            case R.id.led_front_lrg:
                frontOnOff();
                break;
            case R.id.led_front_sml:
                frontSmlOnOff();
                break;
            case R.id.led_alloff:
                 allOff();
                 break;
        }
    }
    
    public void radioClick(View v){
        int value = Integer.valueOf( v.getTag().toString());
        int id = v.getId();
        switch(id){
            case R.id.rad_f100:
            case R.id.rad_f75:
            case R.id.rad_f50:
            case R.id.rad_f25:
                brightnessF = value;
                if(isOn(Flashie.SWTCH.FRONT)){
                    frontOnOff();
                    frontOnOff();
                }
                break;
            default:
                brightnessB = value;
                if(isOn(Flashie.SWTCH.BACK)){
                    switchOff(Flashie.SWTCH.BACK);
                    if(isOn(Flashie.LEDS.B_WHITE) && isOn(Flashie.LEDS.B_YELLOW)){
                        ledValue(Flashie.LEDS.B_WHITE, brightnessB);
                        ledValue(Flashie.LEDS.B_YELLOW, brightnessB);
                        //
                    }else if(isOn(Flashie.LEDS.B_WHITE)){
                        ledValue(Flashie.LEDS.B_WHITE, brightnessB);
                    }else if(isOn(Flashie.LEDS.B_YELLOW)){
                        ledValue(Flashie.LEDS.B_YELLOW, brightnessB);
                    }
                    switchOn(Flashie.SWTCH.BACK);
                }
                break;
        }
    }

    @Override
    public void onValue(final String cmd, final String result) {
        runOnUiThread(new Runnable(){
            public void run(){
                toast(result);
            }
        });
    }
    
    private void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, 500).show();
    }
    
    void yellowOnOff(){
        if(isOn(Flashie.SWTCH.BACK)){
            if(isOn(Flashie.LEDS.B_YELLOW) && isOn(Flashie.LEDS.B_WHITE)){
                yellowOnOnly();
            }else if(isOn(Flashie.LEDS.B_WHITE)){
                yellowOnOnly();
            }else{
                ledOff(Flashie.LEDS.B_YELLOW);
                ledOff(Flashie.LEDS.B_WHITE);
                switchOff(Flashie.SWTCH.BACK);
            }
        }else{
            yellowOnOnly();
        }
    }
    
    void yellowOnOnly(){
        switchOff(Flashie.SWTCH.BACK);
        ledOff(Flashie.LEDS.B_WHITE);
        ledValue(Flashie.LEDS.B_YELLOW, brightnessB);
        switchOn(Flashie.SWTCH.BACK);
    }
    
    void whiteOnoff(){
        if(isOn(Flashie.SWTCH.BACK)){
            if(isOn(Flashie.LEDS.B_YELLOW) && isOn(Flashie.LEDS.B_WHITE)){
                whiteOnOnly();
            }else if(isOn(Flashie.LEDS.B_YELLOW)){
                whiteOnOnly();
            }else{
                ledOff(Flashie.LEDS.B_YELLOW);
                ledOff(Flashie.LEDS.B_WHITE);
                switchOff(Flashie.SWTCH.BACK);
            }
        }else{
            whiteOnOnly();
        }
    }
    
    void whiteOnOnly(){
        switchOff(Flashie.SWTCH.BACK);
        ledOff(Flashie.LEDS.B_YELLOW);
        ledValue(Flashie.LEDS.B_WHITE, brightnessB);
        switchOn(Flashie.SWTCH.BACK);
    }
    
    void yellowWhite(){
        if(isOn(Flashie.SWTCH.BACK)){
            if(isOn(Flashie.LEDS.B_WHITE) && isOn(Flashie.LEDS.B_YELLOW)){
                switchOff(Flashie.SWTCH.BACK);
            }else{
                switchOff(Flashie.SWTCH.BACK);
                ledValue(Flashie.LEDS.B_WHITE, brightnessB);
                ledValue(Flashie.LEDS.B_YELLOW, brightnessB);
                switchOn(Flashie.SWTCH.BACK);
            }
        }else{
            ledValue(Flashie.LEDS.B_WHITE, brightnessB);
            ledValue(Flashie.LEDS.B_YELLOW, brightnessB);
            switchOn(Flashie.SWTCH.BACK);
        }
    }
    
    void frontOnOff(){
        if(isOn(Flashie.SWTCH.FRONT)){
            ledOff(Flashie.LEDS.F_WHITE);
            switchOff(Flashie.SWTCH.FRONT);
        }else{
            ledValue(Flashie.LEDS.F_WHITE, brightnessF);
            switchOn(Flashie.SWTCH.FRONT);
        }
    }
    
    void frontSmlOnOff(){
        if(isOn(Flashie.SWTCH.FRONT_TINY)){
            switchOff(Flashie.SWTCH.FRONT_TINY);
        }else{
            ledValue(Flashie.SWTCH.FRONT_TINY, 150);
        }
    }
    
    void ledValue(String ledPath, int brightness){
        flash.ledValue(ledPath, brightness);
    }
    
    void ledOff(String leds){
        flash.ledOff(leds);
    }
    
    void switchOn(String swtch){
        flash.switchOn(swtch);
    }
    
    void switchOff(String swtch){
        flash.switchOff(swtch);
    }
    
    //
    // determine on/off status of path
    // where path is led os switch
    // @param: path: the path of the LEDS/SWTCH
    //
    boolean isOn(String path){
        int stat = flash.getIntValue(path);
        if(stat!=0){
            return true;
        }
        return false;
    }
    
    void allOff(){
        // leds off
        ledOff(Flashie.LEDS.B_WHITE);
        ledOff(Flashie.LEDS.B_YELLOW);
        ledOff(Flashie.LEDS.F_WHITE);
        // swtch off
        switchOff(Flashie.SWTCH.BACK);
        switchOff(Flashie.SWTCH.FRONT);
        switchOff(Flashie.SWTCH.FRONT_TINY);
    }

    @Override
    protected void onDestroy()
    {
        flash.exit();
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        // TODO: Implement this method
    }
    
    
}
