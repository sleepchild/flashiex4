package sleepchild.flashiex4;

public class Flashie
{
    private SU su;
    
    final static int LED_MIN = 20;
    final static int LED_MAX = 225;
    
    public static class LEDS{
        final static String B_YELLOW = " /sys/class/leds/led:torch_1/brightness;";
        final static String B_WHITE = " /sys/class/leds/led:torch_0/brightness;" ;
        final static String F_WHITE = " /sys/class/leds/led:torch_2/brightness;";
        //final static String F_TINY = "";// use swtch
    }

    public static class SWTCH{
        final static String BACK = " /sys/class/leds/led:switch_0/brightness;";
        final static String FRONT = " /sys/class/leds/led:switch_1/brightness;";
        final static String FRONT_TINY = "/sys/class/leds/charging/brightness";
    }
    
    public Flashie(){
        su = new SU(null);
    }
    
    //
    // determine on/off status of flashlight
    // @param: path: the path of the LEDS or SWTCH
    //
    public boolean isOn(String path){
        String str = su.getVal("cat "+path);
        if(str.isEmpty()){
            //ok, but why?
            return false;
        }
        int stat = Integer.valueOf(str);
        if(stat!=0){
            return true;
        }
        return false;
    }
    
    public String getVal(String cmd){
        return su.getVal(cmd);
    }
    
    public int getIntValue(String path){
        String v = su.getVal("cat "+path);
        int r = Integer.valueOf(v);
        return r;
    }
    
    public void turnOnFront(int brightness){
        switchOff(SWTCH.FRONT);
        ledValue(LEDS.F_WHITE, brightness);
        switchOn(SWTCH.FRONT);
    }
    
    public void turnOffFront(){
        ledOff(LEDS.F_WHITE);
        switchOff(SWTCH.FRONT);
    }
    
    public void switchOn(String SWTCH){
        su.exec("echo 1 > "+ SWTCH);
    }
    
    public void switchOff(String SWTCH){
        su.exec("echo 0 > "+ SWTCH);
    }
    
    public void turnOnBack(int bright){
        su.exec("echo 0 > "+ SWTCH.BACK);
        ledValue(LEDS.B_WHITE, bright);
        ledValue(LEDS.B_YELLOW, bright);
        switchOn(SWTCH.BACK);
    }
    
    public void turnOff(String SWTCH){
        ledOff(LEDS.B_WHITE);
        ledOff(LEDS.B_YELLOW);
        su.exec("echo 0 > "+ SWTCH);
    }
    
    public void ledValue(String ledPath, int brightness){
        su.exec("echo "+ brightness +" > "+ ledPath+"");
    }

    public void ledOff(String leds){
        su.exec("echo 0  > "+ leds);
    }
    
    public void exit(){
        su.exec("exit\n",null);
    }
    
}
