package sleepchild.flashiex4;
import java.io.*;
import java.util.*;

public class SU
{
    Process proc;
    InputStream instream, errs;
    DataOutputStream out;
    DataInputStream inp;
    Thread gobble;
    List<String> spooler = new ArrayList<>();
    
    
    public SU(CB cblistener){
        try
        {
            proc = Runtime.getRuntime().exec("su");
            out = new DataOutputStream( proc.getOutputStream());
            instream = proc.getInputStream();
            inp = new DataInputStream(instream);
            errs = proc.getErrorStream();
            //
            // gobble = new Thread(new Gobler(ins, errs, cblistener));
            // gobble.start();
        }
        catch (IOException e)
        {
            cblistener.onValue("",e.getMessage());
        }
    }
    
    public void exec(String cmd){
        exec(cmd,null);
    }
    
    public void exec(String cmd, CB cblistener){
        try
        {   
            out.writeBytes(cmd + "\n");
            out.flush();
        }
        catch (IOException e)
        {
            cblistener.onValue("",e.getMessage());
        }
    }
    
    public void exit(){
        try{
            out.writeBytes("exit\n");
            out.flush();
        }
        catch (IOException e)
        {}
    }
    
    // todo: needs more testing/ fixing
    // i only tested for one line outputs
    // so for now, only use for (known) single line output cmds
    // using cmds that out multiline outputs, e.g 'ls', WILL NOT work as intended.
    // ? a look into Threads maybe ?
    //
    public String getVal(String cmd){
        try {
            out.writeBytes(cmd + "\n");
            out.flush();
            
            return inp.readLine();
        } catch (IOException e) {}
        return "";
    }
    
    //ensure the input stream is cleared so we dont read old messages.
    public void emptyInp(){
        String waste;
        try {
            while ((waste = inp.readLine()) != null) {
                // dump unread messages somewhere for later reference
                spooler.add(waste);
            }
        } catch (IOException e) {}
    }
    
    // 
    public interface CB{
        public void onValue(final String cmd, final String result);
    }
    //
}
