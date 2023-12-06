import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Recorder {
    private final ConcurrentHashMap<Driver, Set<Lap>> result = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Driver, Lap> bestLap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Driver, Lap> fastestLap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Driver, Lap> fastestSector1= new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Driver, Lap> fastestSector2 = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Driver, Lap> fastestSector3 = new ConcurrentHashMap<>();

    public void addLap(Driver driver, Lap lap) {
        Set<Lap> driverLaps =
                Optional.ofNullable(result.get(driver))
                        .orElse(new LinkedHashSet<>());

        driverLaps.add(lap);
        result.put(driver, driverLaps);

        if(lap.getSectors() != null){
            Sector bestSector1 =
                    fastestSector1.entrySet()
                            .stream()
                            .findFirst()
                            .flatMap(entry -> entry.getValue()
                                    .getSectors()
                                    .stream()
                                    .filter(sector -> sector.getId() == 1)
                                    .findFirst())
                            .orElse(null);

            Sector currentSector1 =
                    lap.getSectors().stream()
                            .filter(sector -> sector.getId() == 1)
                            .findFirst()
                            .orElse(null);

            if((bestSector1 == null && currentSector1.getSectorTime() != null)
                    || currentSector1.getSectorTime() != null && currentSector1.getSectorTime().getTime() < bestSector1.getSectorTime().getTime()) {
                fastestSector1.put(driver, lap);

//                    System.out.println("Fastest Sector 1: "+driver.getId()+" - "+lap.getId()+" - "+currentSector1.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector1.getSectorTime()));
            }

        }

        if(lap.getSectors() != null){
            Sector bestSector2 =
                    fastestSector2.entrySet()
                            .stream()
                            .findFirst()
                            .flatMap(entry -> entry.getValue()
                                    .getSectors()
                                    .stream()
                                    .filter(sector -> sector.getId() == 2)
                                    .findFirst())
                            .orElse(null);

            Sector currentSector2 =
                    lap.getSectors().stream()
                            .filter(sector -> sector.getId() == 2)
                            .findFirst()
                            .orElse(null);

            if((bestSector2 == null && currentSector2 != null && currentSector2.getSectorTime() != null)
                    || currentSector2 != null && currentSector2.getSectorTime() != null && currentSector2.getSectorTime().getTime() < bestSector2.getSectorTime().getTime()) {
                fastestSector2.put(driver, lap);

//                    System.out.println("Fastest Sector 2: "+driver.getId()+" - "+lap.getId()+" - "+currentSector2.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector2.getSectorTime()));
            }
        }

        if(lap.getSectors() != null){
            Sector bestSector3 =
                    fastestSector3.entrySet()
                            .stream()
                            .findFirst()
                            .flatMap(entry -> entry.getValue()
                                    .getSectors()
                                    .stream()
                                    .filter(sector -> sector.getId() == 3)
                                    .findFirst())
                            .orElse(null);

            Sector currentSector3 =
                    lap.getSectors().stream()
                            .filter(sector -> sector.getId() == 3)
                            .findFirst()
                            .orElse(null);

            if((bestSector3 == null && currentSector3 != null && currentSector3.getSectorTime() != null)
                    || currentSector3 != null && currentSector3.getSectorTime() != null && currentSector3.getSectorTime().getTime() < bestSector3.getSectorTime().getTime()) {
                fastestSector3.put(driver, lap);

//                    System.out.println("Fastest Sector 3: "+driver.getId()+" - "+lap.getId()+" - "+currentSector3.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(currentSector3.getSectorTime()));
            }
        }

        if(lap.getLapTime() != null){
            Lap driverBestLap = bestLap.get(driver);
            if(driverBestLap == null
                    || lap.getLapTime().getTime() < driverBestLap.getLapTime().getTime()) {
                bestLap.put(driver, lap);
                System.out.println("BEST LAPS: " +
                        bestLap.entrySet()
                                .stream()
                                .sorted((e1, e2) -> e1.getValue().getLapTime().compareTo(e2.getValue().getLapTime()))
                                .collect(Collectors.toMap(key -> key.getKey().getId(), value -> new SimpleDateFormat("mm:ss.SSS").format(value.getValue().getLapTime()),
                                        (e1, e2) -> e1, LinkedHashMap::new)));
            }

            Map.Entry<Driver, Lap> bestLap =
                    fastestLap.entrySet()
                            .stream()
                            .findFirst()
                            .orElse(null);

            if(bestLap == null
                    || lap.getLapTime().getTime() < bestLap.getValue().getLapTime().getTime()) {
               fastestLap.put(driver, lap);

//                    System.out.println("Fastest lap: "+driver.getId()+" - "+lap.getId()+" - "+ new SimpleDateFormat("mm:ss.SSS").format(lap.getLapTime()));
            }
        }
    }

    public Map<Driver, Set<Lap>> getResult() {
        return result;
    }

    public Map<Driver, Lap> getBestLaps() {
        return bestLap;
    }

    public Map<Driver, Lap> getOverallBestLaps() {
        return fastestLap;
    }

    public Map<Driver, Lap> getBestSector1() {
        return fastestSector1;
    }

    public Map<Driver, Lap> getBestSector2() {
        return fastestSector2;
    }

    public Map<Driver, Lap> getBestSector3() {
        return fastestSector3;
    }

}
