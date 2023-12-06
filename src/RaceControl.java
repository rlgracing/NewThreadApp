import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class RaceControl {

    public static void main(String[] args) {

//            public  Simulator(Event event, Track track, Driver driver, Recorder recorder) {
//
        Track track = new Track();
        track.setId(1);
        track.setLength(4700);
        track.setSectors(3);

        Event event = new Event();
        event.setId(1);
        event.setLaps(10);
//        event.setStart();
//        event.setFinish();
        event.setTrack(track);

        Recorder recorder = new Recorder();
        int threadPool = 20;

        Thread[] threads = new Thread[threadPool];
        for (int i = 0; i < threadPool; i++) {
            Driver driver = new Driver();
            driver.setId(i+1);
            threads[i] = new Thread(new Simulator(event, driver, recorder, 1000*i));
            threads[i].start();
        }

        try {
            for(Thread thread: threads) {
                thread.join();
            }
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }

//        System.out.println("Final count: " + recorder.getResult());
        System.out.println("\nBEST LAPS: " +
                recorder.getBestLaps()
                        .entrySet()
                        .stream()
                        .sorted((e1, e2) -> e1.getValue().getLapTime().compareTo(e2.getValue().getLapTime()))
                        .collect(Collectors.toMap(key -> key.getKey().getId(), value -> new SimpleDateFormat("mm:ss.SSS").format(value.getValue().getLapTime()),
                                (e1, e2) -> e1, LinkedHashMap::new)));

        Driver fastestLapDriver = recorder.getOverallBestLaps().keySet().stream().findFirst().orElse(null);
        Lap fastestLap = recorder.getOverallBestLaps().values().stream().findFirst().orElse(null);
        System.out.println("FASTEST LAP: "+fastestLapDriver.getId()+" - "+fastestLap.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(fastestLap.getLapTime()));

        Driver fastestSector1Driver = recorder.getBestSector1().keySet().stream().findFirst().orElse(null);
        Lap lapFastestSector1 = recorder.getBestSector1().values().stream().findFirst().orElse(null);
        Sector fastestSector1 =
                lapFastestSector1.getSectors().stream()
                        .filter(sector -> sector.getId() == 1)
                        .findFirst()
                        .orElse(null);
        System.out.println("FASTEST SECTOR 1: "+fastestSector1Driver.getId()+" - "+lapFastestSector1.getId()+" - "+fastestSector1.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(fastestSector1.getSectorTime()));

        Driver fastestSector2Driver = recorder.getBestSector2().keySet().stream().findFirst().orElse(null);
        Lap lapFastestSector2 = recorder.getBestSector2().values().stream().findFirst().orElse(null);
        Sector fastestSector2 =
                lapFastestSector2.getSectors().stream()
                        .filter(sector -> sector.getId() == 2)
                        .findFirst()
                        .orElse(null);
        System.out.println("FASTEST SECTOR 2: "+fastestSector2Driver.getId()+" - "+lapFastestSector2.getId()+" - "+fastestSector2.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(fastestSector2.getSectorTime()));

        Driver fastestSector3Driver = recorder.getBestSector3().keySet().stream().findFirst().orElse(null);
        Lap lapFastestSector3 = recorder.getBestSector3().values().stream().findFirst().orElse(null);
        Sector fastestSector3 =
                lapFastestSector3.getSectors().stream()
                        .filter(sector -> sector.getId() == 3)
                        .findFirst()
                        .orElse(null);
        System.out.println("FASTEST SECTOR 3: "+fastestSector3Driver.getId()+" - "+lapFastestSector3.getId()+" - "+fastestSector3.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(fastestSector3.getSectorTime()));
        System.out.println("IDEAL LAP: "+ new SimpleDateFormat("mm:ss.SSS").format(new Date(fastestSector1.getSectorTime().getTime()+fastestSector2.getSectorTime().getTime()+fastestSector3.getSectorTime().getTime())));

    }
}
