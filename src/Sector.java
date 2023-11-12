import java.text.SimpleDateFormat;
import java.util.*;

public class Sector {
    private Set<Section> sections;
    private Date start;
    private Date finish;
    private Date sectorTime;
    private int id;

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        Set<Section> sectionList =
                Optional.ofNullable(sections)
                        .orElse(new LinkedHashSet<>());

        sectionList.add(section);

        sections = sectionList;
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

    public Date getSectorTime() {
        return sectorTime;
    }

    public void setSectorTime(Date sectorTime) {
        this.sectorTime = sectorTime;
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
        return "Sector{" +
                "sections=" + sections +
                ", start=" + simpleDateFormat.format(start) +
                ", finish=" + simpleDateFormat.format(finish) +
                ", sectorTime=" + simpleDateFormatLap.format(sectorTime) +
                ", id=" + id +
                '}';
    }
}
