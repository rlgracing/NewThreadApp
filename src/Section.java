import java.text.SimpleDateFormat;
import java.util.Date;

public class Section {
    private Date start;
    private Date finish;
    private Date sectionTime;
    private int id;

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

    public Date getSectionTime() {
        return sectionTime;
    }

    public void setSectionTime(Date sectionTime) {
        this.sectionTime = sectionTime;
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
        return "Section{" +
                "start=" + simpleDateFormat.format(start) +
                ", finish=" + simpleDateFormat.format(finish) +
                ", sectionTime=" + simpleDateFormatLap.format(sectionTime) +
                ", id=" + id +
                '}';
    }
}
