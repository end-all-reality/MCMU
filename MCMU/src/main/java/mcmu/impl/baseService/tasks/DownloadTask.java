package mcmu.impl.baseService.tasks;

import mcmu.IMCMU;
import mcmu.api.CompatOverride;
import mcmu.impl.baseService.containers.DLOBJ;
import mcmu.utils.Utils;

import java.io.*;

public class DownloadTask implements Runnable {
    private String fileName;
    private DLOBJ dlobj;
    private IMCMU mcmu;
    private String name;
    public DownloadTask(String name, DLOBJ obj, IMCMU mcmu) {
        this.fileName = obj.Folder + name + obj.Ext;
        this.name = name;
        this.dlobj = obj;
        this.mcmu = mcmu;
    }
    @Override
    public void run() {
        if(mcmu.getSide().isClient() && dlobj.Side.isClient() || mcmu.getSide().isServer() == dlobj.Side.isServer()) {
            File fil = new File(this.fileName);
            File fild = new File(this.fileName + ".disabled");
            File ufd;
            if(fild.exists()) {
                ufd = fild;
            } else {
                ufd = fil;
            }
            if(dlobj.Override == CompatOverride.DISABLE) {
                ufd.renameTo(fild);
                ufd = fild;
            } else if(dlobj.Override == CompatOverride.ENABLE) {
                ufd.renameTo(fil);
                ufd = fil;
            }
            try {
                if(ufd.exists() && Utils.MD5(new FileInputStream(ufd)).equals(dlobj.Hash)) {
                    return;
                } else {
                    System.out.println("Downloading file: "+this.name);
                    FileOutputStream fos = new FileOutputStream(ufd);
                    fos.write(Utils.getFile(dlobj.URL));
                    fos.close();
                    System.out.println("Finished downloading: " + this.name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
