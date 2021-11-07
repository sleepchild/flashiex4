package sleepchild.flashiex4;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import java.util.*;

/*
    flashie X4 - torch for the moto X4
//*/

// this is a huge mess
// needs re-writting
//
public class MainActivity extends Activity implements SU.CB{
    SU su;
//    Handler handle = new Handler();
    //final int LED_OFF = 0;
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
        su = new SU(this);
    }
    
    /*
        - the moto X4 has 3 main leds; 2 on the back, 1 on the front;
        - it also has a 4rth led on the front; locayted between the front facing led and the speaker;
        //
        i just cannot think of a usecase for the 4rth led;, so we'll only use the 3 main leds;
        
    //*/
    void init(){
        
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
            case R.id.led_front:
                frontOnOff();
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
                if(isOn(SWTCH.FRONT)){
                    frontOnOff();
                    frontOnOff();
                }
                break;
            default:
                brightnessB = value;
                if(isOn(SWTCH.BACK)){
                    switchOff(SWTCH.BACK);
                    if(isOn(LEDS.B_WHITE) && isOn(LEDS.B_YELLOW)){
                        ledValue(LEDS.B_WHITE, brightnessB);
                        ledValue(LEDS.B_YELLOW, brightnessB);
                        //
                    }else if(isOn(LEDS.B_WHITE)){
                        ledValue(LEDS.B_WHITE, brightnessB);
                    }else if(isOn(LEDS.B_YELLOW)){
                        ledValue(LEDS.B_YELLOW, brightnessB);
                    }
                    switchOn(SWTCH.BACK);
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
        if(isOn(SWTCH.BACK)){
            if(isOn(LEDS.B_YELLOW) && isOn(LEDS.B_WHITE)){
                yellowOnOnly();
            }else if(isOn(LEDS.B_WHITE)){
                yellowOnOnly();
            }else{
                ledOff(LEDS.B_YELLOW);
                ledOff(LEDS.B_WHITE);
                switchOff(SWTCH.BACK);
            }
        }else{
            yellowOnOnly();
        }
    }
    
    void yellowOnOnly(){
        switchOff(SWTCH.BACK);
        ledOff(LEDS.B_WHITE);
        ledValue(LEDS.B_YELLOW, brightnessB);
        switchOn(SWTCH.BACK);
    }
    
    void whiteOnoff(){
        if(isOn(SWTCH.BACK)){
            if(isOn(LEDS.B_YELLOW) && isOn(LEDS.B_WHITE)){
                whiteOnOnly();
            }else if(isOn(LEDS.B_YELLOW)){
                whiteOnOnly();
            }else{
                ledOff(LEDS.B_YELLOW);
                ledOff(LEDS.B_WHITE);
                switchOff(SWTCH.BACK);
            }
        }else{
            whiteOnOnly();
        }
    }
    
    void whiteOnOnly(){
        switchOff(SWTCH.BACK);
        ledOff(LEDS.B_YELLOW);
        ledValue(LEDS.B_WHITE, brightnessB);
        switchOn(SWTCH.BACK);
    }
    
    void yellowWhite(){
        if(isOn(SWTCH.BACK)){
            if(isOn(LEDS.B_WHITE) && isOn(LEDS.B_YELLOW)){
                switchOff(SWTCH.BACK);
            }else{
                switchOff(SWTCH.BACK);
                ledValue(LEDS.B_WHITE, brightnessB);
                ledValue(LEDS.B_YELLOW, brightnessB);
                switchOn(SWTCH.BACK);
            }
        }else{
            ledValue(LEDS.B_WHITE, brightnessB);
            ledValue(LEDS.B_YELLOW, brightnessB);
            switchOn(SWTCH.BACK);
        }
    }
    
    void frontOnOff(){
        if(isOn(SWTCH.FRONT)){
            ledOff(LEDS.F_WHITE);
            switchOff(SWTCH.FRONT);
        }else{
            ledValue(LEDS.F_WHITE, brightnessF);
            switchOn(SWTCH.FRONT);
        }
    }
    
    void ledValue(String ledPath, int brightness){
        su.exec("echo "+ brightness +" > "+ ledPath+"");
    }
    
    void ledOff(String leds){
        su.exec("echo 0 > "+ leds);
    }
    
    void switchOn(String swtch){
        su.exec("echo 1 > "+ swtch);
    }
    
    void switchOff(String swtch){
        su.exec("echo 0 > "+swtch);
    }
    
    //
    // determine on/off status of path
    // where path is led os switch
    // @param: path: the path of the LEDS/SWTCH
    //
    boolean isOn(String path){
        String str = su.getVal("cat "+path);
        if(str.isEmpty()){
            return false;
        }
        int stat = Integer.valueOf(str);
        if(stat!=0){
            return true;
        }
        return false;
    }
    
    void allOff(){
        // leds off
        ledOff(LEDS.B_WHITE);
        ledOff(LEDS.B_YELLOW);
        ledOff(LEDS.F_WHITE);
        // swtch off
        switchOff(SWTCH.BACK);
        switchOff(SWTCH.FRONT);
    }
       
    class LEDS{
        final static String B_YELLOW = " /sys/class/leds/led:torch_1/brightness;";
        final static String B_WHITE = " /sys/class/leds/led:torch_0/brightness;" ;
        final static String F_WHITE = " /sys/class/leds/led:torch_2/brightness;";
    }
    
    class SWTCH{// swtch not switch
        final static String BACK = " /sys/class/leds/led:switch_0/brightness;";
        final static String FRONT = " /sys/class/leds/led:switch_1/brightness;";
    }

    @Override
    protected void onDestroy()
    {
        su.exec("exit\n",null);
        super.onDestroy();
    }
    
    
    
    
}
