package mcmu.downloader;

import mcmu.MCMU;
import mcmu.downloader.containers.DLOBJ;
import mcmu.downloader.containers.RMOBJ;
import mcmu.utils.FileList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModLoader
{
  public ModLoader()
  {
    ExecutorService executor = Executors.newFixedThreadPool(8);
    for (FileList files : MCMU.filst.values()) {
      for (DLOBJ fil : files.files) {
        Runnable worker = new DownloadThread(fil);
        executor.execute(worker);
      }
      for(RMOBJ fil: files.rmfiles) {
        Runnable worker = new RemoveThread(fil);
        executor.execute(worker);
      }
    }
    executor.shutdown();
    while(!executor.isTerminated());
    System.out.println("Files Downloaded");
  }
}