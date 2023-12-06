import java.util.Date;
import java.util.Random;

public class Simulator implements Runnable{
    private Event event;
    private Driver driver;
    private Recorder recorder;
    private int startDelay;

    public  Simulator(Event event, Driver driver, Recorder recorder, int startDelay) {
        this.event = event;
        this.driver = driver;
        this.recorder = recorder;
        this.startDelay = startDelay;
    }
    @Override
    public void run() {
        int driverLaps = 0;
        Date lapStart = null;

        try {
            Thread.sleep(startDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(event.getFinish() == null || driverLaps < event.getLaps()) {
            driverLaps++;
            lapStart = (lapStart == null) ? new Date() : lapStart;

            Lap lap = new Lap();
            lap.setStart(lapStart);
            lap.setId(driverLaps);

            Date sectorStart = lapStart;
            Date sectorFinish = null;
            for (int i = 1 ; i <= event.getTrack().getSectors() ; i++) {
                Sector sector = new Sector();
                sector.setId(i);
                sector.setStart(sectorStart);

                Date sectionStart = sectorStart;
                Date sectionFinish = null;
                for(int j = 1 ; j <= 10 ; j++) {
                    Section section = new Section();
                    section.setId((i*10)+j);
                    section.setStart(sectionStart);

                    try {
                        Thread.sleep(30 + new Random().nextInt(40));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sectionFinish = new Date();
                    section.setFinish(sectionFinish);
                    section.setSectionTime(new Date(sectionFinish.getTime() - sectionStart.getTime()));

                    sector.addSection(section);
                    lap.addSector(sector);

                    recorder.addLap(driver, lap);
                    sectionStart = sectionFinish;
                }

                sectorFinish = sectionFinish;
                sector.setFinish(sectorFinish);
                sector.setSectorTime(new Date(sectorFinish.getTime() - sectorStart.getTime()));

                lap.addSector(sector);

                recorder.addLap(driver, lap);
                sectorStart = sectorFinish;
            }

            Date lapFinish = sectorFinish;
            lap.setFinish(lapFinish);
            lap.setLapTime(new Date(lapFinish.getTime() - lapStart.getTime()));

            recorder.addLap(driver, lap);
            lapStart = lapFinish;

            if(driverLaps == event.getLaps()) {
                event.setFinish(new Date());
            }
        }
    }
}
