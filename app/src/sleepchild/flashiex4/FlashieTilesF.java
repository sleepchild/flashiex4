package sleepchild.flashiex4;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.content.Context;

public class FlashieTilesF extends TileService
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
        // TODO: Implement this method
        super.onTileAdded();
        init();
        setActive(false);
    }
    
    
    @Override
    public void onClick() {
        super.onClick();
        init();
        if(flash.isOn(Flashie.SWTCH.FRONT)){
            int ov = flash.getIntValue(Flashie.LEDS.F_WHITE);
            if(ov==b1){
                flash.turnOnFront(b2);
            }else if(ov==b2){
                flash.turnOnFront(b3);
            }else{
                flash.turnOffFront();
                setActive(false); 
            }
        }else{
            flash.turnOnFront(b1);
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
