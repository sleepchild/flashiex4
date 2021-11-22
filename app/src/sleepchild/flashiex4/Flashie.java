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
    }

    public static class SWTCH{
        final static String BACK = " /sys/class/leds/led:switch_0/brightness;";
        final static String FRONT = " /sys/class/leds/led:switch_1/brightness;";
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
            return false;
        }
        int stat = Integer.valueOf(str);
        if(stat!=0){
            return true;
        }
        return false;
    }
    
    public void turnOnFull(String SWTCH){
        su.exec("echo 0 > "+ SWTCH);
        ledValue(LEDS.B_WHITE, LED_MAX);
        ledValue(LEDS.B_YELLOW, LED_MAX);
        su.exec("echo 1 > "+ SWTCH);
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
    
}
