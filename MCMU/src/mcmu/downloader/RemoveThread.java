package mcmu.downloader;

import mcmu.downloader.containers.RMOBJ;

import java.io.File;

/**
 * Created by bradl on 2/28/2016.
 */
public class RemoveThread implements Runnable {
    protected RMOBJ rmobj;
    public RemoveThread(RMOBJ obj) {
        this.rmobj = obj;
    }
    @Override
    public void run() {
        File rmFile = new File(this.rmobj.Folder + "/"+ this.rmobj.FileName + "/" + this.rmobj.Ext);
        if(rmFile.exists()) {
            System.out.println("Removing mod: " + this.rmobj.FileName);
            rmFile.delete();
            System.out.println("Removed mod: " + this.rmobj.FileName);
        }
    }
}