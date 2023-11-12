import java.text.SimpleDateFormat;
import java.util.*;

public class Lap {
    private Set<Sector> sectors;
    private Date start;
    private Date finish;
    private Date lapTime;
    private int id;

    public Set<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }

    public void addSector(Sector sector) {
        Set<Sector> sectorList =
                Optional.ofNullable(sectors)
                        .orElse(new LinkedHashSet<>());

        sectorList.add(sector);

        sectors = sectorList;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Date getLapTime() {
        return lapTime;
    }

    public void setLapTime(Date lapTime) {
        this.lapTime = lapTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat simpleDateFormatLap = new SimpleDateFormat("mm:ss.SSS");
        return "Lap{" +
                "sectors=" + sectors +
                ", start=" + simpleDateFormat.format(start) +
                ", finish=" + simpleDateFormat.format(finish) +
                ", lapTime=" + simpleDateFormatLap.format(lapTime) +
                ", id=" + id +
                '}';
    }
}
