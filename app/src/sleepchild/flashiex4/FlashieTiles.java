package sleepchild.flashiex4;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.content.Context;

public class FlashieTiles extends TileService
{
    private Flashie flash;
    private Context ctx;
    private Tile mTile;
    private final int b1 = 30;
    private final int b2 = 112;
    private final int b3 = 225;

    @Override
    public void onTileAdded()
    {
        super.onTileAdded();
        init();
        setActive(false);
    }
    
    

    @Override
    public void onClick() {
        super.onClick();
        init();
        if(flash.isOn(Flashie.SWTCH.BACK)){
            int lv = flash.getIntValue(Flashie.LEDS.B_WHITE);
            if(lv==b1){
                flash.turnOnBack(b2);
            }else if(lv==b2){
                flash.turnOnBack(b3);
            }else{
                flash.turnOff(Flashie.SWTCH.BACK);
                setActive(false);
            }
        }else{
            flash.turnOnBack(b1);
            setActive(true);
        }
    }
    
    private int getState(){
        return mTile.getState();
    }
    
    private void setActive(boolean active){
        if(active){
            mTile.setState(Tile.STATE_ACTIVE);
        }else{
            mTile.setState(Tile.STATE_INACTIVE);
        }
        mTile.updateTile();
    }
    
    private boolean isActive(){
        return getState() == Tile.STATE_ACTIVE;
    }
    
    private void init(){
        mTile = getQsTile();
        ctx = getApplicationContext();
        //
        if(flash==null){
            flash = new Flashie();
        }
    }
    
    
}
