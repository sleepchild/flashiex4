package sleepchild.flashiex4;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.content.Context;

public class FlashieTiles extends TileService
{
    private Flashie flash;
    private Context ctx;
    private Tile mTile;

    /*
    @Override
    public void onCreate() {
        super.onCreate();
        //
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        //
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        //
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        //
    }
    //*/

    @Override
    public void onClick() {
        super.onClick();
        init();
        if(flash.isOn(Flashie.SWTCH.BACK)){
            flash.turnOff(Flashie.SWTCH.BACK);
            setActive(false);
        }else{
            flash.turnOnFull(Flashie.SWTCH.BACK);
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
